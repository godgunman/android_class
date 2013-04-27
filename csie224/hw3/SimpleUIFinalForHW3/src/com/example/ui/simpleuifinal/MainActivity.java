package com.example.ui.simpleuifinal;

import java.io.ByteArrayOutputStream;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class MainActivity extends Activity {

	private static final int TAKE_PICTURE = 1;

	private Button submitButton;
	private EditText inputEdit;
	private CheckBox isEncrypt;
	private ImageView photo;
	private ProgressDialog submitProgress;

	private SharedPreferences sp;
	private SharedPreferences.Editor edit;

	/*
	 * data/data/com.example.ui.simple_ui2/shared_prefs/message.xml
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Parse.initialize(this, "hCJ3YM593qsoFGt3CNVa0XRECus3Vbrz56HdyUvD",
				"WRUIsQkcyj0fgv8inoF6hSeo0rftbr2WTKPWLE09");

		setContentView(R.layout.main);

		findViews();
		initSettings();
		setListeners();
	}

	private void findViews() {
		submitButton = (Button) findViewById(R.id.submitButton);
		inputEdit = (EditText) findViewById(R.id.input);
		isEncrypt = (CheckBox) findViewById(R.id.encrypt);
		photo = (ImageView) findViewById(R.id.photo);
		submitProgress = new ProgressDialog(this);
	}

	private void initSettings() {
		sp = getSharedPreferences("message", Context.MODE_PRIVATE);
		edit = sp.edit();

		boolean isChecked = sp.getBoolean("check_box", true);
		String text = sp.getString("edit_text", "");

		inputEdit.setText(text);
		isEncrypt.setChecked(isChecked);
	}

	private void setListeners() {
		submitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Editable editable = inputEdit.getText();
				String text = editable.toString();

				editable.clear();
				goToMessageActivity(text, isEncrypt.isChecked());
			}
		});

		isEncrypt.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				edit.putBoolean("check_box", isChecked);
				edit.commit();
			}
		});

		inputEdit.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				boolean result = false;
				Editable editable = inputEdit.getText();
				String text = editable.toString();

				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_ENTER) {

						editable.clear();
						goToMessageActivity(text, isEncrypt.isChecked());
						result = true;
					}
				}

				text = editable.toString();
				edit.putString("edit_text", text);
				edit.commit();
				return result;
			}
		});
	}

	private void closeKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
					InputMethodManager.HIDE_NOT_ALWAYS);
		} else {
			imm.hideSoftInputFromInputMethod(inputEdit.getWindowToken(), 0);
		}
	}

	/**
	 * TODO Step1. 丟出拍照的 intent 。
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_take_photo:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	/**
	 * TODO Step2. 處理拍照的 intent 所回傳的結果，並將結果顯示在 ImageView photo 這個上面。
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case TAKE_PICTURE:
			}
		} 
	}

	/**
	 * TODO Step3. 在將 ParserObject 存入 server 的時候多新增一個欄位 file 放拍照的照片。 並在 text
	 * 欄位放入學號。
	 */
	public void goToMessageActivity(String text, boolean isChecked) {
		closeKeyboard();

		submitProgress.setTitle("Sending Message...");
		submitProgress.show();

		ParseObject mesObject = new ParseObject(Constants.TABLE_NAME);
		mesObject.put("text", text);
		mesObject.put("isEncrypt", isChecked);
		mesObject.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				// TODO Auto-generated method stub
				submitProgress.dismiss();

				Intent intent = new Intent();
				intent.setClass(MainActivity.this, MessageActivity.class);
				MainActivity.this.startActivity(intent);
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
