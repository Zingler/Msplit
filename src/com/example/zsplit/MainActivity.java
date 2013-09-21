package com.example.zsplit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import com.example.zsplit.splitmodel.SplitList;
import com.example.zsplit.splitmodel.SplitListUtil;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    public int counter = 0;
    public TextView mainTimer;
    public Timer stopwatch;
	public LinearLayout splitTable;
	public SplitListUtil splitListUtil;
	public Run run;
	public SplitList splits;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainTimer = (TextView)findViewById(R.id.maintimer);
        splitListUtil = new SplitListUtil(this);
        
        splits = splitListUtil.SampleSplitList();
        changeToRun(splits);
    }

	private void changeToRun(SplitList splits) {
		splitTable = (LinearLayout)findViewById(R.id.splittable);
        splitTable.removeAllViews();
        
        ArrayList<SplitRow> splitRows = SplitViewGenerator.generateSplitViews(this, splits);
        for(SplitRow row : splitRows){
        	splitTable.addView(row);
        }

        run = new Run(this, splits, splitRows);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.save_split:
        	createSaveDialog().show();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    public void startButtonClicked(View view) {
        run.start();
        if(run.isRunning){
        	((TextView)view).setText("Pause");
        } else {
        	((TextView)view).setText("Start");
        }
        
    }
    public void resetButtonClicked(View view) {
        run.reset();
    }
    public void splitButtonClicked(View view) {
        run.split();
    }
    
    public AlertDialog createSaveDialog(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Save as...");
    	View v = this.getLayoutInflater().inflate(R.layout.savedialog, null);
    	final EditText filename = ((EditText)v.findViewById(R.id.save_filename));
    	if(splits.filename!=null){
    		filename.setText(splits.filename);
    	}
    	builder.setView(v);
    	builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				splits.filename = filename.getText().toString();
				try{
					splitListUtil.save(splits);
				} catch(IOException e){
					Toast.makeText(getApplicationContext(), "Could not save the file", Toast.LENGTH_SHORT).show();
				}
			}
		});
    	builder.setNegativeButton("Cancel", null);
    	return builder.show();
    }
}
