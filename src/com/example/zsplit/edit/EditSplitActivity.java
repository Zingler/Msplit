package com.example.zsplit.edit;

import com.example.zsplit.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


public class EditSplitActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editurn);
    }
	
	public void saveClicked(View v){
		finish();
	}
	public void cancelClicked(View v){
		finish();
	}
	
	@Override 
	public void finish(){ 
		super.finish();
		overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_right);
	}
}
