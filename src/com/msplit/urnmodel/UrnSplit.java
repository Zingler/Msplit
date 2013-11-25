package com.msplit.urnmodel;

import java.io.Serializable;
import java.util.List;

public class UrnSplit implements Comparable<UrnSplit>, Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private int time;
	private Integer bestSegment = null;
	private boolean blankSplit;
	transient private Urn urn;
	transient private int index;
	
	public UrnSplit(String title, int time) {
		this.name = title;
		this.time = time;
	}

	public UrnSplit(UrnSplit urnSplit) {
		this.name = urnSplit.getName();
		this.time = urnSplit.getTime();
		this.bestSegment = urnSplit.getTime();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public int getSegmentTime(){
		if(index == 0){
			return time;
		} else {
			int i = index - 1;
			while(i >= 0 && urn.getSplits().get(i).isBlankSplit()){
				i--;
			}
			if(i == -1){
				return time;
			} else {
				return time - urn.getSplits().get(i).getTime();
			}
		}
	}

	public int getBestSegment() {
		return bestSegment;
	}

	public void setBestSegment(int bestSegment) {
		this.bestSegment = bestSegment;
	}
	
	public boolean isBlankSplit() {
		return blankSplit;
	}

	public void setBlankSplit(boolean blankSplit) {
		this.blankSplit = blankSplit;
	}

	public void resetBestSegment() {
		bestSegment = getSegmentTime();
	}

	public Urn getUrn() {
		return urn;
	}

	public void setUrn(Urn urn) {
		this.urn = urn;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public int compareTo(UrnSplit another) {
		return this.time - another.time;
	}

	public boolean isBestSegmentValid() {
		return bestSegment != null;
	}
	
	public void invalidateBestSegment() {
		bestSegment = null;
	}

	public void remove() {
		if(urn != null){
			urn.getSplits().remove(index);
			if(urn.getSplits().size() > index){
				urn.getSplits().get(index).invalidateBestSegment();
			}
			urn.fixUrnSplits();
		}
	}

	public UrnSplit getNext() {
		Urn urn = getUrn();
		if (urn != null) {
			List<UrnSplit> splits = urn.getSplits();
			if(getIndex()+1 < splits.size()){
				return splits.get(getIndex()+1);
			} else {
				return null;
			}
		} else {
			return null;
		}
		
	}

}
