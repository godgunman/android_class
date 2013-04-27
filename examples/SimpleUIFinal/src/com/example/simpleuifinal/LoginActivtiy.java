package com.example.simpleuifinal;

import org.json.JSONException;
import org.json.JSONObject;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivtiy extends Activity {

	private EditText username;
	private EditText password;

	private Button login;
	private Button singup;

	private ProgressDialog progress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Parse.initialize(this, "hCJ3YM593qsoFGt3CNVa0XRECus3Vbrz56HdyUvD",
				"WRUIsQkcyj0fgv8inoF6hSeo0rftbr2WTKPWLE09");
		ParseInstallation.getCurrentInstallation().saveInBackground();
		PushService.setDefaultPushCallback(this, LoginActivtiy.class);
		PushService.subscribe(this, Constants.TABLE_NAME, MessageActivity.class);
		
		setContentView(R.layout.login);

		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);

		login = (Button) findViewById(R.id.login);
		singup = (Button) findViewById(R.id.signup);

		progress = new ProgressDialog(this);
		
		singup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				progress.setTitle("Singup and Logging ...");
				progress.show();
				
				ParseUser user = new ParseUser();
				user.setUsername(username.getText().toString());
				user.setPassword(password.getText().toString());
				user.signUpInBackground(new SignUpCallback() {

					@Override
					public void done(ParseException e) {
						// TODO Auto-generated method stub
						if (e == null) {
							Log.d("debug", "signup ok");
							goToMainActvitiy();
						} else {
							fail();
						}
						progress.dismiss();
					}
				});
			}
		});

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				progress.setTitle("Logging...");
				progress.show();
				// TODO Auto-generated method stub
				ParseUser.logInInBackground(username.getText().toString(),
						password.getText().toString(), new LogInCallback() {

							@Override
							public void done(ParseUser user, ParseException e) {
								if (e == null) {
									Log.d("debug", "login ok");
									goToMainActvitiy();
								} else {
									fail();
								}
								progress.dismiss();
							}
						});
			}
		});
		
		ParseUser user = ParseUser.getCurrentUser();
		if (user != null) {
			goToMainActvitiy();
		}
	}

	private void fail() {
		Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
	}
	private void goToMainActvitiy() {
		
		ParseUser user = ParseUser.getCurrentUser();
		JSONObject data = new JSONObject();
		try {
			data.put("alert", String.format("%s login !!", user.getUsername()));
			data.put("name", user.getUsername());
			data.put("date", user.getUpdatedAt().toString());
			data.put("id", user.getObjectId());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ParsePush push = new ParsePush();
		push.setChannel(Constants.TABLE_NAME);
		//		push.setMessage(String.format("%s login !!", user.getUsername()));
		push.setData(data);
		push.sendInBackground();
		
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		
	}
}
