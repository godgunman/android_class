package com.example.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PeopleDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "db";
	private static final String TABLE_NAME = "people";

	private static final int DATABASE_VERSION = 1;
	private static final String CREATE_TABLE = "CREATE TABLE people("
			+ "id INTEGER, " + "name TEXT, " + "age INTEGER, " + "phone TEXT, "
			+ "PRIMARY KEY(id));";

	public PeopleDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.d("debug", "constructor");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
		Log.d("debug", "onCreate");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
		Log.d("debug", "onUpgrade");
	}

	public long insert(People people) {
		Log.d("debug", "insert");
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("id", people.getId());
		values.put("name", people.getName());
		values.put("age", people.getAge());
		values.put("phone", people.getPhone());
		long rowId = db.insert(TABLE_NAME, null, values);
		return rowId;
	}

	public List<People> getPeoples() {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_NAME;

		Cursor cursor = db.rawQuery(sql, null);
		List<People> result = new ArrayList<People>();

		while (cursor.moveToNext()) {
			result.add(new People(cursor.getInt(0), cursor.getString(1), cursor
					.getInt(2), cursor.getString(3)));
		}
		cursor.close();
		db.close();
		return result;
	}

	public void deletePeoples() {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(TABLE_NAME, "1", null);
	}
}
