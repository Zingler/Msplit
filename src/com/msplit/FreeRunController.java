package com.msplit;

import com.msplit.runmodel.Run;
import com.msplit.runmodel.RunSplit;
import com.msplit.urnmodel.Urn;
import com.msplit.urnmodel.UrnSplit;

import android.app.Activity;
import android.view.View;

public class FreeRunController extends AbstractRunController {
	
	public FreeRunController(Activity activity) {
		super(activity);
		this.run = new Run(true);
		this.runSplits = run.getRunSplits();
	}

	public boolean split() {
		boolean retval = super.split();
		if (getSplitIndex() == 1) {
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					activity.tapAnywhere.setVisibility(View.INVISIBLE);
				}
			});
		}
		return retval;
	}
	
	@Override
	public void start(){
		super.start();
		if (getSplitIndex() == 0) {
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					activity.tapAnywhere.setVisibility(View.VISIBLE);
				}
			});
		}
	}

	@Override
	public Urn createUrnFromRun() {
		Urn newUrn = new Urn();
		UrnSplit u;
		for (RunSplit r : runSplits) {
			u = new UrnSplit(null, r.getTime());
			newUrn.add(u);
			u.resetBestSegment();
		}
		return newUrn;
	}

	@Override
	public boolean hasDeltas() {
		return false;
	}

}
