package com.ark.dictionary.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ark.dictionary.R;

public class SlidingMenuOptionAdapter extends ArrayAdapter<String> {

	public SlidingMenuOptionAdapter(Context context) {
		this(context, new ArrayList<String>());
	}

	public SlidingMenuOptionAdapter(Context context, String[] items) {
		super(context, 0, items);
	}

	public SlidingMenuOptionAdapter(Context context, List<String> items) {
		super(context, 0, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = (TextView) inflater.inflate(R.layout.sliding_menu_list_item, null);
		}

		switch (position) {
		case 0:
			view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_actionbar_all, 0, 0, 0);
			break;

		case 1:
			view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_actionbar_recent, 0, 0, 0);
			break;

		case 2:
			view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_actionbar_fevorite, 0, 0, 0);
			break;

		case 3:
			view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_actionbar_more_app, 0, 0, 0);
			break;

		case 4:
			view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_actionbar_multi_lang, 0, 0, 0);
			break;
			
		case 5:
			view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_actionbar_remove, 0, 0, 0);
			break;
		}

		view.setText(getItem(position));

		return view;
	}
}
