package com.batman.package1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PhoneStateReceiver extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
			if (WificarNew.instance != null) {
				WificarNew.instance.ExitPcare();
			}
		}
	}

}
