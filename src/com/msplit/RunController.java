package com.msplit;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;

import com.msplit.runmodel.Run;
import com.msplit.runmodel.RunSplit;
import com.msplit.runmodel.SplitState;
import com.msplit.urnmodel.Urn;
import com.msplit.urnmodel.UrnSplit;

public class RunController extends AbstractRunController {
	
	public RunController(Activity activity, Urn urn){
		super(activity);
		this.urn = urn;
		this.run = new Run(urn);
	}
	
	@Override
	public Urn createUrnFromRun() {
		Urn newUrn = new Urn();
		newUrn.setFilename(urn.getFilename());
		int time;
		int bestSegment;
		for (RunSplit r : getRunSplits()) {
			if (r.getState() == SplitState.PAST) {
				time = r.getTime();
			} else {
				time = r.getUrnSplit().getTime();
			}
			
			if (r.getState() == SplitState.PAST && r.getSegmentTime() < r.getUrnSplit().getBestSegment()){
				bestSegment = r.getSegmentTime();
			} else {
				bestSegment = r.getUrnSplit().getBestSegment();
			}
			UrnSplit u = new UrnSplit(r.getUrnSplit().getName(), time);
			u.setBestSegment(bestSegment);
			newUrn.add(u);
		}
		return newUrn;
	}

	@Override
	public boolean hasDeltas() {
		return true;
	}

	@Override
	public List<RunSplit> getFilledBlankSplits() {
		LinkedList<RunSplit> list = new LinkedList<RunSplit>();
		for(RunSplit r : getRunSplits()){
			if(r.getUrnSplit().isBlankSplit() && r.getState() == SplitState.PAST){
				list.add(r);
			}
		}
		return list;
	}
}
