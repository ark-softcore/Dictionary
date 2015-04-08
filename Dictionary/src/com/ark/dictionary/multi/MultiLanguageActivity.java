package com.ark.dictionary.multi;

import org.ark.common.BaseActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ark.dictionary.R;
import com.ark.dictionary.multi.WebServiceAsyncTask.Listener;
import com.ark.dictionary.utils.AdUtilsAdMob;
import com.ark.dictionary.utils.AdUtilsRevMob;
import com.ark.dictionary.utils.Constants;
import com.google.android.gms.ads.AdView;
import com.revmob.RevMobUserGender;
import com.revmob.ads.fullscreen.RevMobFullscreen;

public class MultiLanguageActivity extends BaseActivity implements OnClickListener {
	public static final String TAG = MultiLanguageActivity.class.getName();

	private AdView adView;
	private RevMobFullscreen revMobAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_multi_language);

		adView = AdUtilsAdMob.showBanner(getActivity(), getString(R.string.admob_id_multi_language_screen_banner));
		((ViewGroup) findViewById(R.id.ad_container)).addView(adView);
	}

	@Override
	protected void initVar() {
		final Spinner spinner_language = (Spinner) findViewById(R.id.spinner_language);
		LanguageAdapter adapter = new LanguageAdapter(getActivity());
		spinner_language.setAdapter(adapter);
		spinner_language.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position != 0) {
					spinner_language.setBackgroundColor(getResources().getColor(R.color.spinner_color));
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		Language lastLanguage = getLastLanguage();
		int position = 0;

		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItem(i) == lastLanguage) {
				position = i;
				break;
			}
		}

		spinner_language.setSelection(position, true);
	}

	@Override
	protected void bindContents() {
		SharedPreferences shP = getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
		Editor edit = shP.edit();

		if (shP.getInt(Constants.PREF_REV_MOB_COUNT_MULTI_LANG, 0) >= 3) {
			revMobAd = AdUtilsRevMob.createFullScreen(getActivity(), RevMobUserGender.UNDEFINED);
			revMobAd.show();
			revMobAd = null;

			edit.putInt(Constants.PREF_REV_MOB_COUNT_MULTI_LANG, 1);
		} else {
			edit.putInt(Constants.PREF_REV_MOB_COUNT_MULTI_LANG,
					shP.getInt(Constants.PREF_REV_MOB_COUNT_MULTI_LANG, 0) + 1);
		}

		edit.commit();

		findViewById(R.id.btn_translate).setOnClickListener(this);
	}

	@Override
	protected void unbindContents() {
		findViewById(R.id.btn_translate).setOnClickListener(null);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (adView != null) {
			adView.resume();
		}

	}

	@Override
	public void onPause() {
		if (adView != null) {
			adView.pause();
		}

		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (adView != null) {
			adView.destroy();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_translate:
			translate();
			break;
		}
	}

	private void translate() {
		if (findViewById(R.id.progress_bar).getVisibility() == View.VISIBLE) {
			return;
		}

		final Spinner spinner_language = (Spinner) findViewById(R.id.spinner_language);

		if (spinner_language.getSelectedItemPosition() == 0) {
			spinner_language.setBackgroundColor(Color.RED);
			Toast.makeText(getActivity(), "Select the language first", Toast.LENGTH_SHORT).show();

			return;
		}

		final Language object = (Language) spinner_language.getSelectedItem();
		String word = ((TextView) findViewById(R.id.field_word)).getText().toString();
		if (TextUtils.isEmpty(word)) {
			return;
		}

		((TextView) findViewById(R.id.answer)).setText("");

		setLastLanguage(object);
		
		new WebServiceAsyncTask(getActivity(), object.code, word, new Listener() {

			@Override
			public void onTaskFailed() {
				findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
			}

			@Override
			public void onTaskCompleted(String result) {
				int firstIndex = result.indexOf("\"");
				int secondIndex = result.indexOf("\"", firstIndex + 1);

				String answer = result.substring(firstIndex + 1, secondIndex);

				((TextView) findViewById(R.id.answer)).setText(answer);

				findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
			}

			@Override
			public void onTaskStart() {
				findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
			}
		}).execute((Void) null);
	}

	private void setLastLanguage(Language object) {
		Editor edit = getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE).edit();
		edit.putString(Constants.PREF_LAST_LANGUAGE, object.code);
		edit.commit();
	}

	private Language getLastLanguage() {
		SharedPreferences shP = getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
		return Language.getLanguageByCode(shP.getString(Constants.PREF_LAST_LANGUAGE, ""));
	}

}
