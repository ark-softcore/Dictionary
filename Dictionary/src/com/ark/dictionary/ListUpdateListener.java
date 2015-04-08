package com.ark.dictionary;

import java.util.List;

import com.ark.dictionary.model.Word;

public interface ListUpdateListener {
	void onListUpdate(List<Word> list);
}
