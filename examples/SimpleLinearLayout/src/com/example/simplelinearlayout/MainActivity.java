package com.example.simplelinearlayout;

import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	LinearLayout linearLayout;
	EditText toEditText;
	EditText subjectEditText;
	EditText messageEditText;
	Button sendButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		setContentViewByCode();
		setContentViewByXML();
	}

	private void setContentViewByCode() {
		linearLayout = new LinearLayout(this);
		linearLayout.setPadding(10, 0, 10, 0);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

		toEditText = new EditText(this);
		toEditText.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 0));
		toEditText.setHint("To");

		subjectEditText = new EditText(this);
		subjectEditText.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 0));
		subjectEditText.setHint("Subject");

		messageEditText = new EditText(this);
		messageEditText.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
		messageEditText.setHint("Subject");
		messageEditText.setGravity(Gravity.TOP);

		sendButton = new Button(this);
		sendButton.setText("send");
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.RIGHT;
		sendButton.setLayoutParams(params);

		linearLayout.addView(toEditText);
		linearLayout.addView(subjectEditText);
		linearLayout.addView(messageEditText);
		linearLayout.addView(sendButton);
		setContentView(linearLayout);
	}

	private void setContentViewByXML() {
		setContentView(R.layout.activity_linear_layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.linear_layout, menu);
		return true;
	}
}
