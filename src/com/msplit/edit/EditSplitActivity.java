package com.msplit.edit;

import java.io.IOException;

import com.msplit.R;
import com.msplit.urnmodel.Urn;
import com.msplit.urnmodel.UrnSplit;
import com.msplit.urnmodel.UrnUtil;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EditSplitActivity extends Activity implements OnItemClickListener {
	public static final String URN_TO_EDIT = "URN_TO_EDIT";
	private ListView editSplitListView;
	private UrnUtil urnUtil;
	private Urn urn;
	private String oldFileName;
	private EditText titleText;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editurn);
		editSplitListView = (ListView) findViewById(R.id.editsplitlistview);
		urn = (Urn) getIntent().getExtras().get(URN_TO_EDIT);
		titleText = (EditText) findViewById(R.id.edittitle);
		if (urn.getFilename() != null) {
			oldFileName = urn.getFilename();
			titleText.setText(urn.getFilename());
		}
		EditSplitAdapter adapter = new EditSplitAdapter(this, urn.getSplits());
		editSplitListView.setAdapter(adapter);
		editSplitListView.setOnItemClickListener(this);
		urnUtil = UrnUtil.getInstance(this);
		setResult(RESULT_CANCELED);
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
		if (titleText.getText().toString().equals("")) {
			Toast.makeText(this, "You must have a have a Title", Toast.LENGTH_LONG).show();
			return;
		}

		try {
			urn.setFilename(titleText.getText().toString());
			urnUtil.save(urn);

			// If the old and new file names do not match, delete the old one
			if (!oldFileName.equals(urn.getFilename())) {
				urnUtil.delete(oldFileName);
			}
		} catch (IOException e) {
			Toast.makeText(this, "Was unable to save " + urn.getFilename(), Toast.LENGTH_LONG).show();
			return;
		}
		setResult(RESULT_OK, new Intent().putExtra("urn", urn));
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
