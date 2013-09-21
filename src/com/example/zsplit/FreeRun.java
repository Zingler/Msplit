package com.example.zsplit;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.example.zsplit.splitmodel.Split;

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
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				maintimer.setText(Util.formatTimerString(0));
				((ViewGroup)scroller.getChildAt(0)).removeAllViews();
			}
		});
	}
	
	public void split(){
		if(!isRunning){
			return;
		}
		int splitTime = time;
		splitIndex++;
		
		final SplitRow s = new SplitRow(activity, null, new Split("", splitTime));
		splits.add(s);
		
		activity.runOnUiThread(new Runnable(){
			public void run() {
				mainActivity.splitTable.addView(s);
			}
		});
	}

	
}
