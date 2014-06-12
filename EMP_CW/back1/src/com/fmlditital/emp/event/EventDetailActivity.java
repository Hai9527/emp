package com.fmlditital.emp.event;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.fmlditital.emp.R;
import com.fmlditital.emp.adapter.ViewPagerAdapter;
import com.fmlditital.emp.async.AsyncImage;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.async.AsyncRefreshJson;
import com.fmlditital.emp.base.DetailBaseActivity;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.dowmload.DownloadActivity;
import com.fmlditital.emp.model.EventModel;
import com.fmlditital.emp.preferences.SettingActivity;
import com.fmlditital.emp.share.ShareManage;
import com.fmlditital.emp.share.info.ShareInfo;
import com.fmlditital.emp.share.info.ShareText;
import com.fmlditital.emp.tool.Tools;

public class EventDetailActivity extends DetailBaseActivity implements
		OnClickListener, OnPageChangeListener {

	private String eventUrl = null;

	private ViewPager mPager;
	private List<View> listViews;
	private WebView[] mWebViews = null;

	private String htmlDetail = null;

	private AsyncJson async = null;

	private ImageView imageView = null;
	private AsyncImage asyncImage = null;
	private String imageUrl = null;
	private String type = null;

	private String[] shareContext = new String[4];

	private String[] paseContext = { "Attending (count)",
			"Might Attend(count)", "Not Attend (count)" };

	private String[] jionCount = { "0", "0", "0" };
	private final String[] insertJoin = { "1", "0", "-1" };

	private String STORE_NAME = "Event";
	private SharedPreferences sharedPreferences = null;

	private static final int REFRESH = Menu.FIRST;
	private static final int DOWNLOAD = Menu.FIRST + 1;
	private static final int SETTING = Menu.FIRST + 2;

	private UIConfig uiConfig = Confi.getInstance().getuIConfig();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		type = this.getIntent().getExtras().getString("type");
		if (type.equals("upcoming")) {
			data = app.getDataEvent();
		} else if (type.equals("past")) {
			data = app.getDataEventPast();
		}

		data_name = ((EventModel) data.get(index)).getTitle();

		touchTitleTextView.setVisibility(View.VISIBLE);
		touchTitleTextView.setText((index + 1) + "/" + data.size());

		imageView = new ImageView(this);
		LayoutParams param = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		param.height = Tools.dip2px(this, 182);
		param.gravity = Gravity.CENTER;
		imageView.setAdjustViewBounds(true);
		imageView.setScaleType(ScaleType.FIT_CENTER);

		touchBarLayout.setVisibility(View.VISIBLE);

		listViews = new ArrayList<View>();
		mWebViews = new WebView[data.size()];
		for (int i = 0; i < data.size(); i++) {
			mWebViews[i] = new WebView(this);
			mWebViews[i].setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			mWebViews[i].setFocusable(false);
			mWebViews[i].setFocusableInTouchMode(false);
			// mWebViews[i].getSettings().setJavaScriptEnabled(true);
			// mWebViews[i].getSettings().setUseWideViewPort(true);

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

		bodyLayout.addView(imageView, param);
		bodyLayout.addView(mPager);
		// bodyLayout.setOnTouchListener(this);

		data_id = ((EventModel) data.get(index)).getId();
		eventUrl = Confi.getInstance().getEventDetailApiUrl(tid, data_id, type);
		async = new AsyncJson(this);
		async.addInterFace(this);
		async.execute(eventUrl);

		imageUrl = ((EventModel) data.get(index)).getImage();
		asyncImage = new AsyncImage();
		asyncImage.addImageLoadCallback(this);
		asyncImage.execute(imageUrl);

		touchShareImageView.setOnClickListener(this);
	}

	private void showPage() {
		mPager.setCurrentItem(index);
		touchTitleTextView.setText((index + 1) + "/" + data.size());

		data_id = ((EventModel) data.get(index)).getId();
		eventUrl = Confi.getInstance().getEventDetailApiUrl(tid, data_id, type);
		async = new AsyncJson(this);
		async.addInterFace(this);
		async.execute(eventUrl);

		imageUrl = ((EventModel) data.get(index)).getImage();
		asyncImage = new AsyncImage();
		asyncImage.addImageLoadCallback(this);
		asyncImage.execute(imageUrl);
	}

	@Override
	public void onClick(View v) {
		if (v == touchShareImageView) {
			showShareDialog(index);
		}

	}

	public void joinCount() {
		shareContext[0] = "Share to Sina WeiBo";
		shareContext[1] = "Attending (count)";
		shareContext[2] = "Might Attend(count)";
		shareContext[3] = "Not Attend (count)";

		if (type.equals("upcoming")) {
			for (int i = 1; i < shareContext.length; i++) {
				shareContext[i] = shareContext[i].replace("count",
						jionCount[i - 1]);
			}
		} else if (type.equals("past")) {
			for (int i = 0; i < paseContext.length; i++) {
				paseContext[i] = paseContext[i].replace("count", jionCount[i]);
			}
		}

	}

	public void insertJoin(String join) {
		String insertPath = Confi.getInstance().getuUpadataEventApiUrl(data_id,
				join);

		sharedPreferences = getSharedPreferences(STORE_NAME, MODE_PRIVATE);
		if (sharedPreferences.getString(data_id, "").equals("")) {
			InsetEventDetailAsync async = new InsetEventDetailAsync();
			async.execute(insertPath);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString(data_id, data_id);
			editor.commit();
		} else {
			LayoutInflater factory = getLayoutInflater();
			View view = factory.inflate(R.layout.mytoast, null);
			Toast myToast = new Toast(EventDetailActivity.this);
			myToast.setView(view);
			LinearLayout myToastBg = (LinearLayout) view
					.findViewById(R.id.myToastBg);
			myToastBg.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_background_color()));
			TextView txtToast = (TextView) view
					.findViewById(R.id.myToast_TextView_txtToast);
			txtToast.setText("Sorry. You have made a reply to this event.");
			txtToast.setTextColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			myToast.show();
//			Toast.makeText(this, "Sorry. You have made a reply to this event.",
//					Toast.LENGTH_LONG).show();
		}
	}

	private void showShareDialog(final int index) {

		if (type.equals("upcoming")) {
			// 创建AlertDialog
			final AlertDialog alertDialog = new AlertDialog.Builder(
					EventDetailActivity.this).create();
			alertDialog.show();
			// 接收布局文件
			alertDialog.setContentView(R.layout.eventsdialog);
			// 接收linearlayout
			LinearLayout eventBg = (LinearLayout) alertDialog
					.findViewById(R.id.eventDialogBg);
			// 设置linearlayout背景色
			eventBg.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_background_color()));
			// 设置Linearlayout透明度
			eventBg.getBackground().setAlpha(150);

			// 设置三条底线的颜色
			TextView txtLineOne = (TextView) alertDialog
					.findViewById(R.id.event_textView_txtLineOne);
			txtLineOne.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			TextView txtLineTwo = (TextView) alertDialog
					.findViewById(R.id.event_textView_txtLineTwo);
			txtLineTwo.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			TextView txtLineThree = (TextView) alertDialog
					.findViewById(R.id.event_textView_txtLineThree);
			txtLineThree.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));

			// 获取Share按钮
			TextView txtShare = (TextView) alertDialog
					.findViewById(R.id.event_textView_txtShare);
			// 设置Share按钮颜色
			txtShare.setTextColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			txtShare.setText(shareContext[0]);
			txtShare.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					shareTo(index);
					alertDialog.cancel();
				}
			});
			// Attending按钮
			TextView txtAttending = (TextView) alertDialog
					.findViewById(R.id.event_textView_txtAttending);
			txtAttending.setText(shareContext[1]);
			txtAttending.setTextColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			txtAttending.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					insertJoin(insertJoin[0]);
					alertDialog.cancel();
				}
			});
			// Might Attending按钮
			TextView txtMight = (TextView) alertDialog
					.findViewById(R.id.event_textView_txtMight);
			txtMight.setText(shareContext[2]);
			txtMight.setTextColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			txtMight.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					insertJoin(insertJoin[1]);
					alertDialog.cancel();
				}
			});
			// No Attending按钮
			TextView txtNo = (TextView) alertDialog
					.findViewById(R.id.event_textView_txtNo);
			txtNo.setText(shareContext[3]);
			txtNo.setTextColor(Color.parseColor(uiConfig.getTopbar_text_color()));
			txtNo.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					alertDialog.cancel();
				}
			});

		} else if (type.equals("past")) {
			// 创建AlertDialog
			final AlertDialog alertDialog = new AlertDialog.Builder(
					EventDetailActivity.this).create();
			alertDialog.show();
			// 接收布局文件
			alertDialog.setContentView(R.layout.eventsdialog);
			// 接收linearlayout
			LinearLayout eventBg = (LinearLayout) alertDialog
					.findViewById(R.id.eventDialogBg);
			// 设置linearlayout背景色
			eventBg.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_background_color()));
			// 设置Linearlayout透明度
			eventBg.getBackground().setAlpha(150);
			// 设置三条底线的颜色
			TextView txtLineOne = (TextView) alertDialog
					.findViewById(R.id.event_textView_txtLineOne);
			txtLineOne.setVisibility(View.GONE);

			TextView txtLineTwo = (TextView) alertDialog
					.findViewById(R.id.event_textView_txtLineTwo);
			txtLineTwo.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			TextView txtLineThree = (TextView) alertDialog
					.findViewById(R.id.event_textView_txtLineThree);
			txtLineThree.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			// 获取Share按钮
			TextView txtShare = (TextView) alertDialog
					.findViewById(R.id.event_textView_txtShare);
			txtShare.setVisibility(View.GONE);
			// txtShare.setText(paseContext[0]);
			// txtShare.setTextColor(Color.parseColor(uiConfig
			// .getTopbar_text_color()));
			// txtShare.setOnClickListener(new OnClickListener() {
			//
			// public void onClick(View v) {
			// shareTo(index);
			// alertDialog.cancel();
			// }
			// });
			// Attending按钮
			TextView txtAttending = (TextView) alertDialog
					.findViewById(R.id.event_textView_txtAttending);
			txtAttending.setText(paseContext[0]);
			txtAttending.setTextColor(Color.GRAY);
			txtAttending.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// insertJoin(insertJoin[0]);
					alertDialog.cancel();
				}
			});
			// Might Attending按钮
			TextView txtMight = (TextView) alertDialog
					.findViewById(R.id.event_textView_txtMight);
			txtMight.setText(paseContext[1]);
			txtMight.setTextColor(Color.GRAY);
			txtMight.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// insertJoin(insertJoin[1]);
					alertDialog.cancel();
				}
			});
			// No Attending按钮
			TextView txtNo = (TextView) alertDialog
					.findViewById(R.id.event_textView_txtNo);
			txtNo.setText(paseContext[2]);
			txtNo.setTextColor(Color.GRAY);
			txtNo.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					alertDialog.cancel();
				}
			});

			// AlertDialog.Builder builder = new AlertDialog.Builder(
			// EventDetailActivity.this);
			// builder.setItems(paseContext, null);
			// AlertDialog alert = builder.create();
			// alert.show();

			// AlertDialog.Builder builder = new AlertDialog.Builder(
			// EventDetailActivity.this);
			// // builder.setItems(paseContext, null);
			// LayoutInflater factory = LayoutInflater.from(this);
			//
			// View view = factory.inflate(R.layout.eventdialog, null);
			// view.setBackgroundColor(Color.GRAY);
			// builder.setView(view);
			// TextView tv1 = (TextView)
			// view.findViewById(R.id.eventdialog_textView1);
			// TextView tv2 = (TextView)view.
			// findViewById(R.id.eventdialog_textView2);
			// TextView tv3 = (TextView)
			// view.findViewById(R.id.eventdialog_textView3);
			//
			// tv1.setText("Attending " + jionCount[0]);
			// tv2.setText("Might Attend " + jionCount[1]);
			// tv3.setText("Not Attend " + jionCount[2]);
			//
			// // builder.setInverseBackgroundForced(true);
			// // builder.setCancelable(true);
			// builder.show();

			// AlertDialog alertDialog = new
			// AlertDialog.Builder(this).setMessage(
			// "透明对话框").setPositiveButton("确定", null).create();
			// Window window = alertDialog.getWindow();
			// WindowManager.LayoutParams lp = window.getAttributes();
			// // 设置透明度为0.3
			// // lp.c
			// lp.alpha = 0.3f;
			// window.setAttributes(lp);
			// alertDialog.show();

		}
	}

	private void shareTo(int to) {
		ShareInfo shareInfo = new ShareText();
		shareInfo.constructContext(((EventModel) data.get(to)).getTitle()
				+ " on " + ((EventModel) data.get(to)).getBegintime() + " "
				+ ((EventModel) data.get(to)).getLocation()
				+ " , please email " + ((EventModel) data.get(to)).getEmail()
				+ " for details.");
		ShareManage.getInstance().share2weibo(EventDetailActivity.this,
				shareInfo);
	}

	class InsetEventDetailAsync extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... params) {

			String result = "";
			try {
				URL url = new URL(params[0]);
				StringBuilder builder = new StringBuilder();
				URLConnection tc = url.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						tc.getInputStream()));

				String line;
				while ((line = in.readLine()) != null) {
					builder.append(line);
				}
				JSONObject jsonObject = new JSONObject(builder.toString());

				in.close();
				return result = jsonObject.getString("status");
			} catch (MalformedURLException e) {
				// e.printStackTrace();
			} catch (IOException e) {
				// e.printStackTrace();
			} catch (JSONException e) {
				// e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("1")) {
				LayoutInflater factory = getLayoutInflater();
				View view = factory.inflate(R.layout.mytoast, null);
				Toast myToast = new Toast(EventDetailActivity.this);
				myToast.setView(view);
				LinearLayout myToastBg = (LinearLayout) view
						.findViewById(R.id.myToastBg);
				myToastBg.setBackgroundColor(Color.parseColor(uiConfig
						.getTopbar_background_color()));
				TextView txtToast = (TextView) view
						.findViewById(R.id.myToast_TextView_txtToast);
				txtToast.setText(R.string.join_success);
				txtToast.setTextColor(Color.parseColor(uiConfig
						.getTopbar_text_color()));
				myToast.show();
				// Toast.makeText(
				// EventDetailActivity.this,
				// EventDetailActivity.this.getResources().getString(
				// R.string.join_success), Toast.LENGTH_LONG)
				// .show();

				UpdateJoinAsync async = new UpdateJoinAsync();
				async.execute(eventUrl);

			}
			super.onPostExecute(result);
		}

	}

	private class UpdateJoinAsync extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			try {
				URL url = new URL(params[0]);
				StringBuilder builder = new StringBuilder();
				URLConnection tc = url.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						tc.getInputStream()));

				String line;
				while ((line = in.readLine()) != null) {
					builder.append(line);
				}
				JSONObject jsonObject = new JSONObject(builder.toString());
				JSONArray jsonArray = jsonObject.getJSONArray("datas");
				// for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(0);
				jionCount[0] = jsonObject2.getString("egaree").trim();
				jionCount[1] = jsonObject2.getString("emaybe").trim();
				jionCount[2] = jsonObject2.getString("eunagree").trim();

				joinCount();
				in.close();

			} catch (MalformedURLException e) {
				// e.printStackTrace();
			} catch (IOException e) {
				// e.printStackTrace();
			} catch (JSONException e) {
				// e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

		}
	}

	private String getHtml(String path, String title, String beginTime,
			String endTime, String location, String Holster, String Phone,
			String Email, String context) {
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

			if (sb.indexOf("<!--title-->") > 0)
				sb.insert(sb.indexOf("<!--title-->") + "<!--title-->".length(),
						title);
			if (sb.indexOf("<!--begintime-->") > 0)
				sb.insert(
						sb.indexOf("<!--begintime-->")
								+ "<!--begintime-->".length(), beginTime);
			if (sb.indexOf("<!--endtime-->") > 0)
				sb.insert(
						sb.indexOf("<!--endtime-->")
								+ "<!--endtime-->".length(), endTime);
			if (sb.indexOf("<!--location-->") > 0)
				sb.insert(
						sb.indexOf("<!--location-->")
								+ "<!--location-->".length(), location);
			if (sb.indexOf("<!--Hoster-->") > 0)
				sb.insert(
						sb.indexOf("<!--Hoster-->") + "<!--Hoster-->".length(),
						Holster);
			if (sb.indexOf("<!--Phone-->") > 0)
				sb.insert(sb.indexOf("<!--Phone-->") + "<!--Phone-->".length(),
						Phone);
			if (sb.indexOf("<!--Email-->") > 0)
				sb.insert(sb.indexOf("<!--Email-->") + "<!--Email-->".length(),
						Email);

			if (sb.indexOf("<!--content-->") > 0)
				sb.insert(
						sb.indexOf("<!--content-->")
								+ "<!--content-->".length(), context);

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
		if (url.equals(eventUrl)) {
			try {
				JSONArray jsonArray = json.getJSONArray("datas");

				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(0);

				jionCount[0] = jsonObject2.getString("egaree").trim();
				jionCount[1] = jsonObject2.getString("emaybe").trim();
				jionCount[2] = jsonObject2.getString("eunagree").trim();

				htmlDetail = getHtml("event.html",
						jsonObject2.getString("event_title"),
						jsonObject2.getString("event_begintime"),
						jsonObject2.getString("event_endtime"),
						jsonObject2.getString("elocation"),
						jsonObject2.getString("eholster"),
						jsonObject2.getString("ephone"),
						jsonObject2.getString("email"),
						jsonObject2.getString("event_content"));

				joinCount();

				mWebViews[index].loadDataWithBaseURL(null, htmlDetail,
						"text/html", "utf-8", null);
			} catch (JSONException e) {
				// e.printStackTrace();
			}
			mWebViews[index].invalidate();
		}

	}

	@Override
	public void callJoinFailure(String url, String failMessage) {
		super.callJoinFailure(url, failMessage);
	}

	@Override
	public void callImageSuccess(String url, Drawable drawable) {
		super.callImageSuccess(url, drawable);
		if (url.equals(imageUrl)) {
			if (drawable != null) {
				imageView.setImageDrawable(drawable);
				imageView.setBackgroundColor(this.getResources().getColor(
						R.color.black));
			}
		}
	}

	@Override
	public void callImageFailure(String url) {

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
			data_id = ((EventModel) data.get(index)).getId();
			eventUrl = Confi.getInstance().getEventDetailApiUrl(tid, data_id,
					type);

			imageUrl = ((EventModel) data.get(index)).getImage();
			asyncImage = new AsyncImage();
			asyncImage.addImageLoadCallback(this);
			asyncImage.execute(imageUrl);

			AsyncRefreshJson asyncRefreshJson = new AsyncRefreshJson(this);
			asyncRefreshJson.addInterFace(this);
			asyncRefreshJson.execute(eventUrl);
		}
			break;
		case DOWNLOAD: {
			Intent intent = new Intent(EventDetailActivity.this,
					DownloadActivity.class);
			intent.putExtra("tabName", "Download");
			EventDetailActivity.this.startActivity(intent);
		}
			break;
		case SETTING: {
			Intent intent = new Intent(EventDetailActivity.this,
					SettingActivity.class);
			intent.putExtra("tabName", "Setting");

			EventDetailActivity.this.startActivity(intent);
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
			Toast myToast = new Toast(EventDetailActivity.this);
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
			// Toast.makeText(EventDetailActivity.this, R.string.top,
			// Toast.LENGTH_SHORT).show();
			return;
		} else if (index < 0) {
			index = 0;
			LayoutInflater factory = getLayoutInflater();
			View view = factory.inflate(R.layout.mytoast, null);
			Toast myToast = new Toast(EventDetailActivity.this);
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
			// Toast.makeText(EventDetailActivity.this, R.string.end,
			// Toast.LENGTH_SHORT).show();
			return;
		}
		showPage();

	}

}
