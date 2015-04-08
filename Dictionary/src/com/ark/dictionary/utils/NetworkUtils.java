package com.ark.dictionary.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
	public static final String TAG = NetworkUtils.class.getName();

	/**
	 * Utility method to check Internet connection availability
	 * 
	 * @return boolean value indicating the presence of Internet connection
	 *         availability
	 */
	public static boolean isNetworkAvailable(Activity argActivity) {
		if (argActivity == null) {
			return false;
		}

		ConnectivityManager connectivityManager;
		NetworkInfo activeNetworkInfo = null;
		try {
			connectivityManager = (ConnectivityManager) argActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
			activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return activeNetworkInfo != null;
	}
}
