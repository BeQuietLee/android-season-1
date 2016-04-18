package com.leili.season1.util;

import android.content.Context;
import android.hardware.input.InputManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * View Util Methods
 * Created by lei.li on 4/13/16.
 */
public class ViewUtils {
	private ViewUtils() {
	}

	public static void showShortToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static void hideKeyboard(View v) {
		((InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
				v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
