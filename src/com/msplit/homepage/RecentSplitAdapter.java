package com.msplit.homepage;

import java.util.List;

import com.msplit.R;
import com.msplit.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RecentSplitAdapter extends ArrayAdapter<RecentSplit> {

	private int resource;
	private LayoutInflater inflater;

	public RecentSplitAdapter(Context context, List<RecentSplit> values) {

		super(context, R.layout.split, values);
		this.resource = R.layout.recentsplit;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(resource, null);

		RecentSplit item = (RecentSplit) getItem(position);

		TextView textviewName = (TextView) convertView.findViewById(R.id.recentsplitname);
		textviewName.setText(item.getFilename());
		
		TextView textviewTime = (TextView) convertView.findViewById(R.id.recentsplittime);
		textviewTime.setText(Util.formatTimerStringNoZeros(item.getTime()));

		return convertView;
	}
}
