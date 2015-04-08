package com.ark.dictionary.home;

import org.ark.common.support.BaseActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.ark.dictionary.R;

public class BgActivity extends BaseActivity {
	private static boolean close;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		close = false;
	}

	@SuppressLint("NewApi")
	@Override
	protected void onResume() {
		super.onResume();

		if (close) {
			finish();
		} else {
			if (Build.VERSION.SDK_INT < 11) {
				getSupportActionBar().hide();
			} else {
				getActionBar().hide();
			}

			setContentView(R.layout.activity_bg);

			startActivity(new Intent(getActivity(), MainActivity.class));

			close = true;
		}
	}

	@Override
	protected void initVar() {
	}

	@Override
	protected void bindContents() {
	}

	@Override
	protected void unbindContents() {
	}

}
