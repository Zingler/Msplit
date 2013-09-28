package com.example.zsplit;

import java.util.ArrayList;
import java.util.List;
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
