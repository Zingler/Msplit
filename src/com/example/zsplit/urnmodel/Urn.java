package com.example.zsplit.urnmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Urn implements Serializable{
	private static final long serialVersionUID = 1L;
	private String filename="";
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
	}
	public void add(UrnSplit u){
		splits.add(u);
	}
}
