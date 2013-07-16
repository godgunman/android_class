package com.example.simpleui2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MessageActivity extends Activity {

	private TextView textView;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_message);

    	textView = (TextView) findViewById(R.id.messageTextView);
    	
    	Intent intent = this.getIntent();
    	String text = intent.getStringExtra("message");
    	textView.setText(text);
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
