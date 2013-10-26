package com.msplit;

import android.app.Activity;

import com.msplit.runmodel.Run;
import com.msplit.runmodel.RunSplit;
import com.msplit.runmodel.SplitState;
import com.msplit.urnmodel.Urn;
import com.msplit.urnmodel.UrnSplit;

public class RunController extends AbstractRunController {
	private Urn urn;
	
	public RunController(Activity activity, Urn urn){
		super(activity);
		this.urn = urn;
		this.run = new Run(urn);
	}
	
	@Override
	public Urn createUrnFromRun() {
		Urn newUrn = new Urn();
		newUrn.setFilename(urn.getFilename());
		for (RunSplit r : runSplits) {
			int time;
			if (r.getState() == SplitState.PAST || r.getUrnSplit() == null) {
				time = r.getTime();
			} else {
				time = r.getUrnSplit().getTime();
			}
			newUrn.add(new UrnSplit(r.getUrnSplit().getName(), time));
		}
		return newUrn;
	}

	@Override
	public boolean hasDeltas() {
		return true;
	}
}
