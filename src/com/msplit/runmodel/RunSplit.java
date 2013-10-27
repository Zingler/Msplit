package com.msplit.runmodel;

import com.msplit.urnmodel.UrnSplit;

public class RunSplit {
	private int time;
	private UrnSplit urnSplit;
	private int segmentTime;
	private SplitState state;

	public RunSplit() {}
	
	public RunSplit(UrnSplit urnSplit) {
		this.urnSplit = urnSplit;
		state = SplitState.FUTURE;
	}

	public RunSplit(int time) {
		this.time = time;
		state = SplitState.PAST;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setState(SplitState state) {
		this.state = state;
	}

	public SplitState getState() {
		return state;
	}

	public UrnSplit getUrnSplit() {
		return urnSplit;
	}

	public int getSegmentTime() {
		return segmentTime;
	}

	protected void setSegmentTime(int segmentTime) {
		this.segmentTime = segmentTime;
	}

	public int getSplitDelta() {
		if (urnSplit != null) {
			return time - urnSplit.getTime();
		} else {
			return 0;
		}
	}
	
	public boolean isGoldSplit(){
		if(urnSplit != null && state == SplitState.PAST){
			return segmentTime < urnSplit.getBestSegment();
		} else {
			return false;
		}
	}
}
