package com.example.ui.simpleuifinal;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MessageAdapter extends BaseAdapter {

	private View[] data;

	public MessageAdapter(View[] data) {
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.length;
	}

	@Override
	public View getItem(int position) {
		return data[position];
	}

	@Override
	public long getItemId(int position) {
		return data[position].getId();
	}

	/* */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return data[position];
	}
}
