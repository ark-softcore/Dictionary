package com.ark.dictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Word implements Parcelable {
	public String word;
	public String meaning;
	public long timestamp;

	public Word(String word, String meaning) {
		this.word = word;
		this.meaning = meaning;
	}

	public Word(String word, String meaning, long timestamp) {
		this.word = word;
		this.meaning = meaning;
		this.timestamp = timestamp;
	}

	private Word(Parcel in) {
		word = in.readString();
		meaning = in.readString();
		timestamp = in.readLong();
	}

	public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
		public Word createFromParcel(Parcel in) {
			return new Word(in);
		}

		public Word[] newArray(int size) {
			return new Word[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(word);
		dest.writeString(meaning);
		dest.writeLong(timestamp);
	}
}
