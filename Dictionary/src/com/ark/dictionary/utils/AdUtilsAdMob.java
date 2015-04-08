package com.ark.dictionary.utils;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AdUtilsAdMob {
	public static final String TAG = AdUtilsAdMob.class.getName();

	private static AdListener adListener = new AdListener() {
		@Override
		public void onAdLoaded() {
			super.onAdLoaded();
		}

		@Override
		public void onAdClosed() {
			super.onAdClosed();
		}

		@Override
		public void onAdFailedToLoad(int errorCode) {
			super.onAdFailedToLoad(errorCode);
		}

		@Override
		public void onAdLeftApplication() {
			super.onAdLeftApplication();
		}

		@Override
		public void onAdOpened() {
			// TODO Auto-generated method stub
			super.onAdOpened();
		}
	};

	public static AdView showAd(final Activity activity, final AdSize adSize, String ad_unit_id) {
		try {
			// Create an ad.
			AdView adView = new AdView(activity);
			adView.setAdSize(adSize);
			adView.setAdUnitId(ad_unit_id);

			adView.setAdListener(adListener);

			AdRequest adRequest = new AdRequest.Builder().build();

			adView.loadAd(adRequest);
			return adView;
		} catch (Exception e) {
			Log.e(TAG, "Ad view exception", e);
		}

		return null;
	}

	public static AdView showBanner(final Activity activity, String ad_unit_id) {
		return showAd(activity, AdSize.BANNER, ad_unit_id);
	}

	public static AdView showMediumRectangle(final Activity activity, String ad_unit_id) {
		return showAd(activity, AdSize.MEDIUM_RECTANGLE, ad_unit_id);
	}

	public static void destroyAllAdView(ViewGroup container) {
		int childCount = container.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = container.getChildAt(i);

			if (child instanceof AdView) {
				((AdView) child).destroy();
			}
		}

		container.removeAllViews();
	}

}
