package com.fmlditital.emp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.fmlditital.emp.async.AsyncImage;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.async.AsyncRefreshJson;
import com.fmlditital.emp.base.DefaultActivity;
import com.fmlditital.emp.base.TopBarActivity;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.dowmload.DownloadActivity;
import com.fmlditital.emp.interf.ImageLoadCallback;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.model.NavigationModel;
import com.fmlditital.emp.preferences.SettingActivity;

public class PageActivity extends TopBarActivity implements ImageLoadCallback {

	private static final int REFRESH = Menu.FIRST;
	private static final int DOWNLOAD = Menu.FIRST + 1;
	private static final int SETTING = Menu.FIRST + 2;

	private List<NavigationModel> tabList = app.getTabList();
	private NavigationModel PageModelData = null;
	private WebView mWebView = null;
	private String htmlDetail = null;
	private String currentPage = null;

	private LayoutParams param = null;
	private String page_template[] = { "0", "1", "2", "3", "4" };

	private AsyncImage asyncImage = null;
	private String bodyLinearLayoutUrl = null;
	private String pageUrl = null;

	// UI Config
	private UIConfig uiConfig = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		uiConfig = Confi.getInstance().getuIConfig();

		for (NavigationModel model : tabList) {
			if (model.getFunction().equals(Global.PageFunction)) {
				PageModelData = model;
			}
		}

		if (PageModelData != null) {
			mWebView = new WebView(this);
			mWebView.setBackgroundColor(0);
			mWebView.setHorizontalScrollbarOverlay(false);
			mWebView.setVerticalScrollbarOverlay(false);
			mWebView.setHorizontalScrollBarEnabled(false); 
			mWebView.setVerticalScrollBarEnabled(false); 
			mWebView.setScrollBarStyle (0);
			
//			// mWebView.setc
//			WebSettings webSettings = mWebView.getSettings();
//			webSettings.setUseWideViewPort(true);
//			webSettings.setJavaScriptEnabled(true);
//			webSettings.setSupportZoom(false);

			param = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT, 1.0f);
			mWebView.setLayoutParams(param);

			bodyLayout.addView(mWebView);

			pageUrl = Confi.getInstance().getPageApiUrl(tid);

			asyncJson = new AsyncJson(this);
			asyncJson.addInterFace(this);
			asyncJson.execute(pageUrl);
		}

	}

	private String getHtml(String path, String background, String height,
			String editor) {
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

			if (sb.indexOf("<!--height-->") > 0) {
				sb.replace(sb.indexOf("<!--height-->"),
						sb.indexOf("<!--height-->") + "<!--height-->".length(),
						height);
			}

			sb.insert(sb.indexOf("<!--content-->") + "<!--content-->".length(),
					editor);

			result = sb.toString();
			return result;
		} catch (IOException e) {
		}
		return null;
	}

	@Override
	public void callJoinSuccess(String url, JSONObject json) {
		super.callJoinSuccess(url, json);
		if (url.equals(pageUrl)) {
			try {
				JSONArray jsonArray = json.getJSONArray("datas");
				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(0);

				param = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT, 1.0f);

				if ((jsonObject2.getString("page_template")).trim().equals(
						page_template[0]))
					;

				// for left html
				if ((jsonObject2.getString("page_template")).trim().equals(
						page_template[1]))
					param.rightMargin = DefaultActivity.getScreenWidth() / 2;
				// for top html
				if ((jsonObject2.getString("page_template")).trim().equals(
						page_template[2]))
					param.bottomMargin = DefaultActivity.getScreenHeight() / 2;
				// for right html
				if ((jsonObject2.getString("page_template")).trim().equals(
						page_template[3]))
					param.leftMargin = DefaultActivity.getScreenWidth() / 2;
				// for bottom html
				if ((jsonObject2.getString("page_template")).trim().equals(
						page_template[4]))
					param.topMargin = DefaultActivity.getScreenHeight() / 2;
				mWebView.setLayoutParams(param);

				bodyLinearLayoutUrl = jsonObject2.getString("page_background");

				htmlDetail = getHtml("page.html",
						jsonObject2.getString("page_background"), (480 / 2)
								+ "", jsonObject2.getString("page_editor"));
				
				Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>page_editor  "+jsonObject2.getString("page_editor"));

				mWebView.loadDataWithBaseURL(null, htmlDetail, "text/html",
						"utf-8", null);
				
				if ((jsonObject2.getString("page_template")).trim().equals(
						page_template[0])) {
					bodyLayout.setBackgroundColor(Color.parseColor(uiConfig
							.getApp_background_color()));
				} else {
					asyncImage = new AsyncImage();
					asyncImage.addImageLoadCallback(this);
					asyncImage.execute(bodyLinearLayoutUrl);
				}
			} catch (JSONException e) {
				// e.printStackTrace();
			}
//			mWebView.invalidate();
		}

	}

	@Override
	public void callImageSuccess(String url, Drawable drawable) {
		if (drawable != null)
			bodyLayout.setBackgroundDrawable(drawable);
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
			AsyncRefreshJson asyncRefreshJson = new AsyncRefreshJson(this);
			asyncRefreshJson.addInterFace(this);
			asyncRefreshJson.execute(pageUrl);
		}
			break;
		case DOWNLOAD: {
			Intent intent = new Intent(PageActivity.this,
					DownloadActivity.class);
			intent.putExtra("tabName", "Download");
			PageActivity.this.startActivity(intent);
		}
			break;	
		case SETTING: {
			Intent intent = new Intent(PageActivity.this, SettingActivity.class);
			intent.putExtra("tabName", "Setting");

			PageActivity.this.startActivity(intent);
		}
			break;
		}
		return true;
	}
}
