package com.msplit.homepage;

import java.util.List;

import com.msplit.MainActivity;
import com.msplit.R;
import com.msplit.R.id;
import com.msplit.R.layout;
import com.msplit.R.menu;
import com.msplit.Util;
import com.msplit.urnmodel.UrnUtil;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class HomePage extends ListActivity {

    private ListView recentSplitsListView;
	private UrnUtil urnUtil;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        List<String> recentSplits = UrnUtil.getInstance(this).getRecentSplits().list();
        List<String> sublist = recentSplits.subList(0, recentSplits.size() < 5 ? recentSplits.size() : 5);
        setListAdapter(new RecentSplitAdapter(this, sublist));
        urnUtil = UrnUtil.getInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home_page, menu);
        return true;
    }
    
    public void newRun(View v){
    	Intent i = new Intent(this, MainActivity.class);
    	i.putExtra(MainActivity.URN_NAME_PARAM, (String)null);
    	startActivity(i);
    	overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_left);
    }
    
    public void loadRun(View v){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	final String[] items = urnUtil.listUrns();
    	builder.setItems(items, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent(HomePage.this, MainActivity.class);
				i.putExtra(MainActivity.URN_NAME_PARAM, items[which]);
				startActivity(i);
				overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_left);
			}
    		
    	});
    	builder.create().show();
    }
}
