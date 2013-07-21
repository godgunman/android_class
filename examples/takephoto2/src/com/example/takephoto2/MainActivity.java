package com.example.takephoto2;

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
import com.parse.ParseFile;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final int TAKE_PHOTO = 1;

	private ProgressBar progress;
	private ImageView imageView;
	private TextView filePathTextView;
	private TextView parseFileUrlTextView;
	private LinearLayout linearLayout;
	private LinearLayout.LayoutParams imageMargin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "hCJ3YM593qsoFGt3CNVa0XRECus3Vbrz56HdyUvD",
				"WRUIsQkcyj0fgv8inoF6hSeo0rftbr2WTKPWLE09");

		setContentView(R.layout.activity_main);

		imageView = (ImageView) findViewById(R.id.imageView1);
		filePathTextView = (TextView) findViewById(R.id.filePath);
		parseFileUrlTextView = (TextView) findViewById(R.id.parseFileUrl);
		linearLayout = (LinearLayout) findViewById(R.id.container);
		progress = (ProgressBar) findViewById(R.id.progressBar1);
		
		imageMargin = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		imageMargin.bottomMargin = 15;
		
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("photos2");
		query.orderByDescending("createdAt");
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {

				progress.setVisibility(View.GONE);
				for (ParseObject obj : objects) {
					String name = obj.getString("name");
					ParseFile file = obj.getParseFile("file");
					try {
						byte[] data = file.getData();
						Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
								data.length);
						ImageView imageView = new ImageView(MainActivity.this);
						imageView.setImageBitmap(bitmap);
						linearLayout.addView(imageView, imageMargin);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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

	/**
	 * using ParseFile to save image.
	 * 
	 * @param bitmap
	 */
	private void saveToParseServer(Bitmap bitmap) {
		ByteArrayOutputStream boas = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 90, boas);

		try {
			boas.flush();
			boas.close();

			ParseFile file = new ParseFile("photo.png", boas.toByteArray());
			file.save();
			parseFileUrlTextView.setText(file.getUrl());

			ParseObject object = new ParseObject("photos2");
			object.put("file", file);
			object.put("name", "photo");
			object.saveInBackground(new SaveCallback() {
				@Override
				public void done(ParseException e) {
					if (e == null) {
						Toast.makeText(MainActivity.this, "done",
								Toast.LENGTH_LONG).show();
					}
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void saveToExternalStorage(Bitmap bitmap) {

		File imageDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

		if (imageDir.exists() == false) {
			imageDir.mkdir();
		}

		File imageFile = new File(imageDir, "photo.png");

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(imageFile));

			bitmap.compress(Bitmap.CompressFormat.PNG, 90, bos);

			bos.flush();
			bos.close();

			filePathTextView.setText(imageFile.toString());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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

			saveToExternalStorage(image);
			saveToParseServer(image);
		} else {
			return;
		}
	}
}
