package com.fmlditital.emp.gallery;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.adapter.GalleryThumbAdapter;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.async.AsyncRefreshJson;
import com.fmlditital.emp.base.AdapterViewActivity;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.dowmload.DownloadActivity;
import com.fmlditital.emp.model.GalleryThumbModel;
import com.fmlditital.emp.preferences.SettingActivity;

public class GalleryTumbActivity extends AdapterViewActivity {

	private String gId = null;

	private static final int REFRESH = Menu.FIRST;
	private static final int DOWNLOAD = Menu.FIRST + 1;
	private static final int SETTING = Menu.FIRST + 2;

	private String isDownlaod = null;

	private GridView mGrid = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		gId = this.getIntent().getExtras().getString("gId");

		data = app.getDataGalleryThumb();
		data.clear();

//		isDownlaod = this.getIntent().getExtras().getString("isDownlaod");

		mGrid = new GridView(this);
		mGrid.setNumColumns(3);

		mGrid.setHorizontalSpacing(2);
		mGrid.setVerticalSpacing(2);
		mGrid.setCacheColorHint(Color.TRANSPARENT);

		adapter = new GalleryThumbAdapter(GalleryTumbActivity.this, data);
		// adapter = new GalleryThumbAdapter(GalleryTumbActivity.this);
		mGrid.setAdapter(adapter);

		bodyLayout.addView(mGrid);

		dataUrl = Confi.getInstance().getGalleryDetailApiUrl(tid, gId);
		asyncJson = new AsyncJson(this);
		asyncJson.addInterFace(this);
		asyncJson.execute(dataUrl);

		mGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				GalleryThumbModel model=(GalleryThumbModel)parent
						.getItemAtPosition(position);
				isDownlaod=model.getIsDownload();
				Intent intent = new Intent(GalleryTumbActivity.this,
						ShowPictureActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("index", position);
				bundle.putString("tid", tid);
				bundle.putString("dataUrl", dataUrl);
				bundle.putString("isDownlaod", isDownlaod);
				intent.putExtras(bundle);
				GalleryTumbActivity.this.startActivity(intent);
			}

		});

	}

	@Override
	public void callJoinSuccess(String url, JSONObject json) {
		super.callJoinSuccess(url, json);
		if (url.equals(dataUrl)) {
//			Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>dataUrl " + dataUrl);
			data.clear();
			try {
				JSONArray jsonArray = json.getJSONArray("datas");

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
					if (jsonObject2.getString("gstatus").equals("1")) {
						GalleryThumbModel model = new GalleryThumbModel();
						model.setId(jsonObject2.getString("gid"));
						model.setpThumbUrl(jsonObject2.getString("photo_thumb"));
						model.setpImageUrl(jsonObject2.getString("photo_image"));
						model.setpName(jsonObject2.getString("photo_title"));
						model.setDescription(jsonObject2
								.getString("gdescription"));
//						model.setIsDownload(isDownlaod);
						model.setIsDownload(jsonObject2
								.getString("isdownload"));  
						model.setTitle(jsonObject2.getString("gname"));
						model.setpId(jsonObject2.getString("photo_id"));
//						AsyncLoadImage asyncLoadImage=new AsyncLoadImage();
//						 asyncLoadImage.execute(model);
						 
//						 data.add(model);
						((GalleryThumbAdapter) adapter).addItem(model);
						adapter.notifyDataSetChanged();
						
					}
				}
//				adapter.notifyDataSetChanged();
				mGrid.invalidate();
				bodyLayout.invalidate();
			} catch (JSONException e) {
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
			dataUrl = Confi.getInstance().getGalleryDetailApiUrl(tid, gId);
			AsyncRefreshJson asyncRefreshJson = new AsyncRefreshJson(this);
			asyncRefreshJson.addInterFace(this);
			asyncRefreshJson.execute(dataUrl);
		}
			break;
		case DOWNLOAD: {
			Intent intent = new Intent(GalleryTumbActivity.this,
					DownloadActivity.class);
			intent.putExtra("tabName", "Download");
			GalleryTumbActivity.this.startActivity(intent);
		}
			break;
		case SETTING: {
			Intent intent = new Intent(GalleryTumbActivity.this,
					SettingActivity.class);
			intent.putExtra("tabName", "Setting");

			GalleryTumbActivity.this.startActivity(intent);
		}
			break;
		}
		return true;
	}

	class AsyncLoadImage extends AsyncTask<Object, Object, Bitmap> {
		GalleryThumbModel model = null;

		@Override
		protected Bitmap doInBackground(Object... params) {
			Bitmap bitmap = null;
			try {
				model = (GalleryThumbModel) params[0];
				String url = model.getpThumbUrl();
				// Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>ImageView: url " +
				// url);
				bitmap = getBitmapByUrl(url);
				// publishProgress(new Object[] { imageView, bitmap });
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bitmap;
		}

		protected void onProgressUpdate(Object... progress) {
			// ImageView imageView = (ImageView) progress[0];
			// imageView.setImageBitmap((Bitmap) progress[1]);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if (result != null)
				model.setBitmap(result);
		}

	}

	static public Bitmap getBitmapByUrl(String urlString)
			throws MalformedURLException, IOException {
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(25000);
		connection.setReadTimeout(90000);
		Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
		return bitmap;
	}
}
