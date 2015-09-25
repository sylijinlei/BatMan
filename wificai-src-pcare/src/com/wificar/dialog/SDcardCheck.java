package com.wificar.dialog;

import com.batman.package1.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.ContextThemeWrapper;

public class SDcardCheck {
	public static AlertDialog.Builder creatSDcardCheckDialog(Context context){
		final AlertDialog.Builder sdcardcheckDialog = new AlertDialog.Builder(new ContextThemeWrapper(context,
				R.style.CustomDialog));
		sdcardcheckDialog.setTitle(R.string.sdcard_tilt);
		sdcardcheckDialog.setPositiveButton(R.string.done_button, new OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		return sdcardcheckDialog;
	}
}