package com.ark.dictionary.home;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ark.dictionary.R;
import com.ark.dictionary.model.Word;

public class WordListAdapter extends ArrayAdapter<Word> {
	public static final String TAG = WordListAdapter.class.getName();

	public WordListAdapter(Context context) {
		this(context, new ArrayList<Word>());
	}

	public WordListAdapter(Context context, List<Word> objects) {
		super(context, 0, objects);
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);

			((TextView) convertView).setTextColor(Color.BLACK);
			Drawable drawable = getContext().getResources().getDrawable(R.drawable.word_list_selector);
			if(Build.VERSION.SDK_INT>=16){
				convertView.setBackground(drawable);
			}else{
				convertView.setBackgroundDrawable(drawable);
			}
		}

		((TextView) convertView).setText(getItem(position).word);

		return convertView;
	}

}
