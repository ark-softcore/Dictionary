package org.ark.common;

import android.app.Activity;

public abstract class BaseActivity extends Activity {

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
