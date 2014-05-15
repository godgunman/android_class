package com.example.connectnetwork;

import java.io.BufferedReader;
import java.io.IOException;
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
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private static String SAMPLE_URL = "http://www.ntu.edu.tw/";
	private TextView textView;
	private ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.textView1);
		progress = new ProgressDialog(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View view) {
		String content = null;
		if (view.getId() == R.id.button1) {
			EditText editText = (EditText) findViewById(R.id.editText1);

			fetchMethod1(SAMPLE_URL);
		} else if (view.getId() == R.id.button2) {
			content = fetchMethod2();
		}
		textView.setText(content);
	}

	private void fetchMethod1(String url) {
		AsyncTask<String, Integer, String> task = new AsyncTask<String, Integer, String>() {
			@Override
			protected void onPreExecute() {
				progress.setProgress(0);
				progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				progress.setMessage("Loading ... ");
				progress.show();
			}

			@Override
			protected String doInBackground(String... params) {
				try {

					URL url = new URL(params[0]);
					URLConnection urlConnection = url.openConnection();

					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(
									urlConnection.getInputStream()));
					try {
						String line;
						StringBuilder builder = new StringBuilder();

						/* For performance, using StringBuilder. */
						while ((line = buffer.readLine()) != null) {
							builder.append(line);

							/* fake progress */
							int currentProgress = progress.getProgress();
							int add = (int) ((100 - currentProgress) * 0.02);
							publishProgress(currentProgress + add);
						}
						return builder.toString();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				progress.setProgress(100);
				textView.setText(result);
				progress.dismiss();
			}

			protected void onProgressUpdate(Integer... values) {
				progress.setProgress(values[0]);
			}
		};
		task.execute(url);
	}

	private String fetchMethod2() {

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet target = new HttpGet(SAMPLE_URL);
		try {
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String content = httpClient.execute(target, responseHandler);
			return content;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}