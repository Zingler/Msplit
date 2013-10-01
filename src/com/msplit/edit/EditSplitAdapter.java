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
	
	public EditSplitAdapter (Context context, List<UrnSplit> values) {

	    super(context, R.layout.split, values);
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
	    Button remove = (Button) convertView.findViewById(R.id.editsplitremove);
	    remove.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				splits.remove(position);
				EditSplitAdapter.this.notifyDataSetChanged();
			}
		});
	   
	    textviewName.setText(item.getName());
	    textviewTime.setText(Util.formatTimerStringNoZeros(item.getTime()));
	   
	    return convertView;
	}
}

