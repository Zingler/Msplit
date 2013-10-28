package com.msplit;

import java.io.IOException;

import com.msplit.runmodel.RunSplit;
import com.msplit.urnmodel.Urn;
import com.msplit.urnmodel.UrnUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

public class ResetCheckPipeline {
	private AbstractRunController runController;
	private MainActivity activity;
	private UrnUtil util;
	
	public ResetCheckPipeline(MainActivity activity, AbstractRunController runController) {
		this.runController = runController;
		this.activity = activity;
		this.util = UrnUtil.getInstance(activity);
	}
	
	public void doResetChecks() {
		goldSplit();
	}
	
	private void goldSplit() {
		if (runController.hasGoldSplits()) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
			alertDialogBuilder.setTitle("Gold");
			alertDialogBuilder.setMessage("Congrats on the Gold splits! Do you want to save them as best segments?")
			 .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					for(RunSplit r : runController.getRunSplits()){
						if(r.isGoldSplit()){
							r.getUrnSplit().setBestSegment(r.getSegmentTime());
						}
					}
					try {
						util.save(runController.getUrn());
					} catch (IOException e) {
						Toast.makeText(activity, "Could not save gold splits", Toast.LENGTH_SHORT).show();
						Log.e("file", "Could not save gold splits "+e.getMessage());
					}
					ResetCheckPipeline.this.newUrn();
				}
			}).setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {

					ResetCheckPipeline.this.newUrn();
				}
			});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		} else {
			newUrn();
		}
	}
	
	private void newUrn() {
		if (runController.isNewUrn()) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
			alertDialogBuilder.setTitle("New Urn");
			alertDialogBuilder.setMessage("Congrats on the new Personal Best! Do you want to save this run?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Urn urn = runController.createUrnFromRun();
					try {
						util.save(urn);
						ResetCheckPipeline.this.finish(urn);
					} catch (IOException e) {
						Toast.makeText(activity, "Could not save run", Toast.LENGTH_SHORT).show();
						Log.e("file", "Could not save run "+e.getMessage());
						ResetCheckPipeline.this.finish(null);
					}					
				}
			}).setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					ResetCheckPipeline.this.finish(null);
				}
			});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		} else {
			finish(null);
		}
	}
	
	private void finish(Urn newUrn) {
		activity.reset(newUrn);
	}
}
