package com.msplit;

import java.util.List;

import com.msplit.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SplitRowAdapter extends ArrayAdapter<SplitRow> {

	private int resource;
	private LayoutInflater inflater;

	public SplitRowAdapter(Context context, List<SplitRow> values) {

		super(context, R.layout.split, values);

		this.resource = R.layout.split;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(resource, null);

		SplitRow item = (SplitRow) getItem(position);

		TextView textviewName = (TextView) convertView.findViewById(R.id.name);
		TextView textviewTime = (TextView) convertView.findViewById(R.id.time);

		if (item.getUrnSplit() == null) {
			textviewTime.setText(Util.formatTimerStringNoZeros(item.getRunSplit().getTime()));
			return convertView;
		}
		textviewName.setText(item.getUrnSplit().getName());

		if (item.getRunSplit() == null) {
			textviewTime.setText(Util.formatTimerStringNoZeros(item.getUrnSplit().getTime()));
		} else {
			int delta = item.getRunSplit().getTime() - item.getUrnSplit().getTime();
			String deltaString = Util.formatTimerStringNoZeros(delta, true);
			if (delta > 0) {
				textviewTime.setTextColor(Color.RED);
			} else if (delta < 0) {
				textviewTime.setTextColor(0xFF2AB834);
			}
			textviewTime.setText(deltaString);
		}
		return convertView;
	}
}
