package com.fmlditital.emp.article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fmlditital.emp.R;
import com.fmlditital.emp.app.EMPApp;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.async.AsyncMoareJson;
import com.fmlditital.emp.async.AsyncRefreshJson;
import com.fmlditital.emp.base.AdapterViewActivity;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.dowmload.DownloadActivity;
import com.fmlditital.emp.model.AriticleModel;
import com.fmlditital.emp.preferences.SettingActivity;
import com.fmlditital.emp.tool.ViewFactory;
import com.fmlditital.emp.tool.ViewFactory.AdapterSelect;

/**
 * News列表界面
 * 
 * @author
 * 
 */
public class ArticleActivity extends AdapterViewActivity implements
		OnScrollListener {
	private EMPApp app = EMPApp.getSingleton();

	private static final int REFRESH = Menu.FIRST;
	private static final int DOWNLOAD = Menu.FIRST + 1;
	private static final int SETTING = Menu.FIRST + 2;

	private Button refresh = null;

	private int count = 0, page = 0;
	private String dataMoreUrl = null;
	public static  String comment_count;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		data = app.getDataAriticle();
		data.clear();

		adapter = ViewFactory.getDataAdapter(this, AdapterSelect.newskey, data);

		adapterView = ViewFactory.getAdapterView(this, AdapterSelect.newskey);

		adapterView.setAdapter(adapter);

		bodyLayout.addView(adapterView);

		adapterView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ArticleActivity.this,
						ArticleDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("path", "list");
				bundle.putInt("index", position);
				bundle.putString("tabName", title);
				bundle.putString("tid", tid);
				bundle.putString("comment_count", comment_count);
				intent.putExtras(bundle);
				ArticleActivity.this.startActivity(intent);

			}

		});

		dataUrl = Confi.getInstance().getArticleApiUrl(tid, "1");

		asyncJson = new AsyncJson(this);
		asyncJson.addInterFace(this);
		asyncJson.execute(dataUrl);

		refresh = new Button(this);

		refresh.setGravity(Gravity.CENTER);

		refresh.setBackgroundDrawable(this.getResources().getDrawable(
				android.R.drawable.ic_popup_sync));
		RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		lp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		topBarView.addView(refresh, lp1);
		topBarView.invalidate();

		refresh.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				refresh();
			}

		});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, REFRESH, 0, getResources().getString(R.string.refresh))
				.setIcon(android.R.drawable.ic_popup_sync);

		menu.add(0, DOWNLOAD, 0, getResources().getString(R.string.download))
				.setIcon(android.R.drawable.stat_sys_download_done);
		menu.add(0, SETTING, 0, getResources().getString(R.string.setting))
				.setIcon(android.R.drawable.ic_menu_preferences);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case REFRESH: {
			refresh();
		}
			break;
		case DOWNLOAD: {
			Intent intent = new Intent(ArticleActivity.this,
					DownloadActivity.class);
			//intent.putExtra("tabName", "Download");
			intent.putExtra("tabName", "下载");
			ArticleActivity.this.startActivity(intent);
		}
			break;
		case SETTING: {
			Intent intent = new Intent(ArticleActivity.this,
					SettingActivity.class);
			intent.putExtra("tabName", "Setting");

			ArticleActivity.this.startActivity(intent);
		}
			break;
		}
		return true;
	}

	public void callJoinSuccess(String url, JSONObject json) {
		super.callJoinSuccess(url, json);
		if (url.equals(dataUrl)) {
			data.clear();
			try {
				JSONArray jsonArray = json.getJSONArray("datas");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
					if (jsonObject2.getString("article_status").equals("1")) {
						AriticleModel model = new AriticleModel();
						model.setId(jsonObject2.getString("article_id"));
						model.setTitle(jsonObject2.getString("article_title"));
						model.setTime(jsonObject2.getString("artpublish_time"));
						model.setSummary(jsonObject2
								.getString("article_summary"));
						model.setContent(jsonObject2
								.getString("article_content"));
						model.setThumbUrl(jsonObject2.getString("articon"));

						model.setComment_count(jsonObject2
								.getString("comment_counts"));
						model.setPlayers_count(jsonObject2
								.getString("players_counts"));
						model.setLikes_count(jsonObject2
								.getString("likes_counts"));
						data.add(model);
						comment_count = jsonObject2.getString("comment_counts");
						// adapter.notifyDataSetChanged();
					}
				}

				adapter.notifyDataSetChanged();

				count = Integer.parseInt(json.getString("count"));
				page = Integer.parseInt(json.getString("page"));

				// Log.v("",
				// ">>>>>>>>>>>>>>>>>>>>>> Math.ceil(count / 10)  "+Math.ceil(count
				// / 10));
				// Log.v("",
				// ">>>>>>>>>>>>>>>>>>>>>>(Math.floor(count/10.0)+1)   "+(Math.floor(count/10.0)+1));

				// Log
				// if (Math.ceil(count / 10) > page) {
				if ((Math.floor(count / 10.0) + 1) > page) {
					((ListView) adapterView).setOnScrollListener(this);
				}

			} catch (JSONException e) {
				callJoinFailure(dataUrl, e.getMessage().toString());
			}
		}
	}

	private void refresh() {
		dataUrl = Confi.getInstance().getArticleApiUrl(tid, "1");
		AsyncRefreshJson asyncRefreshJson = new AsyncRefreshJson(this);
		asyncRefreshJson.addInterFace(this);
		asyncRefreshJson.execute(dataUrl);
	}

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if (firstVisibleItem + visibleItemCount == totalItemCount) {
			if ((Math.floor(count / 10.0) + 1) > page) {
				page++;
				dataMoreUrl = Confi.getInstance().getArticleApiUrl(tid,
						String.valueOf(page));

				AsyncMoareJson asyncMoareJson = new AsyncMoareJson(this);
				asyncMoareJson.addInterFace(this);
				asyncMoareJson.execute(dataMoreUrl);
			}
		}

	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	public void callJoinMore(String url, JSONObject json) {
		if (url.equals(dataMoreUrl)) {
			try {
				JSONArray jsonArray = json.getJSONArray("datas");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
					if (jsonObject2.getString("article_status").equals("1")) {
						AriticleModel model = new AriticleModel();
						model.setId(jsonObject2.getString("article_id"));
						model.setTitle(jsonObject2.getString("article_title"));
						model.setTime(jsonObject2.getString("artpublish_time"));
						model.setSummary(jsonObject2
								.getString("article_summary"));
						model.setContent(jsonObject2
								.getString("article_content"));
						model.setThumbUrl(jsonObject2.getString("articon"));

						model.setComment_count(jsonObject2
								.getString("comment_counts"));
						model.setPlayers_count(jsonObject2
								.getString("players_counts"));
						model.setLikes_count(jsonObject2
								.getString("likes_counts"));
						data.add(model);
						adapter.notifyDataSetChanged();
					}
				}

				count = Integer.parseInt(json.getString("count"));
				page = Integer.parseInt(json.getString("page"));
			} catch (JSONException e) {
				callJoinFailure(dataUrl, e.getMessage().toString());
			}
		}
	}
}
