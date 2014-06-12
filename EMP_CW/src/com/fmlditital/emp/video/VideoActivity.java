package com.fmlditital.emp.video;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.BaseAdapter;

import com.fmlditital.emp.R;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.async.AsyncRefreshJson;
import com.fmlditital.emp.base.AdapterViewActivity;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.dowmload.DownloadActivity;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.model.VideoModel;
import com.fmlditital.emp.preferences.SettingActivity;
import com.fmlditital.emp.tool.Tools;
import com.fmlditital.emp.tool.VideoViewpagerAdapter;
import com.fmlditital.emp.tool.ViewFactory;
import com.fmlditital.emp.tool.ViewFactory.AdapterSelect;
import com.fmlditital.emp.view.TabView;

public class VideoActivity extends AdapterViewActivity {

	private static final int REFRESH = Menu.FIRST;
	private static final int DOWNLOAD = Menu.FIRST + 1;
	private static final int SETTING = Menu.FIRST + 2;

	private List<BaseModel> popularData = null;
	private String popularUrl = null;
	private AsyncJson popularAsyncJson = null;
	protected BaseAdapter popularAdapter = null;

	private TabView touchView = null;
	private String recent = Global.RECENT, popular = Global.POPULAR;
	private String currentPage = recent;
	public static String popularType = "popular", currentType = "recent";
	public static String currenttype = currentType;
	private LinearLayout detailLinearLayout = null;
	private ListView recentListView = null, puplarListView = null;
	private ViewPager vp;
	public boolean isrunning = true;
	private Handler handler = new Handler();
	private int i = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		data = app.getDataVideo();
		data.clear();
		popularData = app.getDataVideopopular();
		popularData.clear();

		dataUrl = Confi.getInstance().getVideoApiUrl(tid);
		popularUrl = Confi.getInstance().getVideoPopularApiUrl(tid);

		// touchView = new TabView(this);
		// touchView.addTitle(recent);
		// touchView.addTitle(popular);
		// touchView.display(recent);
		// touchView.addCommentCallback(this);
		vp = new ViewPager(this);
		VideoViewpagerAdapter vvp = new VideoViewpagerAdapter(this);
		vp.setAdapter(vvp);
		vvp.change(getList());
		recentListView = (ListView) ViewFactory.getAdapterView(this,
				AdapterSelect.videokey);
		puplarListView = (ListView) ViewFactory.getAdapterView(this,
				AdapterSelect.videokey);

		adapter = ViewFactory
				.getDataAdapter(this, AdapterSelect.videokey, data);
		popularAdapter = ViewFactory.getDataAdapter(this,
				AdapterSelect.videokey, popularData);

		recentListView.setAdapter(adapter);
		puplarListView.setAdapter(popularAdapter);

		detailLinearLayout = new LinearLayout(this);
		detailLinearLayout.setOrientation(LinearLayout.VERTICAL);

		detailLinearLayout.addView(recentListView);

		// bodyLayout.addView(touchView);
		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT,
				Tools.dip2px(this, 150));
		bodyLayout.addView(vp,lp);
		bodyLayout.addView(detailLinearLayout);

		dataUrl = Confi.getInstance().getVideoApiUrl(tid);
		// recentListView.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		//
		// VideoModel model = (VideoModel) parent
		// .getItemAtPosition(position);
		// String videoName = model.getTitle();
		// Intent intent = new Intent(VideoActivity.this,
		// VideoDetailActivity.class);
		// Bundle bundle = new Bundle();
		// bundle.putString("tabName", title);
		// bundle.putString("videoName", videoName);
		// bundle.putString("tid", tid);
		// bundle.putInt("index", position);
		// bundle.putString("type", "recent");
		// intent.putExtras(bundle);
		//
		// VideoActivity.this.startActivity(intent);
		// }
		//
		// });
		//
		// puplarListView.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// VideoModel model = (VideoModel) parent
		// .getItemAtPosition(position);
		// String videoName = model.getTitle();
		// Intent intent = new Intent(VideoActivity.this,
		// VideoDetailActivity.class);
		// Bundle bundle = new Bundle();
		// bundle.putString("tabName", title);
		// bundle.putString("videoName", videoName);
		// bundle.putString("tid", tid);
		// bundle.putInt("index", position);
		// bundle.putString("type", "popular");
		// intent.putExtras(bundle);
		//
		// VideoActivity.this.startActivity(intent);
		// }
		//
		// });

	}

	@Override
	protected void onResume() {
		super.onResume();
		asyncJson = new AsyncJson(this);
		asyncJson.addInterFace(this);
		asyncJson.execute(dataUrl);
	}

	private void showBody(String flag) {
		if (flag.equals(recent)) {
			detailLinearLayout.removeAllViews();
			detailLinearLayout.addView(recentListView);
			currentPage = recent;
			currenttype = popularType;
		} else if (flag.equals(popular)) {
			popularAsyncJson = new AsyncJson(this);
			popularAsyncJson.addInterFace(this);
			popularAsyncJson.execute(popularUrl);
			detailLinearLayout.removeAllViews();
			detailLinearLayout.addView(puplarListView);
			currentPage = popular;
			currenttype = currentType;
		}
		detailLinearLayout.invalidate();
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

					if (jsonObject2.getString("video_status").equals("1")) {
						VideoModel model = new VideoModel();
						model.setId(jsonObject2.getString("video_id"));
						model.setTitle(jsonObject2.getString("video_title"));
						model.setThumb(jsonObject2.getString("video_thumb"));
						model.setTime(jsonObject2.getString("video_time"));
						model.setUrl(jsonObject2.getString("video_url"));
						model.setDescription(jsonObject2
								.getString("video_caption"));
						model.setSize(jsonObject2.getString("vsize"));
						model.setIsDownload(jsonObject2
								.getString("vis_download"));
						model.setComment_count(jsonObject2
								.getInt("comment_counts"));
						model.setPlayers_count(jsonObject2
								.getInt("players_counts"));
						model.setLikes_count(jsonObject2.getInt("likes_counts"));
						data.add(model);
						adapter.notifyDataSetChanged();
					}
				}
			} catch (JSONException e) {
				callJoinFailure(url, e.getMessage().toString());
			}
		}

		if (url.equals(popularUrl)) {
			popularData.clear();

			try {
				JSONArray jsonArray = json.getJSONArray("datas");

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
					if (jsonObject2.getString("video_status").equals("1")) {
						VideoModel model = new VideoModel();
						model.setId(jsonObject2.getString("video_id"));
						model.setTitle(jsonObject2.getString("video_title"));
						model.setThumb(jsonObject2.getString("video_thumb"));
						model.setTime(jsonObject2.getString("video_time"));
						model.setUrl(jsonObject2.getString("video_url"));
						model.setDescription(jsonObject2
								.getString("video_caption"));
						model.setSize(jsonObject2.getString("vsize"));
						model.setIsDownload(jsonObject2
								.getString("vis_download"));
						model.setComment_count(jsonObject2
								.getInt("comment_counts"));
						model.setPlayers_count(jsonObject2
								.getInt("players_counts"));
						model.setLikes_count(jsonObject2.getInt("likes_counts"));
						popularData.add(model);
						popularAdapter.notifyDataSetChanged();
					}
				}
			} catch (JSONException e) {
				callJoinFailure(url, e.getMessage().toString());
			}
		}

	}

	@Override
	public void callJoinFailure(String url, String failMessage) {
		super.callJoinFailure(url, failMessage);
	}

	@Override
	public void showDetailOrCommentList(String title) {
		super.showDetailOrCommentList(title);
		if (title.equals(recent)) {
			showBody(title);
		} else if (title.equals(popular)) {
			showBody(title);
		}
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
			if (currentPage.equals(recent)) {
				dataUrl = Confi.getInstance().getVideoApiUrl(tid);
				AsyncRefreshJson asyncRefreshJson = new AsyncRefreshJson(this);
				asyncRefreshJson.addInterFace(this);
				asyncRefreshJson.execute(dataUrl);
			} else if (currentPage.equals(popular)) {
				popularAsyncJson = new AsyncJson(this);
				popularAsyncJson.addInterFace(this);
				popularAsyncJson.execute(popularUrl);
			}

		}
			break;
		case DOWNLOAD: {
			Intent intent = new Intent(VideoActivity.this,
					DownloadActivity.class);
			// intent.putExtra("tabName", "Download");
			intent.putExtra("tabName", "下载");
			VideoActivity.this.startActivity(intent);
		}
			break;
		case SETTING: {
			Intent intent = new Intent(VideoActivity.this,
					SettingActivity.class);
			intent.putExtra("tabName", "Setting");

			VideoActivity.this.startActivity(intent);
		}
			break;
		}
		return true;
	}

	private List<Integer> getList() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(R.drawable.sample_0);
		list.add(R.drawable.sample_1);
		list.add(R.drawable.sample_2);
		list.add(R.drawable.sample_3);
		return list;
	}

	private Runnable runnable = new Runnable() {

		public void run() {
			if (isrunning == true) {
				handler.postDelayed(this, 3000);
				i++;

				if (i > getList().size()) {
					i = 0;
				}
				vp.setCurrentItem(i);
			}
		}

	};

	protected void onStop() {
		super.onStop();
		isrunning = false;
	};

	protected void onRestart() {
		super.onRestart();
		isrunning = true;
	}

	protected void onStart() {
		super.onStart();
		handler.post(runnable);
	}
}
