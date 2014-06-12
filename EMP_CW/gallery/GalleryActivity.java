package com.fmlditital.emp.gallery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.async.AsyncRefreshJson;
import com.fmlditital.emp.base.AdapterViewActivity;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.dowmload.DownloadActivity;
import com.fmlditital.emp.interf.ImageLoadCallback;
import com.fmlditital.emp.model.GalleryModel;
import com.fmlditital.emp.preferences.SettingActivity;
import com.fmlditital.emp.tool.ViewFactory;
import com.fmlditital.emp.tool.ViewFactory.AdapterSelect;

public class GalleryActivity extends AdapterViewActivity implements
		ImageLoadCallback {

	private static final int REFRESH = Menu.FIRST;
	private static final int DOWNLOAD = Menu.FIRST + 1;
	private static final int SETTING = Menu.FIRST + 2;
	private TextView titleView = null, countView = null;

	private UIConfig uiConfig = uiConfig = Confi.getInstance().getuIConfig();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		data = app.getDataGallery();
		data.clear();

		adapter = ViewFactory.getDataAdapter(this, AdapterSelect.gallerykey,
				data);
		LayoutParams param = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1.0f);

		param.gravity = Gravity.CENTER;
		param.leftMargin = 10;
		param.rightMargin = 10;
		param.bottomMargin = 10;
		param.topMargin = 10;

		adapterView = ViewFactory
				.getAdapterView(this, AdapterSelect.gallerykey);
		Log.d("adapter ", "" + adapter.isEmpty());
		adapterView.setAdapter(adapter);
		bodyLayout.addView(adapterView, param);

		dataUrl = Confi.getInstance().getGalleryApiUrl(tid);
		asyncJson = new AsyncJson(this);
		asyncJson.addInterFace(this);
		asyncJson.execute(dataUrl);

		adapterView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("GalleryActivity>>>>>>>>> "+data.size(), "GalleryActivity click");
				GalleryModel model = (GalleryModel) parent
						.getItemAtPosition(position);
				String gId = model.getgId();
				Intent intent = new Intent(GalleryActivity.this,
						GalleryTumbActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("gId", gId);
				bundle.putString("tid", tid);
				bundle.putString("tabName", title);
				bundle.putString("isDownlaod", model.getIsDownload());
				intent.putExtras(bundle);
				GalleryActivity.this.startActivity(intent);

			}
		});

		if (adapterView instanceof Gallery) {
			titleView = new TextView(this);
			countView = new TextView(this);

			// titleView.setText("titleView");
			// countView.setText("countView");

			LayoutParams titleParam = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			titleParam.gravity = Gravity.CENTER;
			titleParam.bottomMargin = 20;
			titleView.setTextColor(Color.parseColor(uiConfig
					.getApp_text_color()));

			countView.setTextColor(Color.parseColor(uiConfig
					.getApp_text_color()));

			bodyLayout.addView(titleView, titleParam);
			bodyLayout.addView(countView, titleParam);

			adapterView.setOnItemSelectedListener(new OnItemSelectedListener() {

				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					titleView.setText(((GalleryModel) data.get(position))
							.getgName()
							+ " ("
							+ ((GalleryModel) data.get(position)).getCount()
							+ ")");

					countView.setText(position + 1 + " / " + data.size());
				}

				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}

			});

		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// land do nothing is ok
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			// port do nothing is ok
		}
	}

	public void callImageSuccess(String url, Drawable drawable) {
		super.callImageSuccess(url, drawable);
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
					if (jsonObject2.getString("gstatus").equals("1")) {
						GalleryModel model = new GalleryModel();
						model.setgId(jsonObject2.getString("gid"));
						model.setgName(jsonObject2.getString("gname"));
						model.setgThumbUrl(jsonObject2
								.getString("gdefaut_icon"));
						model.setIsDownload(jsonObject2
								.getString("gis_download"));
						model.setCount(jsonObject2.getString("gcount"));
						data.add(model);
						adapter.notifyDataSetChanged();
						Log.d("callJoinSuccess ",
								"adapter.notifyDataSetChanged");
						Log.d("adapter  "+data.size(), "=====" + adapter.isEmpty());
					}
				}
			} catch (JSONException e) {
			}

		}

	}

	@Override
	public void callJoinFailure(String url, String failMessage) {
		// TODO Auto-generated method stub
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
			dataUrl = Confi.getInstance().getGalleryApiUrl(tid);
			AsyncRefreshJson asyncRefreshJson = new AsyncRefreshJson(this);
			asyncRefreshJson.addInterFace(this);
			asyncRefreshJson.execute(dataUrl);
		}
			break;
		case DOWNLOAD: {
			Intent intent = new Intent(GalleryActivity.this,
					DownloadActivity.class);
			intent.putExtra("tabName", "Download");
			GalleryActivity.this.startActivity(intent);
		}
			break;
		case SETTING: {
			Intent intent = new Intent(GalleryActivity.this,
					SettingActivity.class);
			intent.putExtra("tabName", "Setting");

			GalleryActivity.this.startActivity(intent);
		}
			break;
		}
		return true;
	}
}
