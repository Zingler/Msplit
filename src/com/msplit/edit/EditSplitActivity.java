package com.msplit.edit;

import java.io.IOException;

import com.msplit.R;
import com.msplit.urnmodel.Urn;
import com.msplit.urnmodel.UrnSplit;
import com.msplit.urnmodel.UrnUtil;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EditSplitActivity extends Activity implements OnItemClickListener {
	public static final String URN_TO_EDIT = "URN_TO_EDIT";
	private ListView editSplitListView;
	private UrnUtil urnUtil;
	private Urn urn;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editurn);
		editSplitListView = (ListView) findViewById(R.id.editsplitlistview);
		urn = (Urn) getIntent().getExtras().get(URN_TO_EDIT);
		EditSplitAdapter adapter = new EditSplitAdapter(this, urn.getSplits());
		editSplitListView.setAdapter(adapter);
		editSplitListView.setOnItemClickListener(this);
		urnUtil = new UrnUtil(this);
	}

	public void addSplit(View v) {
		EditSplitDialog d = new EditSplitDialog(this, null);
		d.show();
		d.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				EditSplitDialog d = (EditSplitDialog) dialog;
				UrnSplit newUrnSplit = d.getResultUrnSplit();
				if (newUrnSplit != null) {
					urn.add(newUrnSplit);
					updateSplitList();
				}
			}
		});
	}

	public void saveClicked(View v) {
		try {
			urnUtil.save(urn);
		} catch (IOException e) {
			Toast.makeText(this, "Was unable to save " + urn.getFilename(), Toast.LENGTH_LONG).show();
			return;
		}
		finish();
	}

	public void cancelClicked(View v) {
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		final UrnSplit urnSplit = (UrnSplit) parent.getItemAtPosition(position);
		EditSplitDialog d = new EditSplitDialog(this, urnSplit);
		d.show();
		d.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				EditSplitDialog d = (EditSplitDialog) dialog;
				UrnSplit newUrnSplit = d.getResultUrnSplit();
				if (newUrnSplit != null) {
					urnSplit.setName(newUrnSplit.getName());
					urnSplit.setTime(newUrnSplit.getTime());
					urn.sort();
					updateSplitList();
				}
			}
		});
	}

	public void updateSplitList() {
		((BaseAdapter) editSplitListView.getAdapter()).notifyDataSetChanged();
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_right);
	}
}
