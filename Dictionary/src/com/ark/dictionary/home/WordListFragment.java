package com.ark.dictionary.home;

import java.util.List;

import org.ark.common.support.BaseFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ark.dictionary.ListUpdateListener;
import com.ark.dictionary.R;
import com.ark.dictionary.meaning.MeaningActivity;
import com.ark.dictionary.model.Word;
import com.ark.dictionary.utils.Constants;

public abstract class WordListFragment extends BaseFragment implements OnItemClickListener, ListUpdateListener {
	public static final String TAG = WordListFragment.class.getName();

	private boolean ready;

	protected WordListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.word_list_layout, null);

		ready = true;

		return view;
	}

	@Override
	protected void initVar() {
		adapter = new WordListAdapter(getActivity());
		((ListView) findViewById(R.id.list_view)).setAdapter(adapter);
	}

	@Override
	protected void bindContents() {
		((ListView) findViewById(R.id.list_view)).setOnItemClickListener(this);

		if (((MainActivity) getActivity()).isDatabaseExist() && adapter.isEmpty()) {
			MainActivity activity = (MainActivity) getActivity();
			activity.updateList();
		}
	}

	@Override
	protected void unbindContents() {
		((ListView) findViewById(R.id.list_view)).setOnItemClickListener(null);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Word word = (Word) parent.getItemAtPosition(position);

		Intent intent = new Intent(getActivity(), MeaningActivity.class);
		intent.putExtra(Constants.INTENT_EXTRA_DATA, word);
		startActivity(intent);
	}

	@Override
	public void onListUpdate(List<Word> list) {
		if (!ready) {
			return;
		}

		ListView listView = (ListView) findViewById(R.id.list_view);
		WordListAdapter adapter = (WordListAdapter) listView.getAdapter();
		adapter.clear();
		adapter.addAll(list);
		listView.setSelection(0);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

		ready = false;
	}
}
