package com.fmlditital.emp.receiver;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fmlditital.emp.dowmload.DownloadManager;
import com.fmlditital.emp.model.DownloadModel;
import com.fmlditital.emp.tool.NetWorkState;

public class NetworkReceiver extends BroadcastReceiver {

	private List<DownloadModel> downloadData = DownloadManager.getInstance()
			.getDownloadData();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if (!NetWorkState.isNetworkAvailable(context)) {
//			Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无网络了");
			
			for (DownloadModel model : downloadData) {
				if(model.getState()==DownloadManager.download_ing)
					DownloadManager.getInstance().pause(model);
//				Log.v("", ">>>>>>>>>>>>>>>>model.getState()	"+model.getState());
			}
		}
		else {
//			Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>有网络了");
			for (DownloadModel model : downloadData) {
				if(model.getState()==DownloadManager.download_pause) {
					DownloadManager.getInstance().goon(context,model);
//					Log.v("", ">>>>>>>>>>>>>>>>model.getState() goon	"+model.getState());
				}
			}
		}
	}

}
