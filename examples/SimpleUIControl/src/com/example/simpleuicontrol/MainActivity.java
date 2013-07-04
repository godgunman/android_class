package com.example.simpleuicontrol;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView textView;
	private Button leftButton;
	private Button rightButton;
	private EditText speedText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.textview);
		leftButton = (Button) findViewById(R.id.button1);
		rightButton = (Button) findViewById(R.id.button2);
		speedText = (EditText) findViewById(R.id.speed);
		
		leftButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RelativeLayout.LayoutParams params =
						(LayoutParams) textView.getLayoutParams();
				int speed = Integer.valueOf(speedText.getText().toString());
				params.leftMargin -= speed;
				textView.setLayoutParams(params);
			}
		});
		rightButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RelativeLayout.LayoutParams params =
						(LayoutParams) textView.getLayoutParams();
				int speed = Integer.valueOf(speedText.getText().toString());
				params.leftMargin += speed;
				textView.setLayoutParams(params);
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
