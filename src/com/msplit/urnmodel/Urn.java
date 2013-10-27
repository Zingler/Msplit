package com.msplit.urnmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Urn implements Serializable {
	private static final long serialVersionUID = 1L;
	private String filename = "";
	private List<UrnSplit> splits;

	public Urn() {
		splits = new ArrayList<UrnSplit>();
	}

	public Urn(List<UrnSplit> splits) {
		super();
		this.splits = splits;
	}

	public Urn(String filename, List<UrnSplit> splits) {
		super();
		this.filename = filename;
		this.splits = splits;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<UrnSplit> getSplits() {
		return splits;
	}

	public void setSplits(List<UrnSplit> splits) {
		this.splits = splits;
		sort();
	}

	public void add(UrnSplit u) {
		splits.add(u);
		sort();
	}
	
	public int getOverallTime(){
		if(splits.size()>0){
			return splits.get(splits.size()-1).getTime();
		} else {
			return 0;
		}
	}

	public void sort() {
		Collections.sort(splits);
		fixUrnSplits();
	}
	
	public void fixUrnSplits() {
		if(splits != null) {
			int i = 0;
			for (UrnSplit u : splits){
				u.setUrn(this);
				u.setIndex(i);
				if(!u.isBestSegmentValid()) {
					u.setBestSegment(u.getSegmentTime());
				}
				i++;
			}
		}
	}
}
