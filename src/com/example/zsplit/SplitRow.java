package com.example.zsplit;

import com.example.zsplit.urnmodel.UrnSplit;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SplitRow extends RelativeLayout {
	public UrnSplit split;
	public TextView timeView;
	
	public SplitRow(Context context, AttributeSet attrs) {
		this(context, attrs, new UrnSplit("Sample Split", 100));
	}

	public SplitRow(Context context, AttributeSet attrs, UrnSplit s) {
		super(context, attrs);
		this.split = s;
		if (!isInEditMode()) {
			TextView titleView = new TextView(context, null, android.R.attr.textAppearanceLarge);
			titleView.setText(s.name);
			timeView = new TextView(context, null, android.R.attr.textAppearanceLarge);
			
			this.addView(titleView);
			this.addView(timeView);
			resetTimeText();
			
			RelativeLayout.LayoutParams lay = (RelativeLayout.LayoutParams)timeView.getLayoutParams();
			lay.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		}
	}
	
	public void resetTimeText(){
		timeView.setTextColor(Color.BLACK);
		timeView.setText(Util.formatTimerStringNoZeros(split.time));
	}

}
