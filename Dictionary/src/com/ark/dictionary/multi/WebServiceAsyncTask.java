package com.ark.dictionary.multi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.ark.dictionary.R;
import com.ark.dictionary.utils.NetworkUtils;

public class WebServiceAsyncTask extends AsyncTask<Void, Void, String> {
	public static final String TAG = WebServiceAsyncTask.class.getName();

	private Activity mActivity;
	private Listener mListener;

	private String word;
	private String targetLanguage;

	public WebServiceAsyncTask(Activity argActivity, String targetLanguage, String word, Listener argListener) {
		this.mActivity = argActivity;
		this.targetLanguage = targetLanguage;
		mListener = argListener;
		this.word = word;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		mListener.onTaskStart();

		// Check Internet access
		if (!NetworkUtils.isNetworkAvailable(mActivity)) {
			Toast.makeText(mActivity, "Internet Connection is required.", Toast.LENGTH_SHORT).show();
			cancel(true);
			return;
		}
	}

	@Override
	protected String doInBackground(Void... params) {
		String mResponseJson = null;

		// Set timeout parameters
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is
		// established.
		// The default value is zero, that means the timeout is not used.
		HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		HttpConnectionParams.setSoTimeout(httpParameters, 10000);

		DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);

		HttpGet httpget = new HttpGet();
		try {
			httpget.setURI(new URI(mActivity.getString(R.string.translater_link, "en", targetLanguage, word)));
		} catch (URISyntaxException e1) {
		}

		try {
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity httpEntity = response.getEntity();

			InputStream inputStream = httpEntity.getContent();

			// Parse the response to string
			BufferedReader reader = null;
			reader = new BufferedReader(new InputStreamReader(inputStream));

			StringBuilder sb = new StringBuilder();
			String line = null;

			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}

				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			mResponseJson = sb.toString();
		} catch (Exception e) {
			Log.e(TAG, "doInBackground", e);
		}

		return mResponseJson;
	}

	public static void parseData(String mResponseJson) {
		getObject(mResponseJson, 0);
	}

	private static String getObject(String response, int start) {
		if (response.charAt(start) != '[') {
			return "";
		}

		int end = start;
		int indent = 1;
		while (indent > 0) {
			end++;

			if (response.charAt(end) == '[') {
				indent++;
			}
			if (response.charAt(end) == ']') {
				indent--;
			}
		}

		return response.substring(start, end);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		if (TextUtils.isEmpty(result)) {
			mListener.onTaskFailed();
		} else {
			mListener.onTaskCompleted(result);
		}
	}

	public static interface Listener {
		void onTaskCompleted(String result);

		void onTaskFailed();

		void onTaskStart();
	}

}