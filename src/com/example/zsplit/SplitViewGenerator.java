package com.example.zsplit;

import java.util.ArrayList;

import com.example.zsplit.urnmodel.Urn;
import com.example.zsplit.urnmodel.UrnSplit;

import android.app.Activity;

public class SplitViewGenerator {
	public static ArrayList<SplitRow> generateSplitViews(Activity activity, Urn list){
		ArrayList<SplitRow> splitRows = new ArrayList<SplitRow>();
		
		SplitRow s;
        for(UrnSplit a : list){
        	s = new SplitRow(activity, null, a);
        	splitRows.add(s); 
        }
        return splitRows;
	}
}
