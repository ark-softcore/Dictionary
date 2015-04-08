package org.ark.common;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class BaseFragment extends Fragment {
	protected View view;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initVar();
	}

	@Override
	public void onResume() {
		super.onResume();

		bindContents();
	}

	@Override
	public void onPause() {
		super.onPause();

		unbindContents();
	}

	public View findViewById(int resId) {
		return view.findViewById(resId);
	}

	protected abstract void initVar();

	protected abstract void bindContents();

	protected abstract void unbindContents();
}
