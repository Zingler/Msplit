package com.msplit.util;

import java.util.ArrayList;
import java.util.List;

public class PriorityList<T> {
	ArrayList<T> list;
	
	public PriorityList(){
		list = new ArrayList<T>();
	}
	
	public void putTop(T item){
		list.remove(item);
		list.add(0, item);
	}
	
	public List<T> list(){
		return list;
	}

	public void remove(T item) {
		list.remove(item);
	}
}
