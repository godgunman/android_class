package com.example.simpleui4;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button submitButton;
	private EditText inputEdit;
	private CheckBox isEncrypt;

	private SharedPreferences sp;
	private SharedPreferences.Editor edit;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_ui);
        
        sp = getSharedPreferences("message", Context.MODE_PRIVATE);
        edit = sp.edit();
                
        submitButton = (Button) findViewById(R.id.submitButton);
        inputEdit = (EditText) findViewById(R.id.input);
        isEncrypt = (CheckBox) findViewById(R.id.encrypt);
        
        boolean isChecked = sp.getBoolean("check_box", true);
        String text = sp.getString("edit_text", "");
        
        inputEdit.setText(text);
        isEncrypt.setChecked(isChecked);
        
        submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("debug", "click");
				
				Editable editable = inputEdit.getText();
				String text = editable.toString();
				if (isEncrypt.isChecked()) {
					text = "************";
				}
				
				editable.clear();
				Toast.makeText(MainActivity.this, 
						text, Toast.LENGTH_SHORT).show();
				
				goToMessageActivity(text, isEncrypt.isChecked());
			}
		});
        
        
        isEncrypt.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        edit.putBoolean("check_box", isChecked);
		        edit.commit();
			}
		});
        
        inputEdit.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				Editable editable = inputEdit.getText();
				String text = editable.toString();

				if(event.getAction() == KeyEvent.ACTION_DOWN){
					if(keyCode == KeyEvent.KEYCODE_ENTER) {
						
						editable.clear();
						Toast.makeText(MainActivity.this, 
								text, Toast.LENGTH_SHORT).show();
						goToMessageActivity(text, isEncrypt.isChecked());
						return true;
					}
				}

				text = editable.toString();
				edit.putString("edit_text", text);
		        edit.commit();
				return false;
			}
		});
    }

    public void goToMessageActivity(String text, boolean isChecked) {
    	Log.d("debug", "here2");
    	
		Intent intent = new Intent();
		intent.putExtra("message", text);
		intent.putExtra("isEncrypt", isChecked);
		intent.setClass(MainActivity.this, ShowMessageActivity.class);
		
		this.startActivity(intent);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
