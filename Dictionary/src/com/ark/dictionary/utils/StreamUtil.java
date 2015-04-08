package com.ark.dictionary.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.util.Log;

public class StreamUtil {
	public static final String TAG = StreamUtil.class.getName();

	public static void copyStream(InputStream input, OutputStream output) {
		BufferedInputStream bis = new BufferedInputStream(input);

		byte[] buffer = new byte[1024];
		try {
			while (bis.read(buffer) != -1) {
				output.write(buffer);
			}
		} catch (IOException e) {
			Log.e(TAG, "copy stream", e);
		}
	}
}
