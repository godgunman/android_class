package com.example.simpleui4;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MessageDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "db";
	private static final String TABLE_NAME = "message";

	private static final int DATABASE_VERSION = 1;
	private static final String CREATE_TABLE = "CREATE TABLE message("
			+ "id INTEGER, " + "text TEXT, " + "isEncrypt INTEGER, "
			+ "PRIMARY KEY(id));";

	public MessageDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
	}

	public long insert(Message mes) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("text", mes.text);
		values.put("isEncrypt", mes.isEncrypt);

		long rowId = db.insert(TABLE_NAME, null, values);
		Log.d("debug", "id = " + rowId);
		return rowId;
	}

	public Cursor getMessagesCursor() {
		SQLiteDatabase db = getWritableDatabase();
		String sql = "SELECT id as _id, text, isEncrypt FROM " + TABLE_NAME;

		return db.rawQuery(sql, null);
	}

	public List<Message> getMessages() {
		SQLiteDatabase db = getWritableDatabase();
		String sql = "SELECT * FROM " + TABLE_NAME;

		Cursor cursor = db.rawQuery(sql, null);
		List<Message> result = new ArrayList<Message>();

		while (cursor.moveToNext()) {
			result.add(new Message(cursor.getString(1), cursor.getInt(2) != 0));
		}

		cursor.close();
		db.close();
		return result;
	}
}
