package org.ark.common.support;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

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
