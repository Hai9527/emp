package com.fmlditital.emp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.fmlditital.emp.app.EMPApp;
import com.fmlditital.emp.async.AsyncImage;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.async.AsyncRefreshJson;
import com.fmlditital.emp.base.DefaultActivity;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.dowmload.DownloadActivity;
import com.fmlditital.emp.dowmload.DownloadManager;
import com.fmlditital.emp.interf.ImageLoadCallback;
import com.fmlditital.emp.manage.MusicManage;
import com.fmlditital.emp.manage.NewsManage;
import com.fmlditital.emp.model.AriticleModel;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.model.MusicModel;
import com.fmlditital.emp.model.NavigationModel;
import com.fmlditital.emp.music.MusicPlayer;
import com.fmlditital.emp.notification.NotificationActicity;
import com.fmlditital.emp.preferences.SettingActivity;
import com.fmlditital.emp.receiver.NetworkReceiver;
import com.fmlditital.emp.view.NavigationView1;
import com.fmlditital.emp.view.NavigationView2;

public class HomeActivity extends DefaultActivity implements ImageLoadCallback {

	// //check for network
	private NetworkReceiver mNetworkStateReceiver = null;
	// public static final String CONNECTIVITY_CHANGE_ACTION =
	// "android.net.conn.CONNECTIVITY_CHANGE";

	private EMPApp app = EMPApp.getSingleton();
	private List<NavigationModel> tabList = app.getTabList();

	private static final int REFRESH = Menu.FIRST;
	private static final int EXIT = Menu.FIRST + 1;
	private static final int MORE = Menu.FIRST + 2;

	private AsyncJson asyncJson = null;

	private String pageUrl = null;
	private String tid = null;
	private NavigationModel homeModelData = null;

	private String bodyLinearLayoutUrl = null;

	private WebView mWebView = null;
	private String htmlDetail = null;

	private String page_template[] = { "0", "1", "2", "3", "4" };

	private AsyncImage asyncImage = null;

	private String navigationType = Confi.getInstance().getAPP_Type();

	private NavigationView1 navigationView1 = null;
	private NavigationView2 navigationView2 = null;

	private LayoutParams param = null;

	// private List<BaseModel> dataNotificationModel = null;

	private AsyncJson musicAsyncJson = null, newsAsyncJson = null,
			notificationJson = null;

	// body image
	private Drawable bodyDrawable = null;

	// UI Config
	private UIConfig uiConfig = null;

	// music data
	private List<BaseModel> musicData = app.getBottomDataDataMusic();
	private String musicTid = null;
	private String musicUrl = null;

	// news data
	private List<BaseModel> bottomDataAriticle = app.getBottomDataAriticle();
	private String newsTid = null;
	private List<NavigationModel> newsList = new ArrayList<NavigationModel>();
	private String[] newsLinks = null;
	private NewsManage newsManage = null;

	// notification data
	// private String notificationURL = null;
	// private String notificationTid = null;
	private List<NavigationModel> notificationList = new ArrayList<NavigationModel>();
	private String[] notificationLinks = null;
	private String[] notificationTid = null;
	// private List<BaseModel> notificationData = app
	// .getBottomDataNotificationModel();
	private int[] newNotificationCount = null;
	private boolean showTheFirstNotifi = false;

	private SharedPreferences notificationSP = null;
	private Editor editor = null;
	public static final String NOTIFICATION = NotificationActicity.NOTIFICATION;

	private boolean hasAddBottomShortCut = false;

	// private Window window = null;

	private TextView dialogTxtTitle;

	private LinearLayout dialogBground;

	private Button dialogBtnOk, dialogBtnCanel;

	private AlertDialog alertDialog;

	private LinearLayout.LayoutParams dialogLayout;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		homeModelData = EMPApp.getSingleton().getHomeModel();
		tid = homeModelData.getTab_id();
		pageUrl = Confi.getInstance().getPageApiUrl(tid);

		uiConfig = Confi.getInstance().getuIConfig();

		mWebView = new WebView(this);
		mWebView.setBackgroundColor(0);
		mWebView.setHorizontalScrollbarOverlay(false);
		mWebView.setVerticalScrollbarOverlay(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setScrollBarStyle(0);

		bodyLayout.removeAllViews();

		param = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT, 1);

		asyncJson = new AsyncJson(this);
		asyncJson.addInterFace(this);
		asyncJson.execute(pageUrl);
		System.out.println("tabList.size::::" + tabList.size() + "===="
				+ navigationType);
		if (tabList.size() > 0) {
			if (navigationType.equals(Global.APP_STYLE[0])) { // Music Template

				navigationView1 = new NavigationView1(this);

				mWebView.setLayoutParams(param);
				bodyLayout.addView(mWebView);
				bodyLayout.addView(navigationView1);

			} else if (navigationType.equals(Global.APP_STYLE[1])) { // Event
																		// Template
				navigationView2 = new NavigationView2(this);

				mWebView.setLayoutParams(param);

				bodyLayout.addView(mWebView);
				bodyLayout.addView(navigationView2);
			} else if (navigationType.equals(Global.APP_STYLE[2])) {// Movie
																	// Template

				navigationView2 = new NavigationView2(this);
				mWebView.setLayoutParams(param);

				bodyLayout.addView(navigationView2);
				bodyLayout.addView(mWebView);
			}

			// dataNotificationModel = app.getDataNotificationModel();

			initiBottomView();
		} else {
			mWebView.setLayoutParams(param);
			bodyLayout.addView(mWebView);
		}

		// get Storage data
		DownloadManager.getInstance().getDataFormFile();
		Confi.getInstance().getUserInfoFormFile(this);

		// register for checking network BroadcastReceiver
		registerDateTransReceiver();

	}

	@Override
	protected void onResume() {
		super.onResume();

		if (bodyDrawable != null)
			bodyLayout.setBackgroundDrawable(bodyDrawable);

	}

	// register for checking network BroadcastReceiver
	private void registerDateTransReceiver() {
		mNetworkStateReceiver = new NetworkReceiver();

		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mNetworkStateReceiver, filter);
	}

	private String getHtml(String path, String background, String content) {
		String result = null;
		AssetManager am = null;
		ByteArrayOutputStream bo = null;
		InputStream is = null;
		int i;
		try {
			StringBuffer sb = new StringBuffer();
			am = this.getAssets();
			is = am.open(path);
			bo = new ByteArrayOutputStream();
			while ((i = is.read()) != -1)
				bo.write(i);
			bo.close();
			is.close();

			sb.append(bo.toString());

			if (sb.indexOf("<!--color-->") > 0) {
				sb.replace(sb.indexOf("<!--color-->"),
						sb.indexOf("<!--color-->") + "<!--color-->".length(),
						Confi.getInstance().getuIConfig().getApp_text_color());
			}

			sb.insert(sb.indexOf("<!--content-->") + "<!--content-->".length(),
					content);

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

				// for full html
				if ((jsonObject2.getString("page_template")).trim().equals(
						page_template[0]))
					;

				// for left html
				if ((jsonObject2.getString("page_template")).trim().equals(
						page_template[1])) {
					param.width = DefaultActivity.getScreenWidth() / 2;
					param.rightMargin = DefaultActivity.getScreenWidth() / 2;
				}
				// for top html
				if ((jsonObject2.getString("page_template")).trim().equals(
						page_template[2]))
					param.bottomMargin = DefaultActivity.getScreenHeight() / 3;
				// for right html
				if ((jsonObject2.getString("page_template")).trim().equals(
						page_template[3]))
					param.leftMargin = DefaultActivity.getScreenWidth() / 2;
				// for bottom html
				if ((jsonObject2.getString("page_template")).trim().equals(
						page_template[4]))
					param.topMargin = DefaultActivity.getScreenHeight() / 3;
				mWebView.setLayoutParams(param);

				bodyLinearLayoutUrl = jsonObject2.getString("page_background");

				htmlDetail = getHtml("page.html",
						jsonObject2.getString("page_background"),
						jsonObject2.getString("page_editor"));

				if (!(jsonObject2.getString("page_template")).trim().equals(
						page_template[0])) {

					bodyLayout.setBackgroundColor(Color.parseColor(uiConfig
							.getApp_background_color()));

					asyncImage = new AsyncImage();
					asyncImage.addImageLoadCallback(this);
					asyncImage.execute(bodyLinearLayoutUrl);
				}

			} catch (JSONException e) {
			}

			mWebView.loadDataWithBaseURL(null, htmlDetail, "text/html",
					"utf-8", null);
		}

		if (url.equals(musicUrl)) {
			musicData.clear();
			try {
				if (json.getInt("statusid") == 0) {
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
							model.setIsDownload(jsonObject2
									.getString("download"));
							model.setDescription(Html.fromHtml(
									jsonObject2.getString("music_caption"))
									.toString());
							musicData.add(model);
						}
					}
					if (musicData.size() > 0)
						hasMusic = true;
				} else {
					hasMusic = false;
				}

			} catch (JSONException e) {
				callJoinFailure(musicUrl, e.getMessage().toString());
			}
		}

		if (newsLinks != null && newsLinks.length > 0) {
			for (int j = 0; j < newsLinks.length; j++) {
				if (url.equals(newsLinks[j])) {
					if (hasNews == false) {
						bottomDataAriticle.clear();
						try {
							JSONArray jsonArray = json.getJSONArray("datas");
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject jsonObject2 = (JSONObject) jsonArray
										.opt(i);
								if (jsonObject2.getString("article_status")
										.equals("1")) {
									AriticleModel model = new AriticleModel();
									model.setId(jsonObject2
											.getString("article_id"));
									model.setTitle(jsonObject2
											.getString("article_title"));
									model.setTime(jsonObject2
											.getString("artpublish_time"));
									model.setSummary(jsonObject2
											.getString("article_summary"));
									model.setContent(jsonObject2
											.getString("article_content"));
									model.setThumbUrl(jsonObject2
											.getString("articon"));

									model.setComment_count(jsonObject2
											.getString("comment_counts"));
									model.setPlayers_count(jsonObject2
											.getString("players_counts"));
									model.setLikes_count(jsonObject2
											.getString("likes_counts"));

									bottomDataAriticle.add(model);
								}
							}

							if (bottomDataAriticle.size() > 0) {
								newsImageView.setVisibility(View.VISIBLE);
								newsLinearLayout.setVisibility(View.VISIBLE);
								hasNews = true;
							} else {
								// newsImageView.setVisibility(View.GONE);
								// newsLinearLayout.setVisibility(View.GONE);
								hasNews = false;
							}

							mNewsFlipper.startFlipping();
							mNewsFlipper.setInAnimation(AnimationUtils
									.loadAnimation(this, R.anim.push_up_in));
							mNewsFlipper.setOutAnimation(AnimationUtils
									.loadAnimation(this, R.anim.push_up_out));

							newsManage = NewsManage.getNewsManage(this);
							newsManage.updateNewsTextView(newsTitleView1,
									newsTitleView2, newsTitleView3);

						} catch (JSONException e) {
							callJoinFailure(newsLinks[j], e.getMessage()
									.toString());
						}
					}
				}
			}
		}

		if (notificationLinks != null && notificationLinks.length > 0) {
			for (int j = 0; j < notificationLinks.length; j++) {
				if (url.equals(notificationLinks[j])) {
					// notificationData.clear();
					try {
						if (json.getInt("statusid") == 0) {

							JSONArray jsonArray = json.getJSONArray("datas");

							notificationSP = this.getSharedPreferences(
									NOTIFICATION, Activity.MODE_PRIVATE);
							editor = notificationSP.edit();

							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject jsonObject2 = (JSONObject) jsonArray
										.opt(i);

								// unread notification
								Log.v("",
										">>>>>>>>>>>>>>>>>>>>>>>>>>notificationSP 	"
												+ notificationSP.getString(
														notificationTid
																+ jsonObject2
																		.getString("notification_id"),
														""));
								if (notificationSP
										.getString(
												notificationTid
														+ jsonObject2
																.getString("notification_id"),
												"").equals("")) {
									newNotificationCount[j]++;
									editor.putString(
											notificationTid
													+ jsonObject2
															.getString("notification_id"),
											notificationTid
													+ jsonObject2
															.getString("notification_id"));
									editor.commit();
								}

								// NotificationModel model = new
								// NotificationModel();
								// model.setId(jsonObject2
								// .getString("notification_id"));
								// model.setTitle(jsonObject2
								// .getString("notification_title"));
								// model.setTime(jsonObject2
								// .getString("notification_time"));
								// model.setContent(jsonObject2
								// .getString("notification_content"));
								// notificationData.add(model);

							}

							if (showTheFirstNotifi == false) {
								if (newNotificationCount[j] > 0) {
									showNotificationDialog(j);
									showTheFirstNotifi = true;
									break;
								}

							}
						}

					} catch (JSONException e) {
						callJoinFailure(notificationLinks[j], e.getMessage()
								.toString());
					}

				}
			}
		}
	}

	private void initiBottomView() {
		// boolean
		for (NavigationModel model : tabList) {
			if (model.getFunction().equals("music")) {
				hasMusic = true;
				musicTid = model.getTab_id();
				musicUrl = Confi.getInstance().getMusicApiUrl(musicTid);

				musicAsyncJson = new AsyncJson(this);
				musicAsyncJson.addInterFace(this);
				musicAsyncJson.execute(musicUrl);
			}

			if (model.getFunction().equals("article")) {
				newsList.add(model);

			}

			// judge if there are any notification
			if (model.getFunction().equals("notification")) {
				notificationList.add(model);
			}

		}

		// open new thread to load the notification data
		if (newsList.size() > 0) {
			newsLinks = new String[newsList.size()];
			for (int i = 0; i < newsList.size(); i++) {
				NavigationModel model = newsList.get(i);
				newsTid = model.getTab_id();
				newsLinks[i] = Confi.getInstance().getArticleApiUrl(newsTid,
						"1");

				newsAsyncJson = new AsyncJson(this);
				newsAsyncJson.addInterFace(this);
				newsAsyncJson.execute(newsLinks[i]);
			}
		}

		// if (notificationList.size() > 0) {
		// notificationLinks = new String[notificationList.size()];
		// notificationTid = new String[notificationList.size()];
		// newNotificationCount = new int[notificationList.size()];
		// for (int i = 0; i < notificationList.size(); i++) {
		// NavigationModel notiModel = notificationList.get(i);
		// notificationTid[i] = notiModel.getTab_id();
		// notificationLinks[i] = Confi.getInstance()
		// .getNotificationApiUrl(notificationTid[i]);
		//
		// Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>notificationTid[i]	"
		// + notificationTid[i]);
		// Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>	notificationLinks[i]	"
		// + notificationLinks[i]);
		//
		// notificationJson = new AsyncJson(this);
		// notificationJson.addInterFace(this);
		// notificationJson.execute(notificationLinks[i]);
		// }
		// }

		if (hasMusic == true || hasNews == true) {
			if (hasAddBottomShortCut == true) {
				bottomBarLayout.setVisibility(View.VISIBLE);
			}
		} else {
			bottomBarLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * 通知提示框显示
	 * 
	 * @param index
	 *            新通知的数量
	 */
	private void showNotificationDialog(final int index) {
		// if (newNotificationCount > 0) {
		alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
		alertDialog.show();
		alertDialog.setContentView(R.layout.dialog);

		dialogLayout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		dialogLayout.leftMargin = 30;
		dialogLayout.rightMargin = 30;
		dialogBground = (LinearLayout) alertDialog
				.findViewById(R.id.dialog_linearlayout_bg);
		dialogBground.setBackgroundColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));
		dialogBground.getBackground().setAlpha(130);
		// dialogBground.setLayoutParams(dialogLayout);

		// dialogBground.setLayoutParams(dialogLayout);
		TextView txtTitle = (TextView) alertDialog
				.findViewById(R.id.dialog_textview_title);
		txtTitle.setTextColor(Color.parseColor(uiConfig.getTopbar_text_color()));
		dialogTxtTitle = (TextView) alertDialog
				.findViewById(R.id.dialog_textview_title);
		dialogTxtTitle.setText("You have got " + newNotificationCount[index]
				+ " new notification.Would you like to read it now?");
		dialogBtnOk = (Button) alertDialog.findViewById(R.id.dialog_button_ok);
		dialogBtnOk.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));
		dialogBtnOk.setBackgroundColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));
		dialogBtnOk.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				alertDialog.cancel();
				Intent intent = new Intent(HomeActivity.this,
						NotificationActicity.class);
				intent.putExtra("tabName", "Notification");
				// intent.putExtra("tid", notificationTid);
				intent.putExtra("tid", notificationTid[index]);

				HomeActivity.this.startActivity(intent);
				alertDialog.dismiss();
			}
		});
		dialogBtnCanel = (Button) alertDialog
				.findViewById(R.id.dialog_button_cancel);
		dialogBtnCanel.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));
		dialogBtnCanel.setBackgroundColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));
		dialogBtnCanel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
			}

		});
		// AlertDialog.Builder builder = new Builder(HomeActivity.this);
		// builder.setTitle(R.string.nofitication);
		// builder.setMessage("You have got " + newNotificationCount
		// + " new notification.Would you like to read it now?");

		// builder.setPositiveButton(R.string.yes, new OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// dialog.dismiss();
		//
		// Intent intent = new Intent(HomeActivity.this,
		// NotificationActicity.class);
		// intent.putExtra("tabName", "Notification");
		// intent.putExtra("tid", notificationTid);
		//
		// HomeActivity.this.startActivity(intent);
		// }
		// });
		// builder.setNegativeButton(R.string.cancel, new OnClickListener()
		// {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// dialog.dismiss();
		// }
		//
		// });
		// builder.create().show();
		// }
		// }
	}

	public void callImageSuccess(String url, Drawable drawable) {
		if (drawable != null) {
			bodyDrawable = drawable;
			bodyLayout.setBackgroundDrawable(bodyDrawable);
		}

	}

	public void callImageFailure(String url) {
	}

	protected void onDestroy() {
		super.onDestroy();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, REFRESH, 0, getResources().getString(R.string.refresh))
				.setIcon(android.R.drawable.ic_popup_sync);

		menu.add(0, EXIT, 0, getResources().getString(R.string.exit)).setIcon(
				android.R.drawable.ic_menu_preferences);
		menu.add(0, MORE, 0, getResources().getString(R.string.more)).setIcon(
				R.drawable.more);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case REFRESH: {
			AsyncRefreshJson asyncRefreshJson = new AsyncRefreshJson(this);
			asyncRefreshJson.addInterFace(this);
			asyncRefreshJson.execute(pageUrl);
		}
			break;
		case EXIT: {
			exit();
		}
			break;
		case MORE: {
			alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
			alertDialog.show();

			alertDialog.setContentView(R.layout.homemore);
			LinearLayout moreBg = (LinearLayout) alertDialog
					.findViewById(R.id.homeMoreBg);
			moreBg.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_background_color()));
			moreBg.getBackground().setAlpha(95);
			TextView txtDownTextView = (TextView) alertDialog
					.findViewById(R.id.home_textView_down);
			txtDownTextView.setTextColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			txtDownTextView.setText("Download");
			txtDownTextView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(HomeActivity.this,
							DownloadActivity.class);
					intent.putExtra("tabName", "Download");
					HomeActivity.this.startActivity(intent);
					alertDialog.cancel();
				}
			});
			TextView txtLine = (TextView) alertDialog
					.findViewById(R.id.home_textView_line);
			txtLine.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			TextView txtSettingTextView = (TextView) alertDialog
					.findViewById(R.id.home_textView_setting);
			txtSettingTextView.setTextColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			txtSettingTextView.setText("Setting");
			txtSettingTextView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(HomeActivity.this,
							SettingActivity.class);
					intent.putExtra("tabName", "Setting");
					HomeActivity.this.startActivity(intent);
					alertDialog.cancel();
				}
			});

		}
			break;
		}
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			exit();
		}
		return false;
	}

	private void exit() {
		// final MyDialog alertDialog = new MyDialog(HomeActivity.this,
		// R.style.Theme_dialog);
		// alertDialog.setContentView(R.layout.dialog);
		// alertDialog.show();

		alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
		alertDialog.show();
		alertDialog.setContentView(R.layout.dialog);

		// dialogLayout = new LinearLayout.LayoutParams(
		// DefaultActivity.getScreenWidth() * 7 / 9,
		// DefaultActivity.getScreenHeight() * 3 / 12);
		dialogBground = (LinearLayout) alertDialog
				.findViewById(R.id.dialog_linearlayout_bg);
		dialogBground.setBackgroundColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));
		dialogBground.getBackground().setAlpha(130);
		// dialogBground.setLayoutParams(dialogLayout);
		dialogTxtTitle = (TextView) alertDialog
				.findViewById(R.id.dialog_textview_title);
		dialogTxtTitle.setText("Are you sure to exit");
		dialogTxtTitle.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));
		dialogBtnOk = (Button) alertDialog.findViewById(R.id.dialog_button_ok);
		dialogBtnOk.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));
		dialogBtnOk.setBackgroundColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));
		dialogBtnOk.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				try {
					DefaultActivity.theFirstOpenMusic = true;
					DefaultActivity.initiBottomShortCutData = true;
					DefaultActivity.isOpenPlay = false;
					DefaultActivity.playState = false;
					// DefaultActivity.theFirstMusicIcon = true;
					DefaultActivity.hasNews = false;
					DefaultActivity.hasMusic = false;

					if (app.getSawMap() != null || app.getSawMap().size() > 0) {
						app.getSawMap().clear();
					}
					if (app.getLikeMap() != null || app.getLikeMap().size() > 0) {
						app.getLikeMap().clear();
					}
					if (MusicPlayer.data != null && MusicPlayer.data.size() > 0)
						MusicManage.getMusicManage(MusicPlayer.data).stop();

					// stop all download thread and save the download data
					DownloadManager.getInstance().stopAll();

					// save the user info
					Confi.getInstance().saveUserInfoFormFile(HomeActivity.this);

				} catch (Exception e) {
					Log.v("",
							">>>>>>>>>>>>>>>>>>>>>>>>>Exception  "
									+ e.toString());
				} finally {

					unregisterReceiver(mNetworkStateReceiver); // å�–æ¶ˆç›‘å�¬

					System.gc();

					finish();
					// System.exit(0);

					// DefaultActivit

					// finish();
					// am= (ActivityManager) HomeActivity.this
					// .getSystemService(Context.ACTIVITY_SERVICE);
					// am.killBackgroundProcesses(HomeActivity.this.getPackageName());
					int nPid = android.os.Process.myPid();
					android.os.Process.killProcess(nPid);
					// System.exit(0);

				}
			}
		});
		dialogBtnCanel = (Button) alertDialog
				.findViewById(R.id.dialog_button_cancel);
		dialogBtnCanel.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));
		dialogBtnCanel.setBackgroundColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));
		dialogBtnCanel.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				alertDialog.cancel();
			}

		});
	}
}
