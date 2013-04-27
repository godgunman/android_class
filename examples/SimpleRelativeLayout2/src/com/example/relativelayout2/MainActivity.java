package com.example.relativelayout2;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	Spinner monthSpinner;
	Spinner dateSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_relative_layout);

		monthSpinner = (Spinner) findViewById(R.id.month);
		dateSpinner = (Spinner) findViewById(R.id.date);
		String[] month = getResources().getStringArray(R.array.month);

		/* 從 arrays.xml 拿出資料後放入 adapter 並只用內建的 layout */
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, month);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		monthSpinner.setAdapter(adapter);
		monthSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				String content = parent.getItemAtPosition(pos).toString();
				Toast.makeText(MainActivity.this, content,
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				Toast.makeText(MainActivity.this, "nothing",
						Toast.LENGTH_SHORT).show();
			}
		});

		String[] date = new String[31];
		for (int i = 0; i < 31; i++) {
			date[i] = String.valueOf(i + 1);
		}

		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, date);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dateSpinner.setAdapter(adapter2);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.relative_layout, menu);
		return true;
	}

}
