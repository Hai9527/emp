package com.fmlditital.emp.music;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.fmlditital.emp.PageActivity;
import com.fmlditital.emp.R;
import com.fmlditital.emp.async.AsyncImage;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.async.AsyncRefreshJson;
import com.fmlditital.emp.base.AdapterViewActivity;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.dowmload.DownloadActivity;
import com.fmlditital.emp.interf.ImageLoadCallback;
import com.fmlditital.emp.model.MusicModel;
import com.fmlditital.emp.preferences.SettingActivity;
import com.fmlditital.emp.tool.ViewFactory;
import com.fmlditital.emp.tool.ViewFactory.AdapterSelect;

public class MusicActivity extends AdapterViewActivity {

	private AsyncImage asyncImage = null;
	private ImageView imageView = null;

	private static final int REFRESH = Menu.FIRST;
	private static final int DOWNLOAD = Menu.FIRST + 1;
	private static final int SETTING = Menu.FIRST + 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		data = app.getDataMusic();
		data.clear();

		adapter = ViewFactory
				.getDataAdapter(this, AdapterSelect.musickey, data);

		adapterView = ViewFactory.getAdapterView(this, AdapterSelect.musickey);

		adapterView.setAdapter(adapter);

		imageView = new ImageView(this);

		LayoutParams param = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		param.height = 182;
		param.gravity = Gravity.CENTER;

		imageView.setAdjustViewBounds(true);
		imageView.setScaleType(ScaleType.FIT_CENTER);

		bodyLayout.addView(imageView, param);
		bodyLayout.addView(adapterView);

		dataUrl = Confi.getInstance().getMusicApiUrl(tid);

		asyncJson = new AsyncJson(this);
		asyncJson.addInterFace(this);
		asyncJson.execute(dataUrl);

		adapterView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(MusicActivity.this,
						MusicDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("tabName", title);
				bundle.putInt("index", position);
				bundle.putString("tid", tid);
				intent.putExtras(bundle);
				MusicActivity.this.startActivity(intent);
			}

		});
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
					if (jsonObject2.getString("music_status").equals("1")) {
						MusicModel model = new MusicModel();
						model.setId(jsonObject2.getString("music_id"));
						model.setTitle(jsonObject2.getString("music_title"));
						model.setThumb(jsonObject2.getString("music_thumb"));
						model.setUrl(jsonObject2.getString("music_url"));
						model.setAuthor(jsonObject2.getString("msinger"));
						model.setIsDownload(jsonObject2.getString("download"));
						model.setDescription(Html.fromHtml(
								jsonObject2.getString("music_caption"))
								.toString());
						data.add(model);
						adapter.notifyDataSetChanged();
					}
				}

				if (data.size() > 0) {
					asyncImage = new AsyncImage();
					asyncImage.addImageLoadCallback(this);
					asyncImage.execute(((MusicModel) data.get(0)).getThumb());
				}

			} catch (JSONException e) {
				callJoinFailure(dataUrl, e.getMessage().toString());
			}
		}
	}

	@Override
	public void callImageSuccess(String url, Drawable drawable) {
		super.callImageSuccess(url, drawable);
		imageView.setBackgroundColor(this.getResources()
				.getColor(R.color.black));
		if (data.size()>0 && url.equals(((MusicModel) data.get(0)).getThumb())) {
			if (drawable != null)
				imageView.setImageDrawable(drawable);
			else
				imageView.setImageResource(R.drawable.music_cover);
		}
	}

	@Override
	public void callImageFailure(String url) {
		// TODO Auto-generated method stub

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
//			 asyncImage = new AsyncImage();
//			 asyncImage.addImageLoadCallback(this);
//			 asyncImage.execute(((MusicModel) data.get(0)).getThumb());
			asyncJson = new AsyncJson(this);
			asyncJson.addInterFace(this);
			asyncJson.execute(dataUrl);

		}
			break;
		case DOWNLOAD: {
			Intent intent = new Intent(MusicActivity.this,
					DownloadActivity.class);
			intent.putExtra("tabName", "Download");
			MusicActivity.this.startActivity(intent);
		}
			break;
		case SETTING: {
			Intent intent = new Intent(MusicActivity.this,
					SettingActivity.class);
			intent.putExtra("tabName", "Setting");

			MusicActivity.this.startActivity(intent);
		}
			break;
		}
		return true;
	}
}
