package com.ark.dictionary.utils;

import android.app.Activity;
import android.util.Log;

import com.ark.dictionary.BuildConfig;
import com.revmob.RevMob;
import com.revmob.RevMobAdsListener;
import com.revmob.RevMobUserGender;
import com.revmob.ads.banner.RevMobBanner;
import com.revmob.ads.fullscreen.RevMobFullscreen;

public class AdUtilsRevMob {
	public static final String TAG = AdUtilsRevMob.class.getName();

	private static RevMobAdsListener revmobBannerListener = new RevMobAdsListener() {

		@Override
		public void onRevMobAdReceived() {
			if (BuildConfig.DEBUG)
				Log.e(TAG, "RevMob ad received.");
		}

		@Override
		public void onRevMobAdNotReceived(String message) {
			if (BuildConfig.DEBUG)
				Log.e(TAG, "RevMob ad not received.");
		}

		@Override
		public void onRevMobAdDismiss() {
			if (BuildConfig.DEBUG)
				Log.e(TAG, "Ad dismissed.");
		}

		@Override
		public void onRevMobAdClicked() {
			if (BuildConfig.DEBUG)
				Log.e(TAG, "Ad clicked.");
		}

		@Override
		public void onRevMobAdDisplayed() {
			if (BuildConfig.DEBUG)
				Log.e(TAG, "Ad displayed.");
		}

		@Override
		public void onRevMobEulaIsShown() {
			if (BuildConfig.DEBUG)
				Log.e(TAG, "Ad onRevMobEulaIsShown.");

		}

		@Override
		public void onRevMobEulaWasAcceptedAndDismissed() {
			if (BuildConfig.DEBUG)
				Log.e(TAG, "onRevMobEulaWasAcceptedAndDismissed.");
		}

		@Override
		public void onRevMobEulaWasRejected() {
			if (BuildConfig.DEBUG)
				Log.e(TAG, "onRevMobEulaWasRejected.");
		}

		@Override
		public void onRevMobSessionIsStarted() {
			if (BuildConfig.DEBUG)
				Log.e(TAG, "onRevMobSessionIsStarted.");
		}

		@Override
		public void onRevMobSessionNotStarted(String arg0) {
			if (BuildConfig.DEBUG)
				Log.e(TAG, "onRevMobSessionNotStarted.");
		}
	};

	// Banner

	public static RevMobBanner showBanner(Activity activity) {
		RevMob revmob = RevMob.start(activity);
		
		return revmob.createBanner(activity, revmobBannerListener);
	}

	public static RevMobBanner createBanner(Activity activity) {
		RevMob revmob = RevMob.start(activity);
		
		return revmob.createBanner(activity, revmobBannerListener);
	}

	public static RevMobBanner showBanner(Activity activity, RevMobUserGender gender) {
		RevMob revmob = RevMob.start(activity);
		
		revmob.printEnvironmentInformation(activity);
		return revmob.createBanner(activity);

	}

	public static void showFullScreen(Activity activity, RevMobUserGender gender) {
		RevMob revmob = RevMob.start(activity);
		
		revmob.showFullscreen(activity, revmobBannerListener);

		if (BuildConfig.DEBUG) {
			Log.d(TAG, "full screen Ad added");
		}
	}

	public static void showFullScreen(Activity activity) {
		RevMob revmob = RevMob.start(activity);
		revmob.showFullscreen(activity, revmobBannerListener);

		if (BuildConfig.DEBUG) {
			Log.d(TAG, "full screen Ad added");
		}
	}

	public static RevMobFullscreen createFullScreen(Activity activity, RevMobUserGender gender) {
		RevMob revmob = RevMob.start(activity);

		if (BuildConfig.DEBUG) {
			Log.d(TAG, "full screen Ad created");
		}

		return revmob.createFullscreen(activity, revmobBannerListener);
	}

}
