package com.example.zsplit;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.example.zsplit.urnmodel.Urn;
import com.example.zsplit.urnmodel.UrnSplit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class FreeRun extends Run{

	private MainActivity mainActivity;
	private ScrollView scroller;

	public FreeRun(Activity activity){
		super(activity, null, new ArrayList<SplitRow>());
		runSplits = new ArrayList<RunSplit>();
		mainActivity = (MainActivity)activity;
		scroller = (ScrollView)mainActivity.findViewById(R.id.scrollView1);
	}
	
	public void stop() {
		stopwatch.cancel();
		isRunning = false;
	}

	public void reset() {
		stopwatch.cancel();
		isRunning = false;
		time = 0;
		splitIndex = 0;
		splits.clear();
		runSplits.clear();
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				maintimer.setText(Util.formatTimerString(0));
				((ViewGroup)scroller.getChildAt(0)).removeAllViews();
			}
		});
	}
	
	public void split(){
		int splitTime = time;
		runSplits.add(new RunSplit(time));
		splitIndex++;
		
		final SplitRow s = new SplitRow(activity, null, new UrnSplit("", splitTime));
		splits.add(s);
		
		activity.runOnUiThread(new Runnable(){
			public void run() {
				mainActivity.splitTable.addView(s);
			}
		});
	}
	
	@Override
	public Urn createUrnFromRun(){
		Urn newUrn = new Urn();
		for(RunSplit r : runSplits){
			newUrn.add(new UrnSplit(null, r.time));
		}
		return newUrn;
	}

	
}
