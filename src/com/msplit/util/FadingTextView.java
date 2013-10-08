package com.msplit.util;

import com.msplit.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class FadingTextView extends TextView {

	public FadingTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
		
	@Override
	public void setVisibility(int visibility){
		if(visibility==View.VISIBLE){
			startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fadein));
		} else {
			startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fadeout));
		}
	}

}
