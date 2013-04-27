package com.example.takephoto;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int TAKE_PHOTO = 1;

	private ImageView imageView;
	private TextView textView;
	private LinearLayout linearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "hCJ3YM593qsoFGt3CNVa0XRECus3Vbrz56HdyUvD",
				"WRUIsQkcyj0fgv8inoF6hSeo0rftbr2WTKPWLE09");

		setContentView(R.layout.activity_main);
		
		imageView = (ImageView) findViewById(R.id.imageView1);
		textView = (TextView) findViewById(R.id.textView1);
		linearLayout = (LinearLayout) findViewById(R.id.container);
		
		ParseQuery query = new ParseQuery("photos");
		query.findInBackground(new FindCallback() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				// TODO Auto-generated method stub
				for (ParseObject obj : objects) {
					String name = obj.getString("name");
					byte[] data = obj.getBytes("file");
					
					Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
					ImageView imageView = new ImageView(MainActivity.this);
					imageView.setImageBitmap(bitmap);

					linearLayout.addView(imageView);
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.take_photo:
			Log.d("debug", "take photo");
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, TAKE_PHOTO);
			return true;
		case R.id.action_settings:
			Log.d("debug", "action settings");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void save(Bitmap bitmap) {

		File imageDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

		if (imageDir.exists()==false) {
			Log.d("debug", "exists == false");
			if ( imageDir.mkdir() == false) {
				Log.d("debug", "create false");				
			}
		}

		File imageFile = new File(imageDir, "photo.png");

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(imageFile));
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, bos);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
			
			bos.flush();
			bos.close();
			
			textView.setText(imageFile.toString());
			
			ParseObject obj = new ParseObject("photos");
			obj.put("file", stream.toByteArray());
			obj.put("name", "photo1");
			
			obj.saveInBackground(new SaveCallback() {
				
				@Override
				public void done(ParseException e) {
					// TODO Auto-generated method stub
					if(e!=null) {
						Log.d("deubg", "save done");
					}
				}
			});
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == TAKE_PHOTO) {
			Log.d("debug", "here");
			Bitmap image = (Bitmap) data.getExtras().get("data");
			imageView.setImageBitmap(image);
			save(image);
		} else {
			return;
		}
	}
}
