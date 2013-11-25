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
		boolean first = true;
		for (UrnSplit split : urn.getSplits()) {
			r = new RunSplit(split);
			runSplits.add(r);
			if(first){
				r.setState(SplitState.CURRENT);
				first = false;
			}
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
		RunSplit prev, prev2;
		int i = index - 1;
		while (i>=0 && runSplits.get(i).getUrnSplit().isBlankSplit()){
			i--;
		}
		if(i>=0){
			prev = runSplits.get(i);
		} else {
			prev = null;
		}
		i--;
		while (i>=0 && runSplits.get(i).getUrnSplit().isBlankSplit()){
			i--;
		}
		if(i>=0){
			prev2 = runSplits.get(i);
		} else {
			prev2 = null;
		}
		if(prev != null && prev2 != null){
			return prev.getSplitDelta() - prev2.getSplitDelta();
		} else if (prev != null) {
			return prev.getSplitDelta();
		} else {
			return 0;
		}
		
		
		/*if(index==0) {
			return 0;
		} else if (index == 1){
			return runSplits.get(0).getSplitDelta();
		} else {
			return runSplits.get(index-1).getSplitDelta() - runSplits.get(index-2).getSplitDelta();
		}*/
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

	public int getSplitIndex() {
		return index;
	}
	
	public int getOverallTime() {
		if(runSplits.size()>0){
			return runSplits.get(runSplits.size()-1).getTime();
		} else {
			return 0;
		}
	}
	
	public boolean hasGoldSplits() {
		for(RunSplit r : runSplits){
			if(r.isGoldSplit()){
				return true;
			}
		}
		return false;
	}
	
	public boolean isNewUrn() {
		if(isDone() && getOverallTime() < urn.getOverallTime()){
			return true;
		} else {
			return false;
		}
	}

	public boolean hasBlankSplits() {
		for(UrnSplit u : urn.getSplits()){
			if (u.isBlankSplit()) {
				return true;
			}
		}
		return false;
	}
}
