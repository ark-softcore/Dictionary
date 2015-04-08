package com.ark.dictionary.home;

import java.io.FileOutputStream;
import java.util.List;

import org.ark.common.support.BaseActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.ark.dictionary.R;
import com.ark.dictionary.database.DictionaryDB;
import com.ark.dictionary.model.Word;
import com.ark.dictionary.utils.AdUtilsAdMob;
import com.ark.dictionary.utils.AdUtilsRevMob;
import com.ark.dictionary.utils.Constants;
import com.ark.dictionary.utils.StreamUtil;
import com.google.android.gms.ads.AdView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.revmob.RevMobUserGender;
import com.revmob.ads.fullscreen.RevMobFullscreen;

public class MainActivity extends BaseActivity implements TextWatcher {
	public static final String TAG = MainActivity.class.getName();

	private SlidingMenu menu;

	private View mainPage;

	private WordListFragment fragment;

	private RevMobFullscreen revMobAd;

	private AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		adView = AdUtilsAdMob.showBanner(getActivity(), getString(R.string.admob_id_home_screen_banner));
		((ViewGroup) findViewById(R.id.ad_container)).addView(adView);
	}

	@Override
	protected void initVar() {
		if (!isDatabaseExist()) {
			new CopyDatabaseTask().execute();
		}

		mainPage = findViewById(R.id.main_page);
		setupSlidingMenu();
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

	/** Called before the activity is destroyed. */
	@Override
	public void onDestroy() {
		// Destroy the AdView.
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}

	@Override
	protected void bindContents() {
		EditText search_view = (EditText) findViewById(R.id.search_view);
		search_view.addTextChangedListener(this);

		if (isDatabaseExist() && fragment != null && fragment.adapter.isEmpty()) {
			updateList();
		}

		SharedPreferences shP = getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
		Editor edit = shP.edit();
		edit.putInt(Constants.PREF_REV_MOB_COUNT_HOME, shP.getInt(Constants.PREF_REV_MOB_COUNT_HOME, 0) + 1);
		if (revMobAd != null) {
			revMobAd.show();
			revMobAd = null;

			edit.putInt(Constants.PREF_REV_MOB_COUNT_HOME, 1);
		}

		edit.commit();
	}

	@Override
	protected void unbindContents() {
		EditText search_view = (EditText) findViewById(R.id.search_view);
		search_view.addTextChangedListener(this);

		SharedPreferences shP = getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
		if (shP.getInt(Constants.PREF_REV_MOB_COUNT_HOME, 0) >= 5) {
			revMobAd = AdUtilsRevMob.createFullScreen(getActivity(), RevMobUserGender.UNDEFINED);
		}
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void setupSlidingMenu() {
		final int width;
		final int height;

		Display display = getWindowManager().getDefaultDisplay();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			Point size = new Point();
			display.getSize(size);

			width = size.x;
			height = size.y;
		} else {
			width = display.getWidth();
			height = display.getHeight();
		}

		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setBehindWidth((int) (width * 0.75));
		menu.setBehindScrollScale(1.00f);
		menu.setFadeDegree(0.25f);
		menu.setSlidingEnabled(true);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setMenu(R.layout.menu_frame);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);

		menu.setFrontCanvasTransformer(new CanvasTransformer() {

			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				canvas.scale(1f, 1f - (percentOpen * 0.2f), width - 20f, height / 2);
				mainPage.setAlpha(1f - (percentOpen * 0.2f));
			}
		});

		getSupportFragmentManager().beginTransaction().replace(R.id.menu_layout, new SlidingMenuOptions()).commit();

		View imagebuttonSliderControl = findViewById(R.id.button_slider_control);
		imagebuttonSliderControl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				menu.toggle(true);
			}
		});

		fragment = new AllListFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.page_container, fragment).commit();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		updateList();
	}

	public void updateList() {
		CharSequence string = ((TextView) findViewById(R.id.search_view)).getText();
		List<Word> list = null;
		if (fragment instanceof AllListFragment) {
			list = DictionaryDB.getInstance(getActivity()).selectPattern(string.toString());
		} else if (fragment instanceof RecentListFragment) {
			list = DictionaryDB.getInstance(getActivity()).getRecents(string.toString());
		} else if (fragment instanceof FavoriteListFragment) {
			list = DictionaryDB.getInstance(getActivity()).getFavorites(string.toString());
		}

		fragment.onListUpdate(list);

		if (list.isEmpty()) {
			findViewById(R.id.msg_no_word_found).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.id.msg_no_word_found).setVisibility(View.GONE);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void onBackPressed() {
		if (menu != null) {
			if (menu.isMenuShowing()) {
				menu.toggle();
			} else {
				super.onBackPressed();
			}
		} else {
			super.onBackPressed();
		}
	}

	public void changeFragment(String tag) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (tag.equals("ALL") && !(fragment instanceof AllListFragment)) {

			fragment = new AllListFragment();
		} else if (tag.equals("RECENT") && !(fragment instanceof RecentListFragment)) {

			fragment = new RecentListFragment();
			EditText search_view = (EditText) findViewById(R.id.search_view);
			search_view.setText("");
		} else if (tag.equals("FAVORITES") && !(fragment instanceof FavoriteListFragment)) {

			fragment = new FavoriteListFragment();
		}

		menu.toggle();

		transaction.replace(R.id.page_container, fragment).commit();
	}

	protected boolean isDatabaseExist() {
		SharedPreferences shP = getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
		return shP.getBoolean(Constants.PREF_DB_COPEID, false);
	}

	public class CopyDatabaseTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			dialog = ProgressDialog.show(getActivity(), null, "Initializing App First Time..");
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				openOrCreateDatabase(Constants.DATABASE_NAME, Context.MODE_PRIVATE, null);
				StreamUtil.copyStream(getAssets().open("DICTDATABASE"), new FileOutputStream(
						getDatabasePath(Constants.DATABASE_NAME)));
			} catch (Exception e) {
				Log.e(TAG, "database exist", e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			Editor edit = getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE).edit();
			edit.putBoolean(Constants.PREF_DB_COPEID, true);
			edit.commit();

			dialog.dismiss();

			updateList();
		}
	}
}
