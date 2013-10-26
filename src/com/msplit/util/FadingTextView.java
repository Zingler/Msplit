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
		lastState = this.getVisibility();
	}

	public int lastState;

	@Override
	public void setVisibility(int visibility) {
		if (lastState != visibility) {
			if (visibility == View.VISIBLE) {
				startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fadein));
				lastState = View.VISIBLE;
			} else {
				startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fadeout));
				lastState = View.INVISIBLE;
			}
		}
	}

}
