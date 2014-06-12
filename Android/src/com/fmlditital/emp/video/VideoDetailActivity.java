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

	private LinearLayout detailLinearLayout = null;

	private CharSequence[] shareDonloadContext = { "Share to Sina WeiBo",
			"Download" };

	private CharSequence[] shareContext = { "Share to Sina WeiBo" };

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		type = this.getIntent().getExtras().getString("type");
		if (type.equals("recent")) {
			data = app.getDataVideo();
		} else if (type.equals("popular")) {
			data = app.getDataVideopopular();
		}

		// data = app.getDataVideo();
		// popularData = app.getDataVideopopular();

		data_id = ((VideoModel) data.get(index)).getId();
		data_name = ((VideoModel) data.get(index)).getTitle();

		index();
		touchTitleTextView.setVisibility(View.VISIBLE);
		touchTitleTextView.setText((index + 1) + "/" + data.size());

		videoDetailUrl = Confi.getInstance().getVideoDetailApiUrl(data_id);
		async = new AsyncJson(this);
		async.addInterFace(this);
		async.execute(videoDetailUrl);

		touchShareImageView.setOnClickListener(this);
	}

	private void index() {
		touchBarLayout.setVisibility(View.VISIBLE);

		imageView = new ImageView(this);
		imageView.requestFocus();
		LayoutParams param = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		param.height = Tools.dip2px(this, 182);
		param.gravity = Gravity.CENTER;
		imageView.setAdjustViewBounds(true);
		imageView.setScaleType(ScaleType.FIT_CENTER);

		imageView.setOnClickListener(this);

		frl = new FrameLayout(this);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;

		goImage = new ImageView(this);
		goImage.setImageDrawable(this.getResources().getDrawable(
				R.drawable.go_button));

		frl.addView(imageView, param);
		frl.addView(goImage, params);
		bodyLayout.addView(frl);

		touchView = new TabView(this);
		touchView.addTitle(detail);
		touchView.addTitle(comment);
		touchView.display(detail);
		touchView.addCommentCallback(this);
		bodyLayout.addView(touchView);

		detailLinearLayout = new LinearLayout(this);
		detailLinearLayout.setOrientation(LinearLayout.VERTICAL);
		detailLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		commentInteractView = new CommentInteractView(VideoDetailActivity.this,
				tid, data_id, CommentInteractStyle.saw);
		commentInteractView.addCommentCallback(this);

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

		detailLinearLayout.addView(commentInteractView);
		detailLinearLayout.addView(mPager);

		bodyLayout.addView(detailLinearLayout);

	}

	@Override
	protected void onResume() {
		super.onResume();
		commentInteractView.invalidate();
	}

	@Override
	public void showDetailOrCommentList(String title) {
		if (title.equals(detail)) {
			showBody(title);
		} else if (title.equals(comment)) {
			showBody(title);
		}
	}

	private void showBody(String flag) {
		if (flag.equals(detail)) {
			detailLinearLayout.removeAllViews();
			detailLinearLayout.addView(commentInteractView);
			detailLinearLayout.addView(mPager);
		} else if (flag.equals(comment)) {
			detailLinearLayout.removeAllViews();

			if (commentListView == null) {
				commentListView = new CommentListView(VideoDetailActivity.this,
						tid, data_id, data_name);
				commentListView.setAddCallback(this);
			}
			detailLinearLayout.addView(commentListView);
		}

		detailLinearLayout.invalidate();
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

			if (sb.indexOf("<!--title-->") > 0)
				sb.insert(sb.indexOf("<!--title-->") + "<!--title-->".length(),
						title);
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
			txtToast.setText(R.string.add_download);
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
			txtShare.setText("Share to Sina WeiBo");
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
			txtShare.setText("Share to Sina WeiBo");
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
			txtDown.setText("Download");
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
		touchTitleTextView.setText((index + 1) + "/" + data.size());

		data_id = ((VideoModel) data.get(index)).getId();
		commentInteractView = new CommentInteractView(VideoDetailActivity.this,
				tid, data_id, CommentInteractStyle.saw);
		commentInteractView.addCommentCallback(this);
		commentInteractView.invalidate();

		detailLinearLayout.removeAllViews();
		detailLinearLayout.addView(commentInteractView);
		detailLinearLayout.addView(mPager);
		mPager.setCurrentItem(index);
		detailLinearLayout.invalidate();

		videoDetailUrl = Confi.getInstance().getVideoDetailApiUrl(data_id);
		async = new AsyncJson(this);
		async.addInterFace(this);
		async.execute(videoDetailUrl);
	}

	@Override
	public void callJoinSuccess(String url, JSONObject json) {
		super.callJoinSuccess(url, json);
		if (url.equals(videoDetailUrl)) {
			try {
				JSONArray jsonArray = json.getJSONArray("datas");

				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(0);

				imageUrl = jsonObject2.getString("video_thumb");

				vTitle = jsonObject2.getString("video_title");
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

					detailLinearLayout.removeAllViews();
					detailLinearLayout.addView(commentInteractView);
					detailLinearLayout.addView(mPager);

					detailLinearLayout.invalidate();
					bodyLayout.invalidate();
					playVideo();
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
	public void actionCallback() {
		playVideo();
	}

	@Override
	public void editCallback() {
		touchView.display(comment);
		showBody(comment);
	}

	@Override
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
		detailLinearLayout.invalidate();
		bodyLayout.invalidate();
	}

	@Override
	public void callImageFailure(String url) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
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
		showPage(index);

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

	// 刷新方法
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case REFRESH: {

			videoDetailUrl = Confi.getInstance().getVideoDetailApiUrl(data_id);

			commentInteractView = new CommentInteractView(
					VideoDetailActivity.this, tid, data_id,
					CommentInteractStyle.saw);
			commentInteractView.addCommentCallback(this);
			commentInteractView.invalidate();

			detailLinearLayout.removeAllViews();
			detailLinearLayout.addView(commentInteractView);
			detailLinearLayout.addView(mPager);

			AsyncRefreshJson asyncRefreshJson = new AsyncRefreshJson(this);
			asyncRefreshJson.addInterFace(this);
			asyncRefreshJson.execute(videoDetailUrl);
		}
			break;
		case DOWNLOAD: {
			Intent intent = new Intent(VideoDetailActivity.this,
					DownloadActivity.class);
			intent.putExtra("tabName", "Download");
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

	@Override
	public void addCommentCallback(String url) {
		data_id = ((VideoModel) data.get(index)).getId();
		commentInteractView = new CommentInteractView(VideoDetailActivity.this,
				tid, data_id, CommentInteractStyle.saw);
		commentInteractView.addCommentCallback(this);
		commentInteractView.invalidate();
	}

}
