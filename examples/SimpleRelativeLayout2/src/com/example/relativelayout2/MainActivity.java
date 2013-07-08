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

	private static int[] DAY_LIMIT = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31,
			30, 31 };

	private Spinner monthSpinner;
	private Spinner daySpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		monthSpinner = (Spinner) findViewById(R.id.month);
		daySpinner = (Spinner) findViewById(R.id.day);
		String[] month = getResources().getStringArray(R.array.month);

		/* 從 arrays.xml 拿出資料後放入 adapter 並使用內建的 layout */
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, month);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		monthSpinner.setAdapter(adapter);
		monthSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				int dayLimt = DAY_LIMIT[position];
				Integer[] date = new Integer[dayLimt];
				for (int i = 0; i < dayLimt; i++) {
					date[i] = i + 1;
				}

				ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(
						MainActivity.this,
						android.R.layout.simple_spinner_item, date);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				daySpinner.setAdapter(adapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				Toast.makeText(MainActivity.this, "nothing", Toast.LENGTH_SHORT)
						.show();
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
