package com.msplit;

import com.msplit.R;
import com.msplit.runmodel.RunSplit;
import com.msplit.runmodel.SplitState;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RunSplitAdapter extends ArrayAdapter<RunSplit> {

	private int resource;
	private LayoutInflater inflater;
	private int red;
	private int green;
	private int gold;
	private AbstractRunController controller;

	public RunSplitAdapter(Context context, AbstractRunController controller) {

		super(context, R.layout.split, controller.getRunSplits());
		red = getContext().getResources().getColor(R.color.Red);
		green = getContext().getResources().getColor(R.color.Green);
		gold = getContext().getResources().getColor(R.color.Gold);
		this.resource = R.layout.split;
		this.inflater = LayoutInflater.from(context);
		this.controller = controller;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(resource, null);

		RunSplit item = (RunSplit) getItem(position);

		if (item.getState() == SplitState.CURRENT && controller.isRunning()) {
			convertView.setBackgroundResource(R.drawable.activesplit);
		}
		if (item.getState() == SplitState.FUTURE || (item.getState() == SplitState.CURRENT && !controller.isRunning())) {
			convertView.setAlpha((float) .6);
		} else {
			convertView.setAlpha(1);
		}
		TextView textviewName = (TextView) convertView.findViewById(R.id.name);
		TextView textviewTime = (TextView) convertView.findViewById(R.id.time);
		TextView textviewDelta = (TextView) convertView.findViewById(R.id.delta);

		textviewTime.setText("");
		if (item.getUrnSplit() == null) {
			textviewName.setText("");
			textviewDelta.setText(Util.formatTimerStringNoZeros(item.getTime()));
			return convertView;
		}
		textviewName.setText(item.getUrnSplit().getName());

		if (item.getState() != SplitState.PAST) {
			if (!item.getUrnSplit().isBlankSplit()) {
				textviewDelta.setText(Util.formatTimerStringNoZeros(item.getUrnSplit().getTime()));
			} else {
				textviewDelta.setText("");
			}
		} else {
			textviewTime.setText(Util.formatTimerStringNoZeros(item.getTime()));
			if (!item.getUrnSplit().isBlankSplit()) {
				int delta = item.getSplitDelta();
				String deltaString = Util.formatTimerStringNoZeros(delta, true);
				if (item.getSegmentTime() < item.getUrnSplit().getBestSegment()) {
					textviewDelta.setTextColor(gold);
				} else if (delta > 0) {
					textviewDelta.setTextColor(red);
				} else if (delta < 0) {
					textviewDelta.setTextColor(green);
				}
				textviewDelta.setText(deltaString);
			} else {
				textviewDelta.setText("__");
			}
		}
		return convertView;
	}
}
