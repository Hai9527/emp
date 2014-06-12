package com.fmlditital.emp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fmlditital.emp.service.BootService;

public class BootBroadCastReceiver extends BroadcastReceiver {

	public static final String ACTION = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ACTION)) {
			Intent myIntent = new Intent();
			myIntent.setClass(context, BootService.class);
			context.startService(myIntent);
		}

	}

}
