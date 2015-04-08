package com.ark.dictionary.home;

import org.ark.common.support.BaseFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ark.dictionary.R;
import com.ark.dictionary.multi.MultiLanguageActivity;
import com.ark.dictionary.utils.PlayStoreUtils;

public class SlidingMenuOptions extends BaseFragment implements OnItemClickListener {
	public static final String TAG = SlidingMenuOptions.class.getName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.option_menu, null);

		return view;
	}

	@Override
	protected void initVar() {
		ListView listView = (ListView) findViewById(R.id.option_list_view);

		String[] items = { "ALL", "RECENT", "FAVORITES", "MORE APPS", "MULTI LANGUAGE" };
		SlidingMenuOptionAdapter adapter = new SlidingMenuOptionAdapter(getActivity(), items);
		listView.setAdapter(adapter);
	}

	@Override
	protected void bindContents() {
		((ListView) findViewById(R.id.option_list_view)).setOnItemClickListener(this);
	}

	@Override
	protected void unbindContents() {
		((ListView) findViewById(R.id.option_list_view)).setOnItemClickListener(null);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (position == 4) {
			startActivity(new Intent(getActivity(), MultiLanguageActivity.class));
		} else if (position == 3) {
			PlayStoreUtils.openVendorOnStore(getActivity());
		} else {
			((MainActivity) getActivity()).changeFragment((String) parent.getItemAtPosition(position));
		}
	}

}
