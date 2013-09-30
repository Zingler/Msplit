package com.msplit.edit;

import java.io.IOException;

import com.msplit.R;
import com.msplit.urnmodel.Urn;
import com.msplit.urnmodel.UrnSplit;
import com.msplit.urnmodel.UrnUtil;
import com.msplit.util.Time;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class EditSplitActivity extends Activity implements OnItemClickListener {
	public static final String URN_TO_EDIT = "URN_TO_EDIT";
	private ListView editSplitListView;
	private UrnUtil urnUtil;
	private Urn urn;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editurn);
        editSplitListView = (ListView)findViewById(R.id.editsplitlistview);
        urn = (Urn)getIntent().getExtras().get(URN_TO_EDIT);
        EditSplitAdapter adapter = new EditSplitAdapter(this, urn.getSplits());
        editSplitListView.setAdapter(adapter);
        editSplitListView.setOnItemClickListener(this);
        urnUtil = new UrnUtil(this);
    }
	
	public void addSplit(View v){
		
	}
	
	public void saveClicked(View v){
		try {
			urnUtil.save(urn);
		} catch (IOException e) {
			Toast.makeText(this, "Was unable to save "+urn.getFilename(), Toast.LENGTH_LONG).show();
			return;
		}
		finish();
	}
	public void cancelClicked(View v){
		finish();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("Edit Split");
    	
		View v = this.getLayoutInflater().inflate(R.layout.editsplitdialog, null);
    	final UrnSplit split = (UrnSplit)parent.getItemAtPosition(position);
		
		final EditText name = (EditText)v.findViewById(R.id.editdialogname);
    	final NumberPicker hour = (NumberPicker)v.findViewById(R.id.editdialoghour);
    	final NumberPicker minute = (NumberPicker)v.findViewById(R.id.editdialogminute);
    	final NumberPicker second = (NumberPicker)v.findViewById(R.id.editdialogsecond);
    	final NumberPicker tenthsecond = (NumberPicker)v.findViewById(R.id.editdialogtenthsecond);
    	
    	name.setText(split.getName());
    	Time time = new Time(split.getTime());
    	hour.setValue(time.hour);
    	minute.setValue(time.minute);
    	second.setValue(time.second);
    	tenthsecond.setValue(time.tenthsecond);
    	
    	builder.setView(v);
    	builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Time newTime = new Time(hour.getValue(), minute.getValue(), second.getValue(), tenthsecond.getValue());
				String newName = name.getText().toString();
				split.setName(newName);
				split.setTime(newTime.getTimeAsInt());
				updateSplitList();
			}
		});
    	builder.setNegativeButton("Cancel", null);
    	builder.create().show();
	}
	
	public void updateSplitList(){
		((BaseAdapter)editSplitListView.getAdapter()).notifyDataSetChanged();
	}
	
	
	@Override 
	public void finish(){ 
		super.finish();
		overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_right);
	}
}
