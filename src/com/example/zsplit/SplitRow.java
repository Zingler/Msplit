package com.example.zsplit;

import com.example.zsplit.splitmodel.Split;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SplitRow extends RelativeLayout {
	public Split split;
	public TextView timeView;
	
	public SplitRow(Context context, AttributeSet attrs) {
		this(context, attrs, new Split("Sample Split", 100));
	}

	public SplitRow(Context context, AttributeSet attrs, Split s) {
		super(context, attrs);
		this.split = s;
		if (!isInEditMode()) {
			TextView titleView = new TextView(context, null, android.R.attr.textAppearanceLarge);
			titleView.setText(s.title);
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
