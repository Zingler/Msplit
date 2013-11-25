package com.msplit;

import java.io.IOException;
import java.util.Timer;

import com.msplit.R;
import com.msplit.ResetCheckPipeline.ResetCallBack;
import com.msplit.edit.EditSplitActivity;
import com.msplit.urnmodel.Urn;
import com.msplit.urnmodel.UrnUtil;
import com.msplit.util.FadingTextView;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public final static String URN_NAME_PARAM = "com.msplit.urnNameParam";
	public final static long DOUBLE_SPLIT_PREVENT_TIME_MILLIS = 500;
	public int counter = 0;
	public TextView mainTimer;
	public TextView splitDelta;
	public FadingTextView tapAnywhere;
	public Timer stopwatch;
	public ListView splitTable;
	public UrnUtil splitListUtil;
	public Vibrator vibrator;
	public AbstractRunController runController;
	public Urn urn;
	public boolean inFreeRun;
	private int green;
	private int red;
	private int defaultColor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		splitTable = (ListView) findViewById(R.id.splittable);

		mainTimer = (TextView) findViewById(R.id.maintimer);
		splitDelta = (TextView) findViewById(R.id.splitdelta);
		tapAnywhere = (FadingTextView) findViewById(R.id.splitanywheretext);
		splitListUtil = UrnUtil.getInstance(this);
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		red = getResources().getColor(R.color.Red);
		green = getResources().getColor(R.color.Green);
		defaultColor = splitDelta.getTextColors().getDefaultColor();

		String urnString = (String) getIntent().getExtras().get(URN_NAME_PARAM);
		if (urnString != null) {
			try {
				urn = splitListUtil.load(urnString);
				changeToRun(urn);
			} catch (Exception e) {
				Log.e("load", "could not load urn " + urnString +":"+ e.getMessage());
				changeToFreeRun();
			}
		} else {
			changeToFreeRun();
		}
	}

	private void changeToRun(Urn newUrn) {
		this.urn = newUrn;
		this.setTitle(urn.getFilename());
		if (runController != null) {
			runController.cleanUp();
		}
		runController = new RunController(this, urn);

		splitTable.setAdapter(new RunSplitAdapter(this, runController));
		splitDelta.setVisibility(View.VISIBLE);
		tapAnywhere.setVisibility(View.INVISIBLE);
		updateSplitList();
		inFreeRun = false;
	}

	private void changeToFreeRun() {
		if (runController != null) {
			runController.cleanUp();
		}
		setTitle("(New Split)");
		runController = new FreeRunController(this);

		splitTable.setAdapter(new RunSplitAdapter(this, runController));
		splitDelta.setVisibility(View.INVISIBLE);
		updateSplitList();
		inFreeRun = true;
	}

	public void updateSplitList() {
		((BaseAdapter) splitTable.getAdapter()).notifyDataSetChanged();
		runOnUiThread(new Runnable() {
			public void run() {
				if (runController.hasDeltas()) {
					splitDelta.setText(Util.formatTimerStringNoZeros(runController.getDelta(), true));
					if (runController.getDelta() > 0) {
						splitDelta.setTextColor(red);
					} else if (runController.getDelta() < 0) {
						splitDelta.setTextColor(green);
					} else {
						splitDelta.setTextColor(defaultColor);
					}
				}
			}
		});

	}

	public void scrollToSplit(int position) {
		splitTable.smoothScrollToPositionFromTop(position, splitTable.getHeight() / 2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.save_split:
			createSaveDialog().show();
			return true;
		case R.id.edit_urn:
			Intent intent = new Intent(this, EditSplitActivity.class);
			Urn u;
			if (inFreeRun) {
				u = runController.createUrnFromRun();
			} else {
				u = urn;
			}
			intent.putExtra(EditSplitActivity.URN_TO_EDIT, u);
			startActivityForResult(intent, 1);
			overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_left);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void startButtonClicked(View view) {
		runController.start();
		if (runController.isRunning()) {
			((TextView) view).setText("Pause");
		} else {
			((TextView) view).setText("Start");
		}

	}

	public void resetButtonClicked(View view) {
		new ResetCheckPipeline(this, runController, new ResetCallBack() {
			public void run(Urn urn) {
				reset(urn);
			}
		}).doResetChecks();
	}

	public void reset(Urn newUrn) {
		if (inFreeRun) {
			changeToFreeRun();
		} else {
			if (newUrn != null) {
				changeToRun(newUrn);
			} else {
				changeToRun(urn);
			}
		}
		((TextView) findViewById(R.id.start)).setText("Start");
	}

	long lastSplitTime = 0;

	public boolean splitButtonClicked(View view) {
		if (runController.isRunning()) {
			if (System.currentTimeMillis() - lastSplitTime > DOUBLE_SPLIT_PREVENT_TIME_MILLIS) {
				lastSplitTime = System.currentTimeMillis();
				if (runController.split()) {
					vibrator.vibrate(50);
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public AlertDialog createSaveDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Save as...");
		View v = this.getLayoutInflater().inflate(R.layout.savedialog, null);
		final EditText filename = ((EditText) v.findViewById(R.id.save_filename));
		if (urn != null && urn.getFilename() != null) {
			filename.setText(urn.getFilename());
		}
		builder.setView(v);
		builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String newName = filename.getText().toString();
				try {
					Urn newUrn = runController.createUrnFromRun();
					newUrn.setFilename(newName);
					splitListUtil.save(newUrn);
					changeToRun(newUrn);
				} catch (IOException e) {
					Toast.makeText(getApplicationContext(), "Could not save the file", Toast.LENGTH_SHORT).show();
					Log.e("save", e.getMessage());
				}
			}
		});
		builder.setNegativeButton("Cancel", null);
		return builder.create();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Urn newUrn = (Urn) data.getExtras().get("urn");
			newUrn.fixUrnSplits();
			changeToRun(newUrn);
		} else if (resultCode == EditSplitActivity.DELETED_RETURN_CODE) {
			finish();
		}
	}

	@Override
	public void finish() {
		new ResetCheckPipeline(this, runController, new ResetCallBack() {
			public void run(Urn urn) {
				MainActivity.super.finish();
				overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_right);
			}
		}).doResetChecks();
	}
}
