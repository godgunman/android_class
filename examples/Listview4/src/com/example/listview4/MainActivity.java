package com.example.listview4;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.simpleadapter.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	private ListView listView;
	
	private String[] data1 = { "電腦", "電腦", "電腦", "電腦", "電腦", "電腦", "汽車", "汽車",
			"汽車", "汽車", "汽車" };
	
	private String[] data2 = { "個人電腦", "核心組件", "儲存裝置", "顯示設備", "電腦週邊", "機殼散熱",
			"義大利車系", "美國車系", "日本車系", "韓國車系", "台灣車系" };

	private int[] data3 = { R.drawable.pc, R.drawable.pc, R.drawable.pc,
			R.drawable.pc, R.drawable.pc, R.drawable.pc, R.drawable.car,
			R.drawable.car, R.drawable.car, R.drawable.car, R.drawable.car };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listView1);

		setListViewData2();
		// setListViewData();

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.e("TAG", "" + arg2);
			}
		});
	}

	private void setListViewData2() {
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < data1.length; i++) {

			HashMap<String, String> item = new HashMap<String, String>();
			item.put("category", data1[i]);
			item.put("items", data2[i]);

			list.add(item);
		}

		String[] from = new String[] { "category", "items" };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list,
				android.R.layout.simple_list_item_2, from, to);
		listView.setAdapter(simpleAdapter);
	}

	private void setListViewData() {
		ArrayList<HashMap<String, Object>> list2 = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < data1.length; i++) {

			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("category", data1[i]);
			item.put("items", data2[i]);
			item.put("pic", data3[i]);

			list2.add(item);
		}
		String[] from = new String[] { "category", "items", "pic" };
		int[] to = new int[] { R.id.category, R.id.items, R.id.imageView1 };

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list2,
				R.layout.listitem, from, to);

		listView.setAdapter(simpleAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
