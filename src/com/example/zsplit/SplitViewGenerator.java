package com.example.zsplit;

import java.util.ArrayList;

import com.example.zsplit.splitmodel.Split;
import com.example.zsplit.splitmodel.SplitList;

import android.app.Activity;

public class SplitViewGenerator {
	public static ArrayList<SplitRow> generateSplitViews(Activity activity, SplitList list){
		ArrayList<SplitRow> splitRows = new ArrayList<SplitRow>();
		
		SplitRow s;
        for(Split a : list){
        	s = new SplitRow(activity, null, a);
        	splitRows.add(s); 
        }
        return splitRows;
	}
}
