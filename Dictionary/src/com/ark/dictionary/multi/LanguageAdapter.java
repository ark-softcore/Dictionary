package com.ark.dictionary.multi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ark.dictionary.R;

public class LanguageAdapter extends ArrayAdapter<Language> {
	public static final String TAG = LanguageAdapter.class.getName();

	public LanguageAdapter(Context context) {
		super(context, android.R.layout.simple_spinner_dropdown_item, Language.values());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) convertView;

		if (convertView == null) {
			view = (TextView) ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.spinner_selected_item, null);
		}

		view.setText(getItem(position).name);

		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) super.getView(position, convertView, parent);
		view.setText(getItem(position).name);

		return view;
	}

}
