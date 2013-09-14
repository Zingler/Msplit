package com.example.zsplit;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

public class Run {

	public Timer stopwatch;
	public boolean isRunning = false;
	public int time = 0;
	public int splitIndex = 0;
	protected Activity activity;
	TextView maintimer;
	ArrayList<SplitRow> splits;

	public Run(Activity activity, ArrayList<SplitRow> splits){
		this.activity = activity;
		this.splits = splits;
		maintimer = (TextView)activity.findViewById(R.id.maintimer);
	}
	
	public void start() {
		if (!isRunning) {
			stopwatch = new Timer();
			stopwatch.scheduleAtFixedRate(new Ticker(this), 100, 100);
			isRunning = true;
		} else {
			stopwatch.cancel();
			isRunning = false;
		}
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
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				maintimer.setText(Util.formatTimerString(0));
				for(SplitRow s : splits){
					s.resetTimeText();
				}
			}
		});
	}
	
	public void split(){
		if(!isRunning){
			return;
		}
		SplitRow s = splits.get(splitIndex);
		splitIndex++;
		if(splitIndex >= splits.size()){
			stop();
		}
		
		int splitTime = time;
		final int delta = splitTime - s.split.time;
		final String deltaString = Util.formatTimerStringNoZeros(delta, true);
		final TextView t = (TextView)s.timeView;
		activity.runOnUiThread(new Runnable(){
			public void run() {
				if(delta>0){
					t.setTextColor(Color.RED);
				} else if (delta < 0){
					t.setTextColor(0xFF2AB834);
				} else {
					t.setTextColor(Color.BLACK);
				}
				t.setText(deltaString);
			}
		});
	}

	class Ticker extends TimerTask {
		Run urn;

		public Ticker(Run urn) {
			this.urn = urn;
		}

		@Override
		public void run() {
			urn.time++;
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					maintimer.setText(Util.formatTimerString(urn.time));
				}
			});
		}
	}
}
