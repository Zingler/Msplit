package com.msplit.urnmodel;

import java.io.Serializable;

public class UrnSplit implements Serializable, Comparable<UrnSplit>{
	private static final long serialVersionUID = 1L;
	private String name;
	private int time;

	public UrnSplit(String title, int time){
		this.name = title;
		this.time = time;
	}

	public UrnSplit(UrnSplit urnSplit) {
		this.name = urnSplit.getName();
		this.time = urnSplit.getTime();
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

	@Override
	public int compareTo(UrnSplit another) {
		return this.time - another.time;
	}
	
	
	
	
}
