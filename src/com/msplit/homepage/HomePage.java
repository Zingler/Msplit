package com.msplit.homepage;

import java.util.ArrayList;
import java.util.List;

import com.msplit.MainActivity;
import com.msplit.R;
import com.msplit.urnmodel.UrnUtil;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;

public class HomePage extends ListActivity {

	private UrnUtil urnUtil;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		urnUtil = UrnUtil.getInstance(this);
	}
	
	@Override
	public void onStart(){
		List<RecentSplit> recentSplits = UrnUtil.getInstance(this).getRecentSplits().list();
		List<RecentSplit> sublist = new ArrayList<RecentSplit>();
		for (int i = 0; i < recentSplits.size() && i < 5; i++) {
			sublist.add(recentSplits.get(i));
		}
		setListAdapter(new RecentSplitAdapter(this, sublist));
		super.onStart();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		RecentSplit urn = (RecentSplit) l.getItemAtPosition(position);

		Intent i = new Intent(HomePage.this, MainActivity.class);
		i.putExtra(MainActivity.URN_NAME_PARAM, urn.getFilename());
		startActivity(i);
		overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_left);
	}

	public void newRun(View v) {
		Intent i = new Intent(this, MainActivity.class);
		i.putExtra(MainActivity.URN_NAME_PARAM, (String) null);
		startActivity(i);
		overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_left);
	}

	public void loadRun(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final String[] items = urnUtil.listUrns();
		builder.setItems(items, new DialogInterface.OnClickListener() {

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
