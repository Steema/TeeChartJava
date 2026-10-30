package com.steema.teechart.editors;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View.OnClickListener;

public class Utils {
	
	public static void yesNo(Context context, final String title, final OnClickListener onYes, 
			final OnClickListener onNo) {
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(context)
		.setTitle(title)
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {
				if (onYes != null)
					onYes.onClick(null);
			}

		})
		.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {
				if (onNo != null)
					onNo.onClick(null);
			}

		});

		final AlertDialog alert = builder.create();
		alert.show();
	}

}
