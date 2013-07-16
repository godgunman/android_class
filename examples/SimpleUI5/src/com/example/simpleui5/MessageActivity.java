package com.example.simpleui5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MessageActivity extends Activity {

	private ListView listView;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);

		listView = (ListView) findViewById(R.id.listView1);
		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("Loading...");
		progressDialog.show();
		setListViewContent();
	}

	public void setListViewContent() {
		
		ParseQuery query = new ParseQuery("Message");
		query.findInBackground(new FindCallback() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				
				List<Map<String, String>> data = new ArrayList<Map<String, String>>();
				for (ParseObject obj : objects) {
					Map<String, String> t = new HashMap<String, String>();
					t.put("text", obj.getString("text"));
					t.put("isEncrypt", String.valueOf(obj.getBoolean("isEncrypt")));
					data.add(t);
				}
				SimpleAdapter simpleAdapter = new SimpleAdapter(MessageActivity.this, data,
						R.layout.listview_item, new String[] { "text", "isEncrypt" },
						new int[] { R.id.textView1, R.id.textView2 });
				
				listView.setAdapter(simpleAdapter);
				progressDialog.dismiss();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
