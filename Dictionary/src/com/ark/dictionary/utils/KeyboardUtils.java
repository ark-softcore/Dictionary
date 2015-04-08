package com.ark.dictionary.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtils {
	public static final String TAG = KeyboardUtils.class.getName();

	public static void hideKeyboard(Activity activity) {
		try {
			InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm.isActive()) {
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
			}
		} catch (Exception e) {
		}
	}

	public static void hideKeyboard(Activity activity, View view) {
		try {
			InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm.isActive()) {
				imm.hideSoftInputFromInputMethod(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
			}
		} catch (Exception e) {
		}
	}

}
