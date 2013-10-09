package com.msplit.util;

import java.util.ArrayList;
import java.util.List;

import com.msplit.homepage.RecentSplit;

public class PriorityList {
	ArrayList<RecentSplit> list;

	public PriorityList() {
		list = new ArrayList<RecentSplit>();
	}

	public void putTop(RecentSplit item) {
		list.remove(item);
		list.add(0, item);
	}

	public List<RecentSplit> list() {
		return list;
	}

	public void remove(RecentSplit item) {
		list.remove(item);
	}
}
