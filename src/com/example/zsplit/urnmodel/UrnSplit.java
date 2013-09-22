package com.example.zsplit.urnmodel;

import java.io.Serializable;

public class UrnSplit implements Serializable{
	private static final long serialVersionUID = 1L;
	public String name;
	public int time;

	public UrnSplit(String title, int time){
		this.name = title;
		this.time = time;
	}
	
	
}
