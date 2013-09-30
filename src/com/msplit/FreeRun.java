package com.msplit;


import java.util.List;

import com.msplit.urnmodel.Urn;
import com.msplit.urnmodel.UrnSplit;

import android.app.Activity;

public class FreeRun extends Run{

	private MainActivity mainActivity;

	public FreeRun(Activity activity, List<SplitRow> splitRows){
		this.splits = splitRows;
		mainActivity = (MainActivity)activity;
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
			}
		});
	}
	
	public void split(){
		splits.add(new SplitRow(null, new RunSplit(time)));
		splitIndex++;
	}
	
	@Override
	public Urn createUrnFromRun(){
		Urn newUrn = new Urn();
		for(SplitRow r : splits){
			newUrn.add(new UrnSplit(null, r.getRunSplit().getTime()));
		}
		return newUrn;
	}

	
}
