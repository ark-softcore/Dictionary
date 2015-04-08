package com.ark.dictionary.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PlayStoreUtils {
	public static final String TAG = PlayStoreUtils.class.getName();
	private static final String VENDOR = "ArK+Softcore";

	public static void openAppOnStore1(Context context) {
		final String appPackageName = context.getPackageName();
		try {
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
		} catch (android.content.ActivityNotFoundException anfe) {
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
		}
	}

	public static void openVendorOnStore(Context context) {
		try {
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:" + VENDOR)));
		} catch (android.content.ActivityNotFoundException anfe) {
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse("https://play.google.com/store/search?q=pub:" + VENDOR)));
		}
	}
}
