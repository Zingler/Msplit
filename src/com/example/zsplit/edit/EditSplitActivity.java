package com.example.zsplit.edit;

import java.io.IOException;

import com.example.zsplit.R;
import com.example.zsplit.urnmodel.Urn;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class EditSplitActivity extends Activity implements OnItemClickListener {
	public static final String URN_TO_EDIT = "URN_TO_EDIT";
	private ListView editSplitListView;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editurn);
        editSplitListView = (ListView)findViewById(R.id.editsplitlistview);
        Urn urn = (Urn)getIntent().getExtras().get(URN_TO_EDIT);
        EditSplitAdapter adapter = new EditSplitAdapter(this, urn.getSplits());
        editSplitListView.setAdapter(adapter);
        editSplitListView.setOnItemClickListener(this);
    }
	
	public void saveClicked(View v){
		finish();
	}
	public void cancelClicked(View v){
		finish();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Toast.makeText(this, "Something was touched", Toast.LENGTH_SHORT).show();
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("Edit Split");
    	View v = this.getLayoutInflater().inflate(R.layout.editsplitdialog, null);
    	builder.setView(v);
    	builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				/*String newName = filename.getText().toString();
				try{
					Urn newUrn = run.createUrnFromRun();
					newUrn.setFilename(newName);
					splitListUtil.save(newUrn);
					changeToRun(newUrn);
				} catch(IOException e){
					Toast.makeText(getApplicationContext(), "Could not save the file", Toast.LENGTH_SHORT).show();
				}*/
			}
		});
    	builder.setNegativeButton("Cancel", null);
    	builder.create().show();
	}
	
	
	@Override 
	public void finish(){ 
		super.finish();
		overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_right);
	}
}
