package com.ark.dictionary.meaning;

import java.util.Locale;

import org.ark.common.support.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.ark.dictionary.R;
import com.ark.dictionary.database.DictionaryDB;
import com.ark.dictionary.model.Word;
import com.ark.dictionary.multi.MultiLanguageActivity;
import com.ark.dictionary.utils.AdUtilsAdMob;
import com.ark.dictionary.utils.Constants;
import com.google.android.gms.ads.AdView;

public class MeaningActivity extends BaseActivity implements OnClickListener {
	public static final String TAG = MeaningActivity.class.getName();

	private Word word;

	protected TextToSpeech speech;

	private AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_meaning);

		adView = AdUtilsAdMob.showBanner(getActivity(), getString(R.string.admob_id_meaning_screen_banner));
		((ViewGroup) findViewById(R.id.ad_container)).addView(adView);

		speech = new TextToSpeech(getActivity(), new OnInitListener() {

			@Override
			public void onInit(int status) {
				speech.setLanguage(Locale.UK);
			}
		});
	}

	@Override
	protected void initVar() {
		word = getIntent().getParcelableExtra(Constants.INTENT_EXTRA_DATA);

		if (word != null) {
			((TextView) findViewById(R.id.word)).setText(word.word);
			((TextView) findViewById(R.id.meaning)).setText(word.meaning);

			DictionaryDB.getInstance(getActivity()).updateRecent(word);
		}
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

		if (speech != null) {
			speech.stop();
			speech.shutdown();

			speech = null;
		}

		super.onDestroy();
	}

	@Override
	protected void bindContents() {
		findViewById(R.id.btn_favorite).setOnClickListener(this);
		findViewById(R.id.btn_speaker).setOnClickListener(this);
		findViewById(R.id.btn_multi_lang).setOnClickListener(this);

		updateView();
	}

	@Override
	protected void unbindContents() {
		findViewById(R.id.btn_favorite).setOnClickListener(null);
		findViewById(R.id.btn_speaker).setOnClickListener(null);
		findViewById(R.id.btn_multi_lang).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_favorite:
			DictionaryDB dictionaryDB = DictionaryDB.getInstance(getActivity());
			if (dictionaryDB.isFavorite(word)) {
				dictionaryDB.removeFavorite(word);
			} else {
				dictionaryDB.updateFavorite(word);
			}

			updateView();
			break;

		case R.id.btn_speaker:
			speech.speak(word.word, TextToSpeech.QUEUE_FLUSH, null);
			break;

		case R.id.btn_multi_lang:
			Intent intent = new Intent(getActivity(), MultiLanguageActivity.class);
			intent.putExtra(Constants.INTENT_EXTRA_DATA, word.word);
			startActivity(intent);
			break;
		}
	}

	private void updateView() {
		TextView btn_favorite = (TextView) findViewById(R.id.btn_favorite);
		if (DictionaryDB.getInstance(getActivity()).isFavorite(word)) {
			btn_favorite.setText(R.string.unfavorite);
			btn_favorite.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.btn_star_big_off, 0, 0, 0);
		} else {
			btn_favorite.setText(R.string.favorite);
			btn_favorite.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.btn_star_big_on, 0, 0, 0);
		}
	}
}
