package com.msplit;

import java.util.List;

import com.msplit.urnmodel.Urn;
import com.msplit.urnmodel.UrnSplit;

import android.app.Activity;
import android.widget.TextView;

public class FreeRun extends Run {

	public FreeRun(Activity activity, List<SplitRow> splitRows) {
		this.splits = splitRows;
		this.activity = (MainActivity) activity;
		maintimer = (TextView) activity.findViewById(R.id.maintimer);
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
		splits.clear();
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				maintimer.setText(Util.formatTimerString(0));
			}
		});
	}

	public void split() {
		splits.add(new SplitRow(new UrnSplit(null,time), null));
		splitIndex++;
		activity.updateSplitList();
	}

	@Override
	public Urn createUrnFromRun() {
		Urn newUrn = new Urn();
		for (SplitRow r : splits) {
			newUrn.add(new UrnSplit(null, r.getRunSplit().getTime()));
		}
		return newUrn;
	}

}
