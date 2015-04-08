package com.ark.dictionary.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.ark.dictionary.model.Word;
import com.ark.dictionary.utils.Constants;

public class DictionaryDB extends SQLiteOpenHelper {
	private static final int DB_VERSION = 1;

	private static DictionaryDB _db;

	private DictionaryDB(Context paramContext) {
		super(paramContext, "DICTDATABASE", null, DB_VERSION);
	}

	public static DictionaryDB getInstance(Context context) {
		if (_db == null) {
			_db = new DictionaryDB(context);
		}

		return _db;
	}

	public List<Word> selectPattern(String pattern) {
		List<Word> list = new ArrayList<Word>();
		pattern = pattern.trim();

		String table;
		if (TextUtils.isEmpty(pattern)) {
			table = "A";
		} else {
			table = "" + pattern.charAt(0);
		}
		table = table.toUpperCase(Locale.getDefault());

		Cursor cursor = null;
		SQLiteDatabase localSQLiteDatabase = getReadableDatabase();

		try {
			String[] selectionArgs = { pattern.toLowerCase(Locale.getDefault()) + "%" };

			String str = Constants.BASE_TABLE_NAME + "_" + table;
			cursor = localSQLiteDatabase.query(str, new String[] { Constants.COLUMN_WORD, Constants.COLUMN_MEANING },
					"WORD LIKE ?", selectionArgs, null, null, null, "50");

			if (cursor == null) {
				return list;
			}

			while (cursor.moveToNext()) {
				list.add(new Word(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_WORD)), cursor
						.getString(cursor.getColumnIndex(Constants.COLUMN_MEANING))));
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return list;
	}

	public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
	}

	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
	}

	public void updateRecent(Word word) {
		SQLiteDatabase database = getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Constants.COLUMN_WORD, word.word);
		values.put(Constants.COLUMN_MEANING, word.meaning);
		values.put(Constants.COLUMN_TIMESTAMP, System.currentTimeMillis());

		if (database.query(Constants.TABLE_RECENT, null, Constants.COLUMN_WORD + "=?", new String[] { word.word },
				null, null, null).getCount() > 0) {
			database.update(Constants.TABLE_RECENT, values, Constants.COLUMN_WORD + "=?", new String[] { word.word });
		} else {
			database.insert(Constants.TABLE_RECENT, null, values);
		}
	}

	public void updateFavorite(Word word) {
		SQLiteDatabase database = getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Constants.COLUMN_WORD, word.word);
		values.put(Constants.COLUMN_MEANING, word.meaning);

		database.insert(Constants.TABLE_FAVOURITE, null, values);
	}

	public boolean isFavorite(Word word) {
		SQLiteDatabase database = getReadableDatabase();

		Cursor cursor = database.query(Constants.TABLE_FAVOURITE, null, Constants.COLUMN_WORD + "=?",
				new String[] { word.word }, null, null, null);
		return cursor != null && cursor.getCount() > 0;
	}

	public void removeFavorite(Word word) {
		SQLiteDatabase database = getWritableDatabase();

		database.delete(Constants.TABLE_FAVOURITE, Constants.COLUMN_WORD + "=?", new String[] { word.word });
	}

	public List<Word> getRecents() {
		return getRecents("");
	}

	public List<Word> getRecents(String pattern) {
		List<Word> list = new ArrayList<Word>();

		pattern = pattern.trim();

		Cursor cursor = null;
		SQLiteDatabase localSQLiteDatabase = getReadableDatabase();

		try {
			String[] selectionArgs = { pattern.toLowerCase(Locale.getDefault()) + "%" };

			cursor = localSQLiteDatabase.query(Constants.TABLE_RECENT, null, Constants.COLUMN_WORD + " LIKE ?",
					selectionArgs, null, null, Constants.COLUMN_TIMESTAMP, null);

			if (cursor == null) {
				return list;
			}

			if (cursor.moveToLast()) {
				do {
					list.add(new Word(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_WORD)), cursor
							.getString(cursor.getColumnIndex(Constants.COLUMN_MEANING)), cursor.getLong(cursor
							.getColumnIndex(Constants.COLUMN_WORD))));
				} while (cursor.moveToPrevious());
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return list;
	}

	public List<Word> getFavorites() {
		return getFavorites("");
	}

	public List<Word> getFavorites(String pattern) {
		List<Word> list = new ArrayList<Word>();

		pattern = pattern.trim();

		Cursor cursor = null;
		SQLiteDatabase localSQLiteDatabase = getReadableDatabase();

		try {
			String[] selectionArgs = { pattern.toLowerCase(Locale.getDefault()) + "%" };

			cursor = localSQLiteDatabase.query(Constants.TABLE_FAVOURITE, null, Constants.COLUMN_WORD + " LIKE ?",
					selectionArgs, null, null, Constants.COLUMN_WORD, null);

			if (cursor == null) {
				return list;
			}

			while (cursor.moveToNext()) {
				list.add(new Word(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_WORD)), cursor
						.getString(cursor.getColumnIndex(Constants.COLUMN_MEANING))));
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return list;
	}
}