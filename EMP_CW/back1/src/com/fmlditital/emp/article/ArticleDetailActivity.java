package com.fmlditital.emp.article;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.fmlditital.emp.R;
import com.fmlditital.emp.adapter.ViewPagerAdapter;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.async.AsyncRefreshJson;
import com.fmlditital.emp.base.DetailBaseActivity;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.dowmload.DownloadActivity;
import com.fmlditital.emp.gallery.ShowPictureActivity;
import com.fmlditital.emp.interf.AddCommentCallback;
import com.fmlditital.emp.model.AriticleModel;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.preferences.SettingActivity;
import com.fmlditital.emp.share.ShareManage;
import com.fmlditital.emp.share.info.ShareInfo;
import com.fmlditital.emp.share.info.ShareText;
import com.fmlditital.emp.view.CommentListView;
import com.fmlditital.emp.view.TabView;

public class ArticleDetailActivity extends DetailBaseActivity implements
		OnClickListener, OnPageChangeListener, AddCommentCallback {

	private static final int REFRESH = Menu.FIRST;
	private static final int DOWNLOAD = Menu.FIRST + 1;
	private static final int SETTING = Menu.FIRST + 2;

	private String articleDetailUrl = null;

	private ViewPager mPager;
	private List<View> listViews;
	private WebView[] mWebViews = null;

	private AsyncJson async = null;

	private String htmlDetail = null;

	private CharSequence[] shareContext = { "Share to Sina WeiBo" };

	private LinearLayout detailLinearLayout = null;
	private CommentListView commentListView = null;
	private TabView touchView = null;
	private String detail = Global.DETAIL, comment = Global.COMMENT;

	private String path = null;
	private UIConfig uiConfig = Confi.getInstance().getuIConfig();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		path = this.getIntent().getExtras().getString("path");
		if (path.equals("list")) {
			data = app.getDataAriticle();
		} else if (path.equals("bottom")) {
			data = app.getBottomDataAriticle();
		}

		data_name = ((AriticleModel) data.get(index)).getTitle();

		touchBarLayout.setVisibility(View.VISIBLE);

		touchTitleTextView.setVisibility(View.VISIBLE);
		touchTitleTextView.setText((index + 1) + "/" + data.size());

		detailLinearLayout = new LinearLayout(this);
		detailLinearLayout.setOrientation(LinearLayout.VERTICAL);
		detailLinearLayout.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		touchView = new TabView(this);
		touchView.addTitle(detail);
		touchView.addTitle(comment);
		touchView.display(detail);
		touchView.addCommentCallback(this);
		bodyLayout.addView(touchView);
		bodyLayout.addView(detailLinearLayout);

		listViews = new ArrayList<View>();
		mWebViews = new WebView[data.size()];
		for (int i = 0; i < data.size(); i++) {
			mWebViews[i] = new WebView(this);
			mWebViews[i].setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			// mWebViews[i].setFocusable(false);
			// mWebViews[i].setFocusableInTouchMode(false);
			mWebViews[i].setBackgroundColor(0);

			WebSettings webSettings = mWebViews[i].getSettings();
			webSettings.setJavaScriptEnabled(false);
			webSettings.setSupportZoom(false);
			webSettings.setBuiltInZoomControls(false);
			listViews.add(mWebViews[i]);

		}

		mPager = new ViewPager(this);
		mPager.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		mPager.setAdapter(new ViewPagerAdapter(listViews));
		mPager.setCurrentItem(index);
		mPager.setOnPageChangeListener(this);
		detailLinearLayout.addView(mPager);

		data_id = ((AriticleModel) data.get(index)).getId();
		articleDetailUrl = Confi.getInstance().getArticleDetailApiUrl(data_id);

		async = new AsyncJson(this);
		async.addInterFace(this);
		async.execute(articleDetailUrl);

		touchShareImageView.setOnClickListener(this);
	}

	private void showPage() {

		mPager.setCurrentItem(index);
		touchTitleTextView.setText((index + 1) + "/" + data.size());

		data_id = ((AriticleModel) data.get(index)).getId();
		articleDetailUrl = Confi.getInstance().getArticleDetailApiUrl(data_id);
		async = new AsyncJson(this);
		async.addInterFace(this);
		async.execute(articleDetailUrl);
	}

	private void shareTo(int to) {
		ShareInfo shareInfo = new ShareText();
		shareInfo.constructContext(((AriticleModel) data.get(index))
				.getSummary());
		ShareManage.getInstance().share2weibo(ArticleDetailActivity.this,
				shareInfo);
	}

	private void showBody(String flag) {
		if (flag.equals(detail)) {
			detailLinearLayout.removeAllViews();
			detailLinearLayout.addView(mPager);
		} else if (flag.equals(comment)) {

			detailLinearLayout.removeAllViews();
			if (commentListView == null) {
				commentListView = new CommentListView(
						ArticleDetailActivity.this, tid, data_id, data_name);
				commentListView.setAddCallback(this);
			}
			detailLinearLayout.addView(commentListView);
		}
		detailLinearLayout.invalidate();
	}

	private String getHtml(String path, String title, String time,
			String summary, String context) {
		String result = null;
		AssetManager am = null;
		ByteArrayOutputStream bo = null;
		InputStream is = null;
		int i;
		try {
			StringBuffer sb = new StringBuffer();
			am = this.getAssets();
			;
			is = am.open(path);
			bo = new ByteArrayOutputStream();
			while ((i = is.read()) != -1)
				bo.write(i);
			bo.close();
			is.close();

			sb.append(bo.toString());
			sb.replace(sb.indexOf("<!--color-->"), sb.indexOf("<!--color-->")
					+ "<!--color-->".length(), Confi.getInstance()
					.getuIConfig().getApp_text_color());
			sb.insert(sb.indexOf("<!--title-->") + "<!--title-->".length(),
					title);
			sb.insert(sb.indexOf("<!--time-->") + "<!--time-->".length(), time);
			sb.insert(sb.indexOf("<!--content-->") + "<!--content-->".length(),
					context);

			result = sb.toString();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void callJoinSuccess(String url, JSONObject json) {
		super.callJoinSuccess(url, json);
		if (url.equals(articleDetailUrl)) {
			try {
				JSONArray jsonArray = json.getJSONArray("datas");

				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(0);

				htmlDetail = getHtml("article.html",
						jsonObject2.getString("article_title"),
						jsonObject2.getString("artpublish_time"),
						jsonObject2.getString("article_summary"),
						jsonObject2.getString("article_content")).replace(
						"\\\"", "\"");

				mWebViews[index].loadDataWithBaseURL(null, htmlDetail,
						"text/html", "utf-8", null);
			} catch (JSONException e) {
				// e.printStackTrace();
			}
			mWebViews[index].invalidate();
			detailLinearLayout.invalidate();
		}

	}

	@Override
	public void showDetailOrCommentList(String title) {
		super.showDetailOrCommentList(title);
		if (title.equals(detail)) {
			showBody(title);
		} else if (title.equals(comment)) {
			showBody(title);
		}
	}

	@Override
	public void onClick(View v) {
		if (v == touchShareImageView) {
			showShareDialog(index);
		}

	}

	// å�‘å¸ƒæ–°æµªå¾®å�šæ–¹æ³•
	private void showShareDialog(final int index) {
		// æ–°å»ºAlertDialog
		final AlertDialog alertDialog = new AlertDialog.Builder(
				ArticleDetailActivity.this).create();
		alertDialog.show();
		// è¯»å�–XMLé…�ç½®å¸ƒå±€æ–‡ä»¶
		alertDialog.setContentView(R.layout.newstoshare);
		LinearLayout newBg = (LinearLayout) alertDialog
				.findViewById(R.id.newsshareBg);
		// è®¾ç½®LinearLayoutèƒŒæ™¯è‰²
		newBg.setBackgroundColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));
		// è®¾ç½®LinearLayouté€�æ˜Žåº¦
		newBg.getBackground().setAlpha(55);
		// èŽ·å�–Down
		TextView txtShareTextView = (TextView) alertDialog
				.findViewById(R.id.news_textview_share);
		// è®¾ç½®DownLoadå­—ä½“é¢œè‰²
		txtShareTextView.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));
		txtShareTextView.setText(shareContext[0]);
		txtShareTextView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				shareTo(index);
				alertDialog.cancel();
			}
		});

		// AlertDialog.Builder builder = new AlertDialog.Builder(
		// ArticleDetailActivity.this);
		// builder.setItems(shareContext, new DialogInterface.OnClickListener()
		// {
		// public void onClick(DialogInterface dialog, int item) {
		// switch (item) {
		// case 0: {
		// shareTo(index);
		// }
		//
		// break;
		// }
		//
		// }
		// });
		// AlertDialog alert = builder.create();
		// alert.show();

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
			data_id = ((AriticleModel) data.get(index)).getId();
			articleDetailUrl = Confi.getInstance().getArticleDetailApiUrl(
					data_id);

			AsyncRefreshJson asyncRefreshJson = new AsyncRefreshJson(this);
			asyncRefreshJson.addInterFace(this);
			asyncRefreshJson.execute(articleDetailUrl);
		}
			break;
		case DOWNLOAD: {
			Intent intent = new Intent(ArticleDetailActivity.this,
					DownloadActivity.class);
			intent.putExtra("tabName", "Download");
			ArticleDetailActivity.this.startActivity(intent);
		}
			break;
		case SETTING: {
			Intent intent = new Intent(ArticleDetailActivity.this,
					SettingActivity.class);
			intent.putExtra("tabName", "Setting");

			ArticleDetailActivity.this.startActivity(intent);
		}
			break;
		}
		return true;
	}

	public void onPageScrollStateChanged(int arg0) {

	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	public void onPageSelected(int arg0) {
		index = arg0;
		if (index < 0) {
			index = 0;
			LayoutInflater factory = getLayoutInflater();
			View view = factory.inflate(R.layout.mytoast, null);
			Toast myToast = new Toast(ArticleDetailActivity.this);
			myToast.setView(view);
			LinearLayout myToastBg = (LinearLayout) view
					.findViewById(R.id.myToastBg);
			myToastBg.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_background_color()));
			TextView txtToast = (TextView) view
					.findViewById(R.id.myToast_TextView_txtToast);
			txtToast.setText(R.string.top);
			txtToast.setTextColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			myToast.show();
			// Toast.makeText(ArticleDetailActivity.this, R.string.top,
			// Toast.LENGTH_SHORT).show();
			return;
		} else if (index < 0) {
			index = 0;
			LayoutInflater factory = getLayoutInflater();
			View view = factory.inflate(R.layout.mytoast, null);
			Toast myToast = new Toast(ArticleDetailActivity.this);
			myToast.setView(view);
			LinearLayout myToastBg = (LinearLayout) view
					.findViewById(R.id.myToastBg);
			myToastBg.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_background_color()));
			TextView txtToast = (TextView) view
					.findViewById(R.id.myToast_TextView_txtToast);
			txtToast.setText(R.string.end);
			txtToast.setTextColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			myToast.show();
			// Toast.makeText(ArticleDetailActivity.this, R.string.end,
			// Toast.LENGTH_SHORT).show();
			return;
		}
		showPage();

	}

	@Override
	public void addCommentCallback(String url) {
		// TODO Auto-generated method stub

	}
}
