package com.example.zsplit;

import java.util.ArrayList;
import java.util.Timer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    public int counter = 0;
    public TextView mainTimer;
    public Timer stopwatch;
	public LinearLayout splitList;
	Run run;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainTimer = (TextView)findViewById(R.id.maintimer);
        splitList = (LinearLayout)findViewById(R.id.splittable);
        splitList.removeAllViews();
        ArrayList<SplitRow> splitRows = new ArrayList<SplitRow>();
        
        SplitRow s;
        s = new SplitRow(this, null, new Split("Escape", 2966));
        splitRows.add(s); 
        splitList.addView(s);
        
        s = new SplitRow(this, null, new Split("Kakriko", 3685));
        splitRows.add(s);
        splitList.addView(s);
        
        s = new SplitRow(this, null, new Split("Bottle", 4971));
        splitRows.add(s);
        splitList.addView(s);
        
        s = new SplitRow(this, null, new Split("Deku", 6308));
        splitRows.add(s);
        splitList.addView(s);
        
        s = new SplitRow(this, null, new Split("Gohma", 7648));
        splitRows.add(s);
        splitList.addView(s);
        
        s = new SplitRow(this, null, new Split("Ganandorf", 8366));
        splitRows.add(s);
        splitList.addView(s);
        
        s = new SplitRow(this, null, new Split("Collapse", 9582));
        splitRows.add(s);
        splitList.addView(s);
        
        s = new SplitRow(this, null, new Split("Ganon", 11763));
        splitRows.add(s);
        splitList.addView(s);
        
        //run = new FreeRun(this);
        run = new Run(this, splitRows);
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
}
