package com.fmlditital.emp.notification;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.fmlditital.emp.R;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.async.AsyncRefreshJson;
import com.fmlditital.emp.base.AdapterViewActivity;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.dowmload.DownloadActivity;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.NotificationModel;
import com.fmlditital.emp.preferences.SettingActivity;
import com.fmlditital.emp.tool.ViewFactory;
import com.fmlditital.emp.tool.ViewFactory.AdapterSelect;

public class NotificationActicity extends AdapterViewActivity {

	private static final int REFRESH = Menu.FIRST;
	private static final int DOWNLOAD = Menu.FIRST + 1;
	private static final int SETTING = Menu.FIRST + 2;

	public static final String NOTIFICATION = "NOTIFICATION";

	private ListView listView = null;
	private LinearLayout detailLinearLayout = null;

//	private String notificationURL = null;
//	private String notificationTid = null;

	// private List<BaseModel> notificationData =
	// app.getDataNotificationModel();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		data = app.getDataNotificationModel();
		data.clear();

		adapter = ViewFactory.getDataAdapter(this,
				AdapterSelect.notificationkey, data);

		// adapterView = (ListView)ViewFactory.getAdapterView(this,
		// AdapterSelect.notificationkey);
		//
		// adapterView.setAdapter(adapter);
		//
		// adapterView.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// Toast.makeText(NotificationActicity.this, "kakak",
		// Toast.LENGTH_LONG).show();
		// }
		//
		// });

		detailLinearLayout = new LinearLayout(this);
		detailLinearLayout.setOrientation(LinearLayout.VERTICAL);

		listView = (ListView) ViewFactory.getAdapterView(this,
				AdapterSelect.notificationkey);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			}

		});

		detailLinearLayout.addView(listView);
		bodyLayout.addView(detailLinearLayout);
		listView.setAdapter(adapter);
		Log.d("NotificationActicity>>>>>>>>>>>>", "onCreate");
//		notificationTid = model.getTab_id();
		dataUrl = Confi.getInstance().getNotificationApiUrl(
				tid);

		asyncJson = new AsyncJson(this);
		asyncJson.addInterFace(this);
		asyncJson.execute(dataUrl);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, REFRESH, 0, getResources().getString(R.string.refresh))
				.setIcon(android.R.drawable.ic_popup_sync);

		menu.add(0, DOWNLOAD, 0, getResources().getString(R.string.download))
				.setIcon(android.R.drawable.stat_sys_download_done);
		menu.add(0, SETTING, 0, getResources().getString(R.string.setting))
				.setIcon(android.R.drawable.ic_menu_preferences);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case REFRESH: {
			dataUrl = Confi.getInstance().getNotificationApiUrl(tid);

			AsyncRefreshJson asyncRefreshJson = new AsyncRefreshJson(this);
			asyncRefreshJson.addInterFace(this);
			asyncRefreshJson.execute(dataUrl);
		}
			break;
		case DOWNLOAD: {
			Intent intent = new Intent(NotificationActicity.this,
					DownloadActivity.class);
			intent.putExtra("tabName", "Download");
			NotificationActicity.this.startActivity(intent);
		}
			break;
		case SETTING: {
			Intent intent = new Intent(NotificationActicity.this,
					SettingActivity.class);
			intent.putExtra("tabName", "Setting");

			NotificationActicity.this.startActivity(intent);
		}
			break;
		}
		return true;
	}

	@Override
	public void callJoinSuccess(String url, JSONObject json) {
		super.callJoinSuccess(url, json);
		if (url.equals(dataUrl)) {
			data.clear();
			try {
				JSONArray jsonArray = json.getJSONArray("datas");

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
					NotificationModel model = new NotificationModel();
					model.setId(jsonObject2.getString("notification_id"));
					model.setTitle(jsonObject2.getString("notification_title"));
					model.setTime(jsonObject2.getString("notification_time"));
					model.setContent(jsonObject2
							.getString("notification_content"));
					data.add(model);
					adapter.notifyDataSetChanged();
					// editor.putString(model.getId(), model.getId());
					// editor.commit();
				}

			} catch (JSONException e) {
				callJoinFailure(dataUrl, e.getMessage().toString());
			}

		}
	}

}
