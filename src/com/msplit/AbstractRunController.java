package com.msplit;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.msplit.R;
import com.msplit.runmodel.Run;
import com.msplit.runmodel.RunSplit;
import com.msplit.runmodel.SplitState;
import com.msplit.urnmodel.Urn;
import com.msplit.urnmodel.UrnSplit;

import android.app.Activity;
import android.widget.TextView;

public abstract class AbstractRunController {

	public Timer refresher;
	public Stopwatch stopwatch;
	protected boolean isRunning = false;
	boolean isFreshStart = true;
	public int splitIndex = 0;
	protected MainActivity activity;
	TextView maintimer;
	Run run;
	
	protected List<RunSplit> runSplits;

	public AbstractRunController(Activity activity) {
		this.activity = (MainActivity) activity;
		maintimer = (TextView) activity.findViewById(R.id.maintimer);
		stopwatch = new Stopwatch();
		
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				maintimer.setText(Util.formatTimerString(0));
			}
		});
	}

	public void start() {
		if (!isRunning) {
			refresher = new Timer();
			refresher.scheduleAtFixedRate(new Ticker(this), 100, 100);
			stopwatch.start();
			activity.updateSplitList();
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

	public void cleanUp() {
		if (refresher != null) {
			refresher.cancel();
		}
	}

	public boolean split() {
		boolean didSplit = run.split(stopwatch.getTimeInTenths());
		if(!didSplit) {
			return false;
		}
		activity.scrollToSplit(splitIndex);
		if (run.isDone()) {
			stop();
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
		return run.getRunDelta();
	}

	public List<RunSplit> getRunSplits() {
		return run.getRunSplits();
	}

	public abstract Urn createUrnFromRun();

	class Ticker extends TimerTask {
		AbstractRunController urn;

		public Ticker(AbstractRunController urn) {
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
