package com.example.zsplit.edit;

import java.util.List;

import com.example.zsplit.R;
import com.example.zsplit.Util;
import com.example.zsplit.urnmodel.UrnSplit;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class EditSplitAdapter extends ArrayAdapter<UrnSplit> {
	private int resource;
	private LayoutInflater inflater;
	
	public EditSplitAdapter (Context context, List<UrnSplit> values) {

	    super(context, R.layout.split, values);
	    this.resource = R.layout.editsplit;
	    this.inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    convertView = inflater.inflate(resource, null);

	    UrnSplit item = (UrnSplit) getItem(position);

	    TextView textviewName = (TextView) convertView.findViewById(R.id.name);
	    TextView textviewTime = (TextView) convertView.findViewById(R.id.time);
	   
	    textviewName.setText(item.getName());
	    textviewTime.setText(Util.formatTimerStringNoZeros(item.getTime()));
	   
	    return convertView;
	}
}

