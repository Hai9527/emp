package com.fmlditital.emp.cast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fmlditital.emp.R;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.async.AsyncRefreshJson;
import com.fmlditital.emp.base.AdapterViewActivity;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.dowmload.DownloadActivity;
import com.fmlditital.emp.model.CastModel;
import com.fmlditital.emp.preferences.SettingActivity;
import com.fmlditital.emp.tool.ViewFactory;
import com.fmlditital.emp.tool.ViewFactory.AdapterSelect;

public class CastActivity extends AdapterViewActivity {

	private static final int REFRESH = Menu.FIRST;
	private static final int DOWNLOAD = Menu.FIRST + 1;
	private static final int SETTING = Menu.FIRST + 2;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		data = app.getDataCast();
		data.clear();

		adapterView = ViewFactory.getAdapterView(this, AdapterSelect.castkey);
		adapter = ViewFactory.getDataAdapter(this, AdapterSelect.castkey, data);
		adapterView.setAdapter(adapter);

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		layoutParams.gravity = Gravity.CENTER;

		bodyLayout.addView(adapterView, layoutParams);

		dataUrl = Confi.getInstance().getCastApiUrl(tid);
		asyncJson = new AsyncJson(this);
		asyncJson.addInterFace(this);
		asyncJson.execute(dataUrl);
		// if (adapterView instanceof GridView) {
		if(adapterView instanceof GridView) {
//			((GridView) adapterView).setSelector(Color.RED);
//			((GridView) adapterView).setSelector(R.drawable.blue);
		}
		adapterView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(CastActivity.this,
						CastDetailActivity.class);
				Bundle bundle = new Bundle();

				bundle.putInt("index", position);
				bundle.putString("tabName", title);
				intent.putExtras(bundle);
				CastActivity.this.startActivity(intent);
			}

		});
		// }
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
					if (jsonObject2.getString("cast_status").equals("1")) {
						CastModel model = new CastModel();
						model.setId(jsonObject2.getString("cast_id"));
						model.setTitle(jsonObject2.getString("cast_title"));
						model.setIcon(jsonObject2.getString("cast_icon"));
						model.setImage(jsonObject2.getString("cast_image"));

						// Log.v("",
						// ">>>>>>>>>>>>>>>>>>>>>>cast_title "+jsonObject2.getString("cast_title"));
						data.add(model);
						adapter.notifyDataSetChanged();
					}
				}
			} catch (JSONException e) {
				// e.printStackTrace();
			}
		}
	}

	@Override
	public void callJoinFailure(String url, String failMessage) {
		super.callJoinFailure(url, failMessage);
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
			dataUrl = Confi.getInstance().getCastApiUrl(tid);
			AsyncRefreshJson asyncRefreshJson = new AsyncRefreshJson(this);
			asyncRefreshJson.addInterFace(this);
			asyncRefreshJson.execute(dataUrl);
		}
			break;
		case DOWNLOAD: {
			Intent intent = new Intent(CastActivity.this,
					DownloadActivity.class);
			intent.putExtra("tabName", "Download");
			CastActivity.this.startActivity(intent);
		}
			break;
		case SETTING: {
			Intent intent = new Intent(CastActivity.this, SettingActivity.class);
			intent.putExtra("tabName", "Setting");

			CastActivity.this.startActivity(intent);
		}
			break;
		}
		return true;
	}

}
