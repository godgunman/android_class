package com.example.database;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private PeopleDBHelper dbHelp;
	
	private Button randomButton;
	private Button insertButton;
	private Button showButton;
	private Button deleteButton;
	
	private TextView peopleTextView;
	private TextView databaseContentTextView;
	
	private People random_people;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbHelp = new PeopleDBHelper(this);
		
		randomButton = (Button) findViewById(R.id.btn_rand);
		insertButton = (Button) findViewById(R.id.btn_insert);
		showButton = (Button) findViewById(R.id.btn_show);
		deleteButton = (Button) findViewById(R.id.btn_del);
		
		peopleTextView = (TextView) findViewById(R.id.people);
		databaseContentTextView = (TextView) findViewById(R.id.database_content);

		
		randomButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				random_people = new People();
				peopleTextView.setText(random_people.toString());
			}
		});
		
		insertButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				long id1 = dbHelp.insert(random_people);
				Log.d("debug", ""+id1);
			}
		});

		showButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				List<People> peoples = dbHelp.getPeoples();
				String text = "";
				for(People p : peoples) {
					text+=p.toString()+"\n";
				}
				databaseContentTextView.setText(text);
			}
		});
		
		deleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dbHelp.deletePeoples();
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
