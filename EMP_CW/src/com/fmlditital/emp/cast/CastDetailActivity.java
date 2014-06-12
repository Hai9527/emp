package com.fmlditital.emp.cast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.fmlditital.emp.R;
import com.fmlditital.emp.adapter.ViewPagerAdapter;
import com.fmlditital.emp.article.ArticleDetailActivity;
import com.fmlditital.emp.async.AsyncImage;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.async.AsyncRefreshJson;
import com.fmlditital.emp.base.DetailBaseActivity;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.dowmload.DownloadActivity;
import com.fmlditital.emp.interf.ViewPagerOnPageChangeListener;
import com.fmlditital.emp.manage.AnimationManage;
import com.fmlditital.emp.model.CastModel;
import com.fmlditital.emp.preferences.SettingActivity;
import com.fmlditital.emp.share.ShareManage;
import com.fmlditital.emp.share.info.ShareInfo;
import com.fmlditital.emp.share.info.ShareText;
import com.fmlditital.emp.tool.Tools;

public class CastDetailActivity extends DetailBaseActivity implements
		OnPageChangeListener {

	private static final int REFRESH = Menu.FIRST;
	private static final int DOWNLOAD = Menu.FIRST + 1;
	private static final int SETTING = Menu.FIRST + 2;

	private String urlCast = null;

	private String htmlDetail = null, cTitle = null, cCaption = null;

	private LinearLayout detailLinearLayout = null;

	private ImageView imageView = null;
	private AsyncImage asyncImage = null;
	private String imageUrl = null;

	private AsyncJson async = null;

	private ViewPager mPager;
	private List<View> listViews;
	private WebView[] mWebViews = null;

	/** Called when the activity is first created. */
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		data = app.getDataCast();
		data_name = ((CastModel) data.get(index)).getTitle();

		touchBarLayout.setVisibility(View.VISIBLE);
		touchShareImageView.setVisibility(View.GONE);
		touchTitleTextView.setVisibility(View.VISIBLE);
		touchTitleTextView.setText((index + 1) + "/" + data.size());

		listViews = new ArrayList<View>();
		mWebViews = new WebView[data.size()];
		for (int i = 0; i < data.size(); i++) {
			mWebViews[i] = new WebView(this);
			mWebViews[i].setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			mWebViews[i].setFocusable(false);
			mWebViews[i].setFocusableInTouchMode(false);
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

		imageView = new ImageView(this);

		LayoutParams param = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		param.height = Tools.dip2px(this, 182);
		param.gravity = Gravity.CENTER;

		imageView.setAdjustViewBounds(true);
		imageView.setScaleType(ScaleType.FIT_CENTER);

		bodyLayout.addView(imageView, param);
		bodyLayout.addView(mPager);

		data_id = ((CastModel) data.get(index)).getId();
		urlCast = Confi.getInstance().getCastDetailApiUrl(data_id);

		async = new AsyncJson(this);
		async.addInterFace(this);
		async.execute(urlCast);

		imageUrl = ((CastModel) data.get(index)).getImage();
		asyncImage = new AsyncImage();
		asyncImage.addImageLoadCallback(this);
		asyncImage.execute(imageUrl);
	}

	
	public void callJoinSuccess(String url, JSONObject json) {
		super.callJoinSuccess(url, json);
		if (url.equals(urlCast)) {
			try {
				JSONArray jsonArray = json.getJSONArray("datas");

				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(0);

				cTitle = jsonObject2.getString("cast_title");
				cCaption = jsonObject2.getString("cast_content");

				imageUrl = jsonObject2.getString("cast_image");

				htmlDetail = getHtml("cast.html", cTitle, cCaption);

			} catch (JSONException e) {
				// e.printStackTrace();
			}

			asyncImage = new AsyncImage();
			asyncImage.addImageLoadCallback(this);
			asyncImage.execute(imageUrl);

		}

	}

	private String getHtml(String path, String name, String context) {

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
			sb.insert(sb.indexOf("<!--name-->") + "<!--name-->".length(), name);
			sb.insert(sb.indexOf("<!--content-->") + "<!--content-->".length(),
					context);
			result = sb.toString();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void showPage() {

		mPager.setCurrentItem(index);
		touchTitleTextView.setText((index + 1) + "/" + data.size());

		data_id = ((CastModel) data.get(index)).getId();
		urlCast = Confi.getInstance().getCastDetailApiUrl(data_id);
		async = new AsyncJson(this);
		async.addInterFace(this);
		async.execute(urlCast);

	}

	
	public void callImageSuccess(String url, Drawable drawable) {
		super.callImageSuccess(url, drawable);
		if (url.equals(imageUrl)) {
			
			mWebViews[index].loadDataWithBaseURL(null, htmlDetail, "text/html",
					"utf-8", null);
			mWebViews[index].invalidate();
			bodyLayout.invalidate();
			
			if (drawable != null) {
				imageView.setBackgroundColor(this.getResources().getColor(
						R.color.black));
				imageView.setImageDrawable(drawable);
				imageView.invalidate();
			}
		}
	}

	
	public void callImageFailure(String url) {
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
			data_id = ((CastModel) data.get(index)).getId();
			urlCast = Confi.getInstance().getCastDetailApiUrl(data_id);

			// imageUrl = ((CastModel) data.get(index)).getImage();
			// asyncImage = new AsyncImage();
			// asyncImage.addImageLoadCallback(this);
			// asyncImage.execute(imageUrl);

			AsyncRefreshJson asyncRefreshJson = new AsyncRefreshJson(this);
			asyncRefreshJson.addInterFace(this);
			asyncRefreshJson.execute(urlCast);
		}
			break;
		case DOWNLOAD: {
			Intent intent = new Intent(CastDetailActivity.this,
					DownloadActivity.class);
			intent.putExtra("tabName", "Download");
			CastDetailActivity.this.startActivity(intent);
		}
			break;
		case SETTING: {
			Intent intent = new Intent(CastDetailActivity.this,
					SettingActivity.class);
			intent.putExtra("tabName", "Setting");

			CastDetailActivity.this.startActivity(intent);
		}
			break;
		}
		return true;
	}

	
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	
	public void onPageSelected(int arg0) {
		index = arg0;
		if (index < 0) {
			index = 0;
			Toast.makeText(CastDetailActivity.this, R.string.top,
					Toast.LENGTH_SHORT).show();
			return;
		} else if (index < 0) {
			index = 0;
			Toast.makeText(CastDetailActivity.this, R.string.end,
					Toast.LENGTH_SHORT).show();
			return;
		}
		showPage();

	}
}
