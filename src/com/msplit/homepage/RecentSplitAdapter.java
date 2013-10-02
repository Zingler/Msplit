package com.msplit.homepage;

import java.util.List;

import com.msplit.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RecentSplitAdapter extends ArrayAdapter<String> {

	private int resource;
	private LayoutInflater inflater;

	public RecentSplitAdapter(Context context, List<String> values) {

		super(context, R.layout.split, values);
		this.resource = R.layout.recentsplit;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(resource, null);

		String item = (String) getItem(position);

		TextView textviewName = (TextView) convertView.findViewById(R.id.recentsplitname);
		textviewName.setText(item);

		return convertView;
	}
}
