package com.msplit;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class ResetCheckPipeline {
	private AbstractRunController runController;
	private MainActivity activity;
	
	public ResetCheckPipeline(MainActivity activity, AbstractRunController runController) {
		this.runController = runController;
		this.activity = activity;
	}
	
	public void doResetChecks() {
		goldSplit();
	}
	
	private void goldSplit() {
		if (runController.hasGoldSplits()) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
			alertDialogBuilder.setTitle("Gold");
			alertDialogBuilder.setMessage("Congrats! Do you want to save your new Gold Splits as best segments?")
			 .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
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
			alertDialogBuilder.setMessage("Congrats! Do you want to save this as your best run?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					
					ResetCheckPipeline.this.finish();
				}
			}).setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					
					ResetCheckPipeline.this.finish();
				}
			});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		} else {
			finish();
		}
	}
	
	private void finish() {
		activity.reset();
	}
}
