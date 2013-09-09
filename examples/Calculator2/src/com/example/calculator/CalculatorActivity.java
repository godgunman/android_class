package com.example.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CalculatorActivity extends Activity {

	private EditText editText;

	/**
	 * +, -, *, / 1, 2, 3, 4
	 */
	private int lastOpt = -1;
	private int lastNum = Integer.MAX_VALUE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator);

		editText = (EditText) findViewById(R.id.result);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculator, menu);
		return true;
	}

	public void numClick(View view) {
		Button btn = (Button) view;
		btn.getText();
		editText.setText(editText.getText().toString() + btn.getText());
	}

	public int cal() {
		switch (lastOpt) {
		case 1:
			return lastNum + Integer.valueOf(editText.getText().toString());
		case 2:
			return lastNum - Integer.valueOf(editText.getText().toString());
		case 3:
			return lastNum * Integer.valueOf(editText.getText().toString());
		case 4:
			return lastNum / Integer.valueOf(editText.getText().toString());
		}
		return Integer.MAX_VALUE;
	}

	public void optClick(View view) {
		Button btn = (Button) view;
		switch (btn.getId()) {
		case R.id.btn_add:
			lastOpt = 1;
			lastNum = Integer.valueOf(editText.getText().toString());
			editText.setText("");
			break;
		case R.id.btn_sub:
			lastOpt = 2;
			lastNum = Integer.valueOf(editText.getText().toString());
			editText.setText("");
			break;
		case R.id.btn_mul:
			lastOpt = 3;
			lastNum = Integer.valueOf(editText.getText().toString());
			editText.setText("");
			break;
		case R.id.btn_div:
			lastOpt = 4;
			lastNum = Integer.valueOf(editText.getText().toString());
			editText.setText("");
			break;
		case R.id.btn_equal:
			if (lastOpt != -1) {
				int result = cal();
				editText.setText(String.valueOf(result));
				lastOpt = -1;
				lastNum = Integer.MAX_VALUE;
			}
			break;
		case R.id.btn_clean:
			lastOpt = -1;
			editText.setText("");
			break;
		}
	}
}
