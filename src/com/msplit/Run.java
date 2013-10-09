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

	public Timer refresher;
	public Stopwatch stopwatch;
	protected boolean isRunning = false;
	boolean isFreshStart = true;
	public int splitIndex = 0;
	protected MainActivity activity;
	TextView maintimer;
	List<SplitRow> splits;
	private Urn urn;
	protected ArrayList<RunSplit> runSplits;
	private int delta = 0;

	public Run() {
	}

	public Run(Activity activity, Urn urn, List<SplitRow> splitsview) {
		this.activity = (MainActivity) activity;
		this.urn = urn;
		this.splits = splitsview;
		maintimer = (TextView) activity.findViewById(R.id.maintimer);
		stopwatch = new Stopwatch();
	}

	public void start() {
		if (!isRunning) {
			refresher = new Timer();
			refresher.scheduleAtFixedRate(new Ticker(this), 100, 100);
			stopwatch.start();
			if(isFreshStart && splits.size()>0){
				splits.get(0).setState(SplitState.CURRENT);
			}
			activity.updateSplitList();
			isFreshStart = false;
			isRunning = true;
		} else {
			refresher.cancel();
			stopwatch.stop();
			isRunning = false;
		}
	}

	public void stop() {
		if (refresher != null) {
			refresher.cancel();
			stopwatch.stop();
		}
		isRunning = false;
	}

	public void reset() {
		if (refresher != null) {
			refresher.cancel();
		}
		isRunning = false;
		stopwatch.reset();
		splitIndex = 0;
		delta = 0;
		for (SplitRow s : splits) {
			s.reset();
		}
		isFreshStart = true;
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
		activity.scrollToSplit(splitIndex);
		SplitRow s = splits.get(splitIndex);
		s.setRunSplit(new RunSplit(stopwatch.getTimeInTenths()));
		if(splitIndex==0){
			this.delta = (s.getRunSplit().getTime()-s.getUrnSplit().getTime());
		} else {
			this.delta = (s.getRunSplit().getTime()-s.getUrnSplit().getTime()) - 
					     (splits.get(splitIndex-1).getRunSplit().getTime() - splits.get(splitIndex-1).getUrnSplit().getTime());
		}
		s.setState(SplitState.PAST);
		splitIndex++;
		if (splitIndex == splits.size()) {
			stop();
		} else {
			splits.get(splitIndex).setState(SplitState.CURRENT);
		}
		activity.updateSplitList();
		return true;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}

	public Urn createUrnFromRun() {
		Urn newUrn = new Urn();
		newUrn.setFilename(urn.getFilename());
		for (int i = 0; i < splits.size(); i++) {
			int time;
			if (splits.get(i).getRunSplit() != null) {
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
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					maintimer.setText(Util.formatTimerString(stopwatch.getTimeInTenths()));
				}
			});
		}
	}
}
