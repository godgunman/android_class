package com.example.simpleui3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Message extends Activity {
	
	ListView listView;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.message);

    	listView = (ListView) findViewById(R.id.listView1);
    	
    	Intent intent = this.getIntent();
    	String text = intent.getStringExtra("message");
    	writeFile(text);
    	
		String[] content = getMessages();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, content);
		
		listView.setAdapter(adapter);
    }

	private String[] getMessages() {
		String allText = readFile();
		return allText.split("\n");
	}
	
	private void writeFile(String text) {
    	try {
			FileOutputStream fos = openFileOutput("message.txt", Context.MODE_APPEND);
			text = text + "\n";
			fos.write(text.getBytes());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String readFile() {
		String text = "";
    	try {
			FileInputStream fis = openFileInput("message.txt");
			byte[] buffer = new byte[1024];			
			fis.read(buffer);
			text = new String(buffer);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}    	
    	return text;
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
