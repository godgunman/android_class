package com.example.threewaysforclick;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ClickActivity extends Activity {

	Button button1;
	Button button2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.click);
		
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		
		/* anonymous inner type */
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(ClickActivity.this, "button1 clicked", Toast.LENGTH_SHORT).show();	
			}
		});
		
		button2.setOnClickListener(new Button2ClickImpl());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.click, menu);
		return true;
	}

	/* method has View parameter, and integrated with XML   */
	public void button3Click(View view) {
		Toast.makeText(this, "button3 clicked", Toast.LENGTH_SHORT).show();
	}

	/* inner class, and implements OnClickListener  */
	public class Button2ClickImpl implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(ClickActivity.this, "button2 clicked", Toast.LENGTH_SHORT).show();	
		}
	}

}
