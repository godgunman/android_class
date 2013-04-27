package com.example.notificaiton;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private static final int NOTIFICATION_ID = 1234;

	private Button button;
	private NotificationManager notificationMgr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showNotification();
			}
		});

		notificationMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@SuppressLint("NewApi")
	private void showNotification() {
		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, 0);

		Notification noti = new Notification.Builder(this)
				.setContentTitle("New Notification !!")
				.setContentText("Notification content")
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentIntent(pendingIntent).build();

		notificationMgr.notify(NOTIFICATION_ID, noti);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
