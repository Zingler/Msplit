package com.msplit.edit;

import java.util.List;

import com.msplit.R;
import com.msplit.Util;
import com.msplit.urnmodel.UrnSplit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class EditSplitAdapter extends ArrayAdapter<UrnSplit> {
	private int resource;
	private LayoutInflater inflater;
	private List<UrnSplit> splits;

	public EditSplitAdapter(Context context, List<UrnSplit> values) {

		super(context, R.layout.editsplit, values);
		this.resource = R.layout.editsplit;
		this.inflater = LayoutInflater.from(context);
		this.splits = values;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(resource, null);

		UrnSplit item = (UrnSplit) getItem(position);

		TextView textviewName = (TextView) convertView.findViewById(R.id.editname);
		TextView textviewTime = (TextView) convertView.findViewById(R.id.edittime);
		TextView textViewSegment = (TextView) convertView.findViewById(R.id.editsegment);
		TextView textViewBestSegment = (TextView) convertView.findViewById(R.id.editbestsegment);
		Button remove = (Button) convertView.findViewById(R.id.editsplitremove);
		remove.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				splits.get(position).remove();
				EditSplitAdapter.this.notifyDataSetChanged();
			}
		});

		textviewName.setText(item.getName());
		if(item.isBlankSplit()) {
			textviewTime.setText("");
			textViewSegment.setText("");
			textViewBestSegment.setText("");
		} else {
			textviewTime.setText(Util.formatTimerStringNoZeros(item.getTime()));
			textViewSegment.setText(Util.formatTimerStringNoZeros(item.getSegmentTime()));
			textViewBestSegment.setText(Util.formatTimerStringNoZeros(item.getBestSegment()));
		}

		return convertView;
	}
}
