package com.wificar.dialog;


import com.batman.package1.R;
import com.batman.package1.WificarNew;
import com.seuic.AppActivityClose;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;

public class Connect_Dialog {
	public Connect_Dialog() {
		
	}
	public static AlertDialog.Builder createconnectDialog(Context context){
		final AlertDialog.Builder connectDialog = new AlertDialog.Builder(new ContextThemeWrapper(context, R.layout.share_dialog));
		connectDialog.setTitle(R.string.net_connect_error_status);
		
		connectDialog.setMessage(R.string.net_connect_error_content);
		connectDialog .setCancelable(false);

		connectDialog .setPositiveButton(R.string.net_connect_error_exit,new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						AppActivityClose.getInstance().exitAll(); // 这个程序退出
						dialog.cancel();
					}
				}).setNegativeButton(R.string.net_connect_error_share,new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Action for 'NO' Button 
				if (WificarNew.instance != null) {
					WificarNew.instance.share();
				}
			}
		});
		return connectDialog;
	}

	
}