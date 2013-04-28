package com.example.connectnetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private final static String EXAMPLE_URL = "http://www.yahoo.com/";

	private ProgressDialog progress;
	private TextView resultTextView;
	private Button button1;
	private Button button2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		resultTextView = (TextView) findViewById(R.id.result);
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				UrlLoader urlLoader = new UrlLoader();
				urlLoader.execute(1);
			}
		});

		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UrlLoader urlLoader = new UrlLoader();
				urlLoader.execute(2);
			}
		});

		progress = new ProgressDialog(this);
	}

	private String readStreamToString(InputStream in) {
		BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
		try {
			String line;
			StringBuilder content = new StringBuilder();
			while ((line = buffer.readLine()) != null) {
				content.append(line);
			}
			return content.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String fetchMethod1() {
		try {
			URL url = new URL(EXAMPLE_URL);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			String content = readStreamToString(is);
			return content;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String fetchMethod2() {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(EXAMPLE_URL);
		try {
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String content = httpclient.execute(httpget, responseHandler);
			return content;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Android 在 API 8 之後便不能在 Main Thread 中直接使用網路的功能，否則會丟出
	 * android.os.NetworkOnMainThreadException 這個例外。所以必須將使用網路的程式碼寫在另外一個 Thread
	 * 之中。這裡我們使用 AsyncTask 來幫助我們完成這件事情。
	 * 
	 * 另外要注意的是，任何會改變 UI 的操作一定要在 Main Thread 中執行。譬如 TextView 裡面的 setText()
	 * 由於 doInBackground() 並不是 Main Thread 所以不能在這裡操作 UI。 
	 */

	private class UrlLoader extends AsyncTask<Integer, Integer, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress.setTitle("Fetching");
			progress.show();
		}

		@Override
		protected String doInBackground(Integer... params) {
			int useMethod = params[0];
			switch (useMethod) {
			case 1:
				return fetchMethod1();
			case 2:
				return fetchMethod2();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				resultTextView.setText(result);
			}
			progress.dismiss();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
