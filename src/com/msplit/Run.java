package com.msplit;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.msplit.R;
import com.msplit.urnmodel.Urn;
import com.msplit.urnmodel.UrnSplit;

import android.app.Activity;
import android.widget.TextView;

public class Run {

	public Timer stopwatch;
	public boolean isRunning = false;
	public int time = 0;
	public int splitIndex = 0;
	protected MainActivity activity;
	TextView maintimer;
	List<SplitRow> splits;
	private Urn urn;
	protected ArrayList<RunSplit> runSplits;

	public Run() {
	}

	public Run(Activity activity, Urn urn, List<SplitRow> splitsview) {
		this.activity = (MainActivity) activity;
		this.urn = urn;
		this.splits = splitsview;
		maintimer = (TextView) activity.findViewById(R.id.maintimer);
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
		if (stopwatch != null) {
			stopwatch.cancel();
		}
		isRunning = false;
	}

	public void reset() {
		if (stopwatch != null) {
			stopwatch.cancel();
		}
		isRunning = false;
		time = 0;
		splitIndex = 0;
		for (SplitRow s : splits) {
			s.reset();
		}
		activity.updateSplitList();
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				maintimer.setText(Util.formatTimerString(0));
			}
		});
	}

	public boolean split() {
		if (splitIndex >= splits.size()) {
			return false;
		}
		SplitRow s = splits.get(splitIndex);
		s.setRunSplit(new RunSplit(time));
		splitIndex++;
		if (splitIndex == splits.size()) {
			stop();
		}
		activity.updateSplitList();
		return true;
	}

	public Urn createUrnFromRun() {
		Urn newUrn = new Urn();
		newUrn.setFilename(urn.getFilename());
		for(int i=0; i < splits.size(); i++){
			int time;
			if(splits.get(i).getRunSplit()!=null){
				time = splits.get(i).getRunSplit().getTime();
			} else {
				time = splits.get(i).getUrnSplit().getTime();
			}
			newUrn.add(new UrnSplit(splits.get(i).getUrnSplit().getName(), time));
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
