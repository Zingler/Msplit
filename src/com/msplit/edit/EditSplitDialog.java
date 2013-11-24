package com.msplit.edit;

import com.msplit.R;
import com.msplit.urnmodel.UrnSplit;
import com.msplit.util.Time;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;

public class EditSplitDialog extends AlertDialog {
	private UrnSplit resultUrnSplit;
	private UrnSplit sourceUrnSplit;

	public EditSplitDialog(Context context, UrnSplit sourceUrnSplit) {
		super(context);
		this.sourceUrnSplit = sourceUrnSplit;
	}

	public void show() {
		setTitle("Edit Split");

		View v = this.getLayoutInflater().inflate(R.layout.editsplitdialog, null);

		final EditText name = (EditText) v.findViewById(R.id.editdialogname);
		final NumberPicker hour = (NumberPicker) v.findViewById(R.id.editdialoghour);
		final NumberPicker minute = (NumberPicker) v.findViewById(R.id.editdialogminute);
		final NumberPicker second = (NumberPicker) v.findViewById(R.id.editdialogsecond);
		final NumberPicker tenthsecond = (NumberPicker) v.findViewById(R.id.editdialogtenthsecond);
		final CheckBox blankSplit = (CheckBox) v.findViewById(R.id.blanksplitcheckbox); 

		if (sourceUrnSplit != null) {
			name.setText(sourceUrnSplit.getName());
			Time time = new Time(sourceUrnSplit.getTime());
			hour.setValue(time.hour);
			minute.setValue(time.minute);
			second.setValue(time.second);
			tenthsecond.setValue(time.tenthsecond);
			blankSplit.setChecked(sourceUrnSplit.isBlankSplit());
		}

		setView(v);
		setButton(BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Time newTime = new Time(hour.getValue(), minute.getValue(), second.getValue(), tenthsecond.getValue());
				String newName = name.getText().toString();
				UrnSplit result = new UrnSplit(newName, newTime.toInt());
				result.setBlankSplit(blankSplit.isChecked());
				setResultUrnSplit(result);
			}
		});
		setButton(BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setResultUrnSplit(null);
			}
		});
		super.show();
	}

	public UrnSplit getResultUrnSplit() {
		return resultUrnSplit;
	}

	public void setResultUrnSplit(UrnSplit resultUrnSplit) {
		this.resultUrnSplit = resultUrnSplit;
	}

}
