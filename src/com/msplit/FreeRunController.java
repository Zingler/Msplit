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
	public Urn createUrnFromRun() {
		Urn newUrn = new Urn();
		for (RunSplit r : runSplits) {
			newUrn.add(new UrnSplit(null, r.getTime()));
		}
		return newUrn;
	}

	@Override
	public boolean hasDeltas() {
		return false;
	}

}
