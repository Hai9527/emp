package com.fmlditital.emp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fmlditital.emp.R;
import com.fmlditital.emp.app.EMPApp;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.model.AriticleModel;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.model.NotificationModel;
import com.fmlditital.emp.tool.NetWorkState;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class BootService extends Service {

	private EMPApp app = EMPApp.getSingleton();
//	private List<NotificationModel> data = app.getDataNotificationModel();
	private List<NotificationModel> data  =null;

	private Timer timer = null;
	private TimerTask timerTask = null;
//	private String notificationURL =Confi.getNotificationApiUrl(id);
	private String notificationURL =null;
	private String tid=null;
	private NotificationAsync async = null;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
//		Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>onCreate ");
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
//		Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>onStart ");
		
		if (NetWorkState.isNetworkAvailable(this)) {
			tid=intent.getExtras().getString("tid");
			notificationURL =Confi.getNotificationApiUrl(tid);
			Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>notificationURL "+notificationURL);
			async = new NotificationAsync();
			async.execute();
		}
		
		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
//		timer.cancel();
	}

	private synchronized void getNotification(String path) {

		String result = "";
		try {
			URL url = new URL(path);

			StringBuilder builder = new StringBuilder();
			URLConnection tc = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					tc.getInputStream()));

			String line;
			while ((line = in.readLine()) != null) {
				builder.append(line);

			}
			JSONObject jsonObject = new JSONObject(builder.toString());

			result = builder.toString();

			JSONArray jsonArray = jsonObject.getJSONArray("datas");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
				NotificationModel model = new NotificationModel();
				model.setId(jsonObject2.getString("notification_id"));
				model.setTitle(jsonObject2.getString("notification_title"));
				model.setTime(jsonObject2.getString("notification_time"));
				model.setContent(jsonObject2.getString("notification_content"));
				Log.v("",
						">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>: data: "
								+ i + "   notification_id:"
								+ jsonObject2.getString("notification_id")
								+ "   notification_title:"
								+ jsonObject2.getString("notification_title")
								+ "   notification_time:"
								+ jsonObject2.getString("notification_time")
								+ "   notification_content:"
								+ jsonObject2.getString("notification_content"));
				data.add(model);

			}
			in.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// return result;
	}

	private class NotificationAsync extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			data.clear();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			getNotification(notificationURL);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
		}
	}
}
