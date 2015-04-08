package org.ark.common.support;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;

public abstract class BaseActivity extends ActionBarActivity {

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);

		initVar();
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		bindContents();
	}

	@Override
	protected void onPause() {
		super.onPause();

		unbindContents();
	}
	
	protected Activity getActivity() {
		return this;
	}

	protected abstract void initVar();

	protected abstract void bindContents();

	protected abstract void unbindContents();
}
