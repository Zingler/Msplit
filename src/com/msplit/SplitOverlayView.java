package com.msplit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SplitOverlayView extends android.view.View {

	MainActivity mainActivity;

	public SplitOverlayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode()) {
			mainActivity = (MainActivity) context;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			return mainActivity.splitButtonClicked(null);
		}
		return false;
	}

}
