package com.fmlditital.emp.video;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
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
import com.fmlditital.emp.dowmload.DownloadManager;
import com.fmlditital.emp.interf.AddCommentCallback;
import com.fmlditital.emp.interf.ImageLoadCallback;
import com.fmlditital.emp.model.DownloadModel;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.model.VideoModel;
import com.fmlditital.emp.preferences.SettingActivity;
import com.fmlditital.emp.share.ShareManage;
import com.fmlditital.emp.share.info.ShareInfo;
import com.fmlditital.emp.share.info.ShareText;
import com.fmlditital.emp.tool.NetWorkState;
import com.fmlditital.emp.tool.Tools;
import com.fmlditital.emp.view.CommentInteractView;
import com.fmlditital.emp.view.CommentInteractView.CommentInteractStyle;
import com.fmlditital.emp.view.CommentListView;
import com.fmlditital.emp.view.TabView;

public class VideoDetailActivity extends DetailBaseActivity implements
		ImageLoadCallback, OnClickListener, OnPageChangeListener,
		AddCommentCallback {

	private CommentInteractView commentInteractView = null;

	private CommentListView commentListView = null;

	private AsyncJson async = null, sawAsyncJson = null;

	private String imageUrl = null, sawInsertUrl = null;

	// private LinearLayout detailLinearLayout = null;

	// private CharSequence[] shareDonloadContext = { "Share to Sina WeiBo",
	// "Download" };
	private CharSequence[] shareDonloadContext = { "分享到微博", "下载" };

	// private CharSequence[] shareContext = { "Share to Sina WeiBo" };
	private CharSequence[] shareContext = { "分享到微博" };
	private String videoDetailUrl = null;

	private AsyncImage asyncImage = null;
	private ImageView imageView = null, goImage = null;
	private FrameLayout frl = null;

	private TabView touchView = null;
	private String detail = Global.DETAIL, comment = Global.COMMENT;

	private ViewPager mPager;
	private List<View> listViews;
	private WebView[] mWebViews = null;

	private String htmlDetail = null, vTitle = null, vCaption = null;

	private static final int REFRESH = Menu.FIRST;
	private static final int DOWNLOAD = Menu.FIRST + 1;
	private static final int SETTING = Menu.FIRST + 2;

	private Map<String, String> sawMap = app.getSawMap();

	// private List<BaseModel> popularData = null;
	private String type = null;

	private UIConfig uiConfig = Confi.getInstance().getuIConfig();

	public int vindex = index;
	LayoutParams parm = new LayoutParams(LayoutParams.WRAP_CONTENT,
			LayoutParams.WRAP_CONTENT);

	RelativeLayout content;
	RelativeLayout.LayoutParams rp;
	RelativeLayout.LayoutParams rp1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		rp = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,
				Tools.dip2px(this, 50));
		rp1 = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);

		parm.setMargins(0, 20, 0, 0);
		type = this.getIntent().getExtras().getString("type");
		if (type.equals("recent")) {
			data = app.getDataVideo();
		} else if (type.equals("popular")) {
			data = app.getDataVideopopular();
		}

		// data = app.getDataVideo();
		// popularData = app.getDataVideopopular();
		// ait=((VideoModel) data.get(index)).get
		data_id = ((VideoModel) data.get(index)).getId();
		data_name = ((VideoModel) data.get(index)).getTitle();

		index();
		touchTitleTextView.setVisibility(View.VISIBLE);
		// touchTitleTextView.setText((index + 1) + "/" + data.size());

		videoDetailUrl = Confi.getInstance().getVideoDetailApiUrl(data_id);
		async = new AsyncJson(this);
		async.addInterFace(this);
		async.execute(videoDetailUrl);
		System.out.println("commentInteractView =====oncreate====== "
				+ commentInteractView.getId());
		touchShareImageView.setOnClickListener(this);
	}

	private void add() {
		content.removeAllViews();

		rp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		content.addView(commentInteractView, rp);
		commentInteractView.setId(1);

		rp1.addRule(RelativeLayout.ABOVE, 1);
		content.addView(mPager, rp1);
	}

	private void index() {
		touchBarLayout.setVisibility(View.VISIBLE);

		// imageView = new ImageView(this);
		// imageView.requestFocus();
		// LayoutParams param = new LinearLayout.LayoutParams(
		// LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		// //param.width=DefaultActivity.getScreenWidth();
		// param.height = Tools.dip2px(this, 182);
		// //param.gravity = Gravity.CENTER;
		// //imageView.setAdjustViewBounds(true);
		// //imageView.setScaleType(ScaleType.FIT_CENTER);

		frl = new FrameLayout(this);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;

		goImage = new ImageView(this);
		goImage.setImageDrawable(this.getResources().getDrawable(
				R.drawable.go_button));

		imageView = new ImageView(this);
		imageView.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, Tools.dip2px(this, 182)));
		imageView.setScaleType(ScaleType.CENTER_CROP);
		// LayoutParams param = new LinearLayout.LayoutParams(
		// LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		// param.height = Tools.dip2px(this, 182);
		// param.width=DefaultActivity.getScreenWidth();
		// param.gravity = Gravity.CENTER;
		frl.addView(imageView);
		frl.addView(goImage, params);
		bodyLayout.addView(frl);
		imageView.setOnClickListener(this);
		touchView = new TabView(this);
		touchView.addTitle(detail);
		touchView.addTitle(comment);
		touchView.display(detail);
		touchView.addCommentCallback(this);
		// bodyLayout.addView(touchView);
		// touchBarLayout.setVisibility(View.GONE);
		// detailLinearLayout = new LinearLayout(this);
		// detailLinearLayout.setOrientation(LinearLayout.VERTICAL);
		// detailLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
		// LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		commentInteractView = new CommentInteractView(VideoDetailActivity.this,
				tid, data_id, CommentInteractStyle.saw);
		commentInteractView.addCommentCallback(this);
		commentInteractView.setId(1);
		commentInteractView.setGravity(Gravity.BOTTOM);

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
		// mPager.setLayoutParams(new LinearLayout.LayoutParams(
		// // LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		// LayoutParams.FILL_PARENT, DefaultActivity.getScreenHeight() / 3,2f));
		mPager.setAdapter(new ViewPagerAdapter(listViews));
		mPager.setCurrentItem(index);
		mPager.setOnPageChangeListener(this);

		// LinearLayout temp=new LinearLayout(this);
		// temp.addView(mPager);

		content = new RelativeLayout(this);
		content.setLayoutParams(new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		rp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		content.addView(commentInteractView, rp);

		rp1.addRule(RelativeLayout.ABOVE, 1);
		content.addView(mPager, rp1);

		// detailLinearLayout.addView(mPager);
		// detailLinearLayout.addView(commentInteractView);
		// commentInteractView.set
		// bodyLayout.addView(detailLinearLayout);
		// commentInteractView.setBackgroundColor(Color.YELLOW);
		// mPager.setBackgroundColor(Color.GREEN);
		// content.setBackgroundColor(Color.BLUE);
		bodyLayout.addView(content);
		// Log.d("",
		// "detailLinearLayout========= "+detailLinearLayout.getHeight());
	}

	protected void onResume() {
		super.onResume();
		// bottomBarLayout.setVisibility(View.GONE);
		commentInteractView.invalidate();
	}

	public void showDetailOrCommentList(String title) {
		Log.d("", "showDetailOrCommentList =============== " + title);
		if (title.equals(detail)) {
			showBody(title);
		} else if (title.equals(comment)) {
			showBody(title);
		}
	}

	private void showBody(String flag) {
		if (flag.equals(detail)) {
			// detailLinearLayout.removeAllViews();
			//
			// detailLinearLayout.addView(mPager);
			// detailLinearLayout.addView(commentInteractView);
			content.removeAllViews();

			rp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			content.addView(commentInteractView, rp);

			rp1.addRule(RelativeLayout.ABOVE, 1);
			content.addView(mPager, rp1);
		} else if (flag.equals(comment)) {
			// detailLinearLayout.removeAllViews();
			bodyLayout.removeAllViews();
			if (commentListView == null) {
				commentListView = new CommentListView(VideoDetailActivity.this,
						tid, data_id, data_name);
				commentListView.setAddCallback(this);
			}
			// detailLinearLayout.addView(commentListView);
			// rp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			bodyLayout.addView(commentListView);
		}

		// detailLinearLayout.invalidate();
		bodyLayout.invalidate();
	}

	private String getHtml(String path, String title, String context) {
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
			// System.out.println("getHtml ::>>before>>>:: " + sb);

			if (sb.indexOf("<!--content-->") > 0)
				sb.insert(
						sb.indexOf("<!--content-->")
								+ "<!--content-->".length(), context);
			// System.out.println("getHtml ::>>>>>:: " + sb);
			result = sb.toString();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void onClick(View v) {
		if (v == imageView) {

			if (sawMap.get(tid + data_id) == null
					|| !sawMap.get(tid + data_id).equals(tid + data_id)) {
				sawInsertUrl = Confi.getInstance()
						.getInsertAccumulativeCountsApiUrl(tid, data_id, "1");

				sawMap.put(tid + data_id, tid + data_id);
				sawAsyncJson = new AsyncJson(this);
				sawAsyncJson.addInterFace(this);
				sawAsyncJson.execute(sawInsertUrl);
			} else {
				playVideo();
			}

		} else if (v == touchShareImageView) {
			showShareDialog(index);
		}
	}

	private void shareTo(int to) {
		ShareInfo shareInfo = new ShareText();
		shareInfo.constructContext(((VideoModel) data.get(index)).getTitle());
		ShareManage.getInstance().share2weibo(VideoDetailActivity.this,
				shareInfo);
	}

	public void saveToSD(int index) {

		if (NetWorkState.isNetworkAvailable(VideoDetailActivity.this)) {
			DownloadManager downloadManager = DownloadManager.getInstance();
			DownloadModel model = new DownloadModel();
			model.setUrl(((VideoModel) data.get(index)).getUrl());
			model.setTitle(((VideoModel) data.get(index)).getTitle());
			model.setTime(((VideoModel) data.get(index)).getTime());
			downloadManager.addTask(VideoDetailActivity.this, model);

			LayoutInflater factory = getLayoutInflater();
			View view = factory.inflate(R.layout.mytoast, null);
			Toast myToast = new Toast(VideoDetailActivity.this);
			myToast.setView(view);
			LinearLayout myToastBg = (LinearLayout) view
					.findViewById(R.id.myToastBg);
			myToastBg.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_background_color()));
			TextView txtToast = (TextView) view
					.findViewById(R.id.myToast_TextView_txtToast);
			//txtToast.setText(R.string.add_download);
			txtToast.setText(R.string.add_cdownload);
			txtToast.setTextColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			myToast.show();
		} else {
			Tools.showBadNetwork(VideoDetailActivity.this);
		}

	}

	// 发送微博和下载对话框
	private void showShareDialog(final int index) {

		if (((VideoModel) data.get(index)).getIsDownload().equals("0")) {
			// 新建AlertDialog
			final AlertDialog alertDialog = new AlertDialog.Builder(
					VideoDetailActivity.this).create();
			alertDialog.show();
			// 读取XML配置布局文件
			alertDialog.setContentView(R.layout.shareanddownloaddialog);
			// 背景
			LinearLayout videoDialogBg = (LinearLayout) alertDialog
					.findViewById(R.id.shareandrdown_Bg);
			videoDialogBg.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_background_color()));

			videoDialogBg.getBackground().setAlpha(95);
			// 分享微博按钮
			TextView txtShare = (TextView) alertDialog
					.findViewById(R.id.shareanddown_textView_share);
			txtShare.setTextColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			txtShare.setText(shareContext[0]);
			txtShare.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					shareTo(index);
				}
			});
			TextView txtLine = (TextView) alertDialog
					.findViewById(R.id.shareanddown_textView_lines);
			txtLine.setVisibility(View.GONE);
			TextView txtDown = (TextView) alertDialog
					.findViewById(R.id.shareanddown_textView_down);
			txtDown.setVisibility(View.GONE);

		} else if (((VideoModel) data.get(index)).getIsDownload().equals("1")) {
			// 新建AlertDialog
			final AlertDialog alertDialog = new AlertDialog.Builder(
					VideoDetailActivity.this).create();
			alertDialog.show();
			// 读取XML配置布局文件
			alertDialog.setContentView(R.layout.shareanddownloaddialog);
			// 背景
			LinearLayout videoDialogBg = (LinearLayout) alertDialog
					.findViewById(R.id.shareandrdown_Bg);
			videoDialogBg.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_background_color()));

			videoDialogBg.getBackground().setAlpha(95);
			// 分享微博按钮
			TextView txtShare = (TextView) alertDialog
					.findViewById(R.id.shareanddown_textView_share);
			txtShare.setText(shareDonloadContext[0]);
			txtShare.setTextColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			txtShare.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					shareTo(index);
					alertDialog.cancel();
				}
			});
			TextView txtLine = (TextView) alertDialog
					.findViewById(R.id.shareanddown_textView_lines);
			txtLine.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));

			TextView txtDown = (TextView) alertDialog
					.findViewById(R.id.shareanddown_textView_down);
			txtDown.setText(shareDonloadContext[1]);
			txtDown.setTextColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			txtDown.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					saveToSD(index);
					alertDialog.cancel();
				}
			});

		}
	}

	private void playVideo() {
		Intent intent = new Intent(VideoDetailActivity.this,
				VideoPlayerActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("url", ((VideoModel) data.get(index)).getUrl());
		intent.putExtras(bundle);
		VideoDetailActivity.this.startActivity(intent);
	}

	private void showPage(int page) {
		// touchTitleTextView.setText((index + 1) + "/" + data.size());
		touchTitleTextView.setText(vTitle);
		data_id = ((VideoModel) data.get(index)).getId();
		commentInteractView = new CommentInteractView(VideoDetailActivity.this,
				tid, data_id, CommentInteractStyle.saw);
		commentInteractView.addCommentCallback(this);
		commentInteractView.invalidate();

		// detailLinearLayout.removeAllViews();
		//
		// detailLinearLayout.addView(mPager);
		// detailLinearLayout.addView(commentInteractView);
		// content.removeAllViews();
		//
		// rp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		// content.addView(commentInteractView,rp);
		//
		// System.out.println("commentInteractView =========== "+commentInteractView.getId());
		// rp1.addRule(RelativeLayout.ABOVE,commentInteractView.getId());
		// content.addView(mPager,rp1);
		add();
		mPager.setCurrentItem(index);
		// detailLinearLayout.invalidate();
		content.invalidate();

		videoDetailUrl = Confi.getInstance().getVideoDetailApiUrl(data_id);
		async = new AsyncJson(this);
		async.addInterFace(this);
		async.execute(videoDetailUrl);
	}

	public void callJoinSuccess(String url, JSONObject json) {
		super.callJoinSuccess(url, json);
		if (url.equals(videoDetailUrl)) {
			try {
				JSONArray jsonArray = json.getJSONArray("datas");

				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(0);

				imageUrl = jsonObject2.getString("video_thumb");

				vTitle = jsonObject2.getString("video_title");
				touchTitleTextView.setText(vTitle);
				vCaption = jsonObject2.getString("video_caption");
				((VideoModel) data.get(index)).setIsDownload(jsonObject2
						.getString("vis_download"));

			} catch (JSONException e) {
				callJoinFailure(videoDetailUrl, videoDetailUrl + e.toString());
			}

			asyncImage = new AsyncImage();
			asyncImage.addImageLoadCallback(this);
			asyncImage.execute(imageUrl);
		}

		if (url.equals(sawInsertUrl)) {
			try {
				if (json.getInt("status") == 1) {
					JSONObject jSONObject = json.getJSONObject("data");

					commentInteractView = new CommentInteractView(
							VideoDetailActivity.this, tid, data_id,
							CommentInteractStyle.saw);
					commentInteractView.addCommentCallback(this);
					commentInteractView.invalidate();

					// detailLinearLayout.removeAllViews();
					//
					// detailLinearLayout.addView(mPager);
					// detailLinearLayout.addView(commentInteractView);
					content.removeAllViews();

					rp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					content.addView(commentInteractView, rp);

					rp1.addRule(RelativeLayout.ABOVE, 1);
					content.addView(mPager, rp1);
					// detailLinearLayout.invalidate();
					content.invalidate();
					bodyLayout.invalidate();
					playVideo();
				}
			} catch (JSONException e) {
				callJoinFailure(url, e.getMessage().toString());
			}
		}

	}

	public void callJoinFailure(String url, String failMessage) {
		super.callJoinFailure(url, failMessage);
	}

	public void actionCallback() {
		playVideo();
	}

	public void editCallback() {
		touchView.display(comment);
		showBody(comment);
	}

	public void callImageSuccess(String url, Drawable drawable) {
		super.callImageSuccess(url, drawable);
		imageView.requestFocus();
		imageView.setBackgroundColor(this.getResources()
				.getColor(R.color.black));
		if (url.equals(imageUrl)) {
			imageView.setImageDrawable(drawable);
			imageView.invalidate();
		}

		htmlDetail = getHtml("video.html", vTitle, vCaption);

		mWebViews[index].loadDataWithBaseURL(null, htmlDetail, "text/html",
				"utf-8", null);

		mWebViews[index].invalidate();
		// detailLinearLayout.invalidate();
		// rp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		// content.addView(commentInteractView,rp);

		// rp1.addRule(RelativeLayout.ABOVE,1);
		// content.addView(mPager,rp1);
		content.invalidate();
		bodyLayout.invalidate();
	}

	public void callImageFailure(String url) {
		// TODO Auto-generated method stub
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
			LayoutInflater factory = getLayoutInflater();
			View view = factory.inflate(R.layout.mytoast, null);
			Toast myToast = new Toast(VideoDetailActivity.this);
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
			// Toast.makeText(VideoDetailActivity.this, R.string.top,
			// Toast.LENGTH_SHORT).show();
			return;
		} else if (index < 0) {
			index = 0;
			LayoutInflater factory = getLayoutInflater();
			View view = factory.inflate(R.layout.mytoast, null);
			Toast myToast = new Toast(VideoDetailActivity.this);
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
			// Toast.makeText(VideoDetailActivity.this, R.string.end,
			// Toast.LENGTH_SHORT).show();
			return;
		}
		System.out.println("select ================ " + index);
		showPage(index);

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

	// 刷新方法

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case REFRESH: {

			videoDetailUrl = Confi.getInstance().getVideoDetailApiUrl(data_id);

			commentInteractView = new CommentInteractView(
					VideoDetailActivity.this, tid, data_id,
					CommentInteractStyle.saw);
			commentInteractView.addCommentCallback(this);
			commentInteractView.invalidate();

			// detailLinearLayout.removeAllViews();
			//
			// detailLinearLayout.addView(mPager);
			// detailLinearLayout.addView(commentInteractView);
			content.removeAllViews();

			rp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			content.addView(commentInteractView, rp);

			rp1.addRule(RelativeLayout.ABOVE, 1);
			content.addView(mPager, rp1);

			AsyncRefreshJson asyncRefreshJson = new AsyncRefreshJson(this);
			asyncRefreshJson.addInterFace(this);
			asyncRefreshJson.execute(videoDetailUrl);
		}
			break;
		case DOWNLOAD: {
			Intent intent = new Intent(VideoDetailActivity.this,
					DownloadActivity.class);
			//intent.putExtra("tabName", "Download");
			intent.putExtra("tabName", "下载");
			VideoDetailActivity.this.startActivity(intent);
		}
			break;
		case SETTING: {
			Intent intent = new Intent(VideoDetailActivity.this,
					SettingActivity.class);
			intent.putExtra("tabName", "Setting");

			VideoDetailActivity.this.startActivity(intent);
		}
			break;
		}
		return true;
	}

	public void addCommentCallback(String url) {
		data_id = ((VideoModel) data.get(index)).getId();
		commentInteractView = new CommentInteractView(VideoDetailActivity.this,
				tid, data_id, CommentInteractStyle.saw);
		commentInteractView.addCommentCallback(this);
		commentInteractView.invalidate();
	}

}
