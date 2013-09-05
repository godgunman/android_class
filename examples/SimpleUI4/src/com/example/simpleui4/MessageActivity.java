package com.example.simpleui4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

public class MessageActivity extends Activity {

	private ListView listView;
	private MessageDBHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		listView = (ListView) findViewById(R.id.listView1);
		dbHelper = new MessageDBHelper(this);

		Intent intent = this.getIntent();
		String text = intent.getStringExtra("message");
		boolean isEncrypt = intent.getBooleanExtra("isEncrypt", false);

		dbHelper.insert(new Message(text, isEncrypt));

		/* 兩種方法實作 Adapter */

		// listView.setAdapter(getSimpleAdapter());
		listView.setAdapter(getCursorAdapter());
	}

	public SimpleCursorAdapter getCursorAdapter() {
		Cursor c = dbHelper.getMessagesCursor();
		String[] from = new String[] { "text", "isEncrypt" };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

		/*
		 * 這個建構方法在 API level 11 的時候被列為不建議使用。 建議改用 LoaderManager 搭配 CursorLoader。
		 */
		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_expandable_list_item_2, c, from, to);
		return cursorAdapter;
	}

	public SimpleAdapter getSimpleAdapter() {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		List<Message> messages = dbHelper.getMessages();
		for (Message mes : messages) {
			Map<String, String> t = new HashMap<String, String>();
			t.put("text", mes.text);
			t.put("isEncrypt", String.valueOf(mes.isEncrypt));
			data.add(t);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, data,
				android.R.layout.simple_expandable_list_item_2, new String[] {
						"text", "isEncrypt" }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		return simpleAdapter;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
