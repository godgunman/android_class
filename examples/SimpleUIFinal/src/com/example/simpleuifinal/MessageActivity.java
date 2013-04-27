package com.example.simpleuifinal;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MessageActivity extends Activity {
	
	private LayoutInflater layoutInflater;
	private ListView listView;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);

		listView = (ListView) findViewById(R.id.listView1);
		layoutInflater = (LayoutInflater) MessageActivity.this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("Loading Message ...");
		progressDialog.show();
		setListViewContent();
		
		Intent intent = getIntent();
		Log.d("debug", "notification data = " + intent.getExtras().getString("com.parse.Data"));
	}

	private View ParseObjectToView(ParseObject obj) {
		View view = layoutInflater.inflate(R.layout.listview_item,
				null);

		byte[] file = obj.getBytes("file");
		String textString = obj.getString("text");
		boolean isEncrypt = obj.getBoolean("isEncrypt");

		ImageView photo = (ImageView) view.findViewById(R.id.photo);
		TextView text = (TextView) view.findViewById(R.id.text);
		TextView encrypt = (TextView) view
				.findViewById(R.id.isEncrypt);

		if (file != null) {
			photo.setImageBitmap(BitmapFactory.decodeByteArray(
					file, 0, file.length));
		} else {
			photo.setVisibility(View.GONE);
		}
		if (textString != null) {
			text.setText(textString);
		}
		if (isEncrypt) {
			encrypt.setText("Encrypted");
		} else {
			encrypt.setVisibility(View.GONE);
		}
		return view;
	}
	
	private void setListViewContent() {
		
		ParseQuery query = new ParseQuery(Constants.TABLE_NAME);
		query.orderByDescending("updatedAt");
		query.findInBackground(new FindCallback() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				View[] data = new View[objects.size()];
				
				for (int i = 0; i < objects.size(); i++) {
					data[i] = ParseObjectToView(objects.get(i));
				}
				MessageAdapter adapter = new MessageAdapter(data);
				listView.setAdapter(adapter);
				progressDialog.dismiss();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message, menu);
		return true;
	}
}
