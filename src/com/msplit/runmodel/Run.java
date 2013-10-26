package com.msplit.runmodel;

import java.util.ArrayList;
import java.util.List;

import com.msplit.urnmodel.Urn;
import com.msplit.urnmodel.UrnSplit;

public class Run {
	private List<RunSplit> runSplits = new ArrayList<RunSplit>();
	private Urn urn;
	private int index;
	private boolean freeRun;
	
	public Run(boolean freeRun) {
		this.index = 0;
		this.freeRun = freeRun;
	}

	public Run(Urn urn) {
		this.urn = urn;
		this.freeRun = false;
		this.index = 0;
		RunSplit r;
		for (UrnSplit split : urn.getSplits()) {
			r = new RunSplit(split);
			runSplits.add(r);
		}
	}

	public boolean split(int time) {
		if(!freeRun && index >= runSplits.size()){
			return false;
		}
		RunSplit r;
		if(freeRun){
			r = new RunSplit();
			runSplits.add(r);
		} else {
			r = runSplits.get(index);
		}
		r.setTime(time);
		r.setState(SplitState.PAST);
		
		if(index==0){
			r.setSegmentTime(time);
		} else {
			r.setSegmentTime(time - runSplits.get(index-1).getTime());
		}

		index++;
		if (!freeRun && index < runSplits.size()) {
			r = runSplits.get(index);
			r.setState(SplitState.CURRENT);
		}
		return true;
	}
	
	public int getRunDelta(){
		if(index==0) {
			return 0;
		} else if (index == 1){
			return runSplits.get(1).getSplitDelta();
		} else {
			return runSplits.get(index-2).getSplitDelta() - runSplits.get(index-1).getSplitDelta();
		}
	}

	public boolean isDone() {
		if (freeRun || index < runSplits.size()){
			return false;
		} else {
			return true;
		}
	}

	public List<RunSplit> getRunSplits() {
		return runSplits;
	}
}
