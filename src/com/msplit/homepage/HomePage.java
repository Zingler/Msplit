package com.msplit.homepage;

import java.util.List;

import com.msplit.R;
import com.msplit.R.id;
import com.msplit.R.layout;
import com.msplit.R.menu;
import com.msplit.urnmodel.UrnUtil;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class HomePage extends ListActivity {

    private ListView recentSplitsListView;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        List<String> recentSplits = UrnUtil.getInstance(this).getRecentSplits().list();
        List<String> sublist = recentSplits.subList(0, recentSplits.size() < 5 ? recentSplits.size() : 5);
        setListAdapter(new RecentSplitAdapter(this, sublist));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home_page, menu);
        return true;
    }
    
    public void newRun(View v){
    	
    }
    
    public void loadRun(View v){
    	
    }
}
