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
import android.widget.TextView;

public class Run {

	public Timer stopwatch;
	public boolean isRunning = false;
	public int time = 0;
	public int splitIndex = 0;
	protected Activity activity;
	TextView maintimer;
	ArrayList<SplitRow> splits;
	private Urn urn;
	protected ArrayList<RunSplit> runSplits;

	public Run(Activity activity, Urn urn, ArrayList<SplitRow> splitsview){
		this.activity = activity;
		this.urn = urn;
		this.splits = splitsview;
		this.runSplits = new ArrayList<RunSplit>();
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
		if(stopwatch!=null){
			stopwatch.cancel();
		}
		isRunning = false;
		time = 0;
		splitIndex = 0;
		runSplits = new ArrayList<RunSplit>();
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
		SplitRow s = splits.get(splitIndex);
		splitIndex++;
		if(splitIndex >= splits.size()){
			stop();
		}
		
		int splitTime = time;
		runSplits.add(new RunSplit(time));
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

	public Urn createUrnFromRun(){
		Urn newUrn = new Urn();
		newUrn.filename = urn.filename;
		for(int i=0; i<runSplits.size(); i++){
			newUrn.add(new UrnSplit(urn.get(i).name, runSplits.get(i).time));
		}
		for(int i=runSplits.size(); i<urn.size(); i++){
			newUrn.add(new UrnSplit(urn.get(i).name, urn.get(i).time));
		}
		return newUrn;
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
