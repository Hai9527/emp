package com.fmlditital.emp.music;

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
import com.fmlditital.emp.base.DefaultActivity;
import com.fmlditital.emp.base.DetailBaseActivity;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.dowmload.DownloadActivity;
import com.fmlditital.emp.dowmload.DownloadManager;
import com.fmlditital.emp.interf.AddCommentCallback;
import com.fmlditital.emp.manage.MusicManage;
import com.fmlditital.emp.model.DownloadModel;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.model.MusicModel;
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

public class MusicDetailActivity extends DetailBaseActivity implements
		OnClickListener, OnPageChangeListener, AddCommentCallback {

	private static final int REFRESH = Menu.FIRST;
	private static final int DOWNLOAD = Menu.FIRST + 1;
	private static final int SETTING = Menu.FIRST + 2;

	// private CharSequence[] shareDownloadContext = { "Share to Sina WeiBo",
	// "Download" };
	// private CharSequence[] shareContext = { "Share to Sina WeiBo" };
	private CharSequence[] shareDonloadContext = { "分享到微博", "下载" };

	private CharSequence[] shareContext = { "分享到微博" };
	private String musicDetaUrl = null;

	private AsyncJson async = null;

	private ViewPager mPager;
	private List<View> listViews;
	private WebView[] mWebViews = null;
	private String htmlDetail = null, mTitle = null, mCaption = null;

	// for ui
	// private LinearLayout detailLinearLayout = null;

	private TabView touchView = null;
	private String detail = Global.DETAIL, comment = Global.COMMENT;

	private CommentListView commentListView = null;
	private CommentInteractView commentInteractView = null;

	private AsyncImage asyncImage = null;
	private ImageView imageView = null;
	private String imageUrl = null;

	private UIConfig uiConfig = Confi.getInstance().getuIConfig();
	LayoutParams parm = new LayoutParams(LayoutParams.WRAP_CONTENT,
			LayoutParams.WRAP_CONTENT);

	private RelativeLayout musicLayout;
	private LayoutInflater factory = null;
	RelativeLayout content;
	RelativeLayout.LayoutParams rp;
	RelativeLayout.LayoutParams rp1;

	// private Map<String, String> sawMap = app.getSawMap();
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rp = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,
				Tools.dip2px(this, 50));
		rp1 = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		parm.setMargins(0, Tools.dip2px(this, 20), 0, 0);
		data = app.getDataMusic();
		data_name = ((MusicModel) data.get(index)).getTitle();
		data_id = ((MusicModel) data.get(index)).getId();

		factory = LayoutInflater.from(this);
		musicLayout = (RelativeLayout) factory.inflate(R.layout.music, null);

		index();

		musicDetaUrl = Confi.getInstance().getMusicDetailApiUrl(data_id);
//		System.out.println("music musicDetaUrl= =========== "+musicDetaUrl);
		async = new AsyncJson(this);
		async.addInterFace(this);
		async.execute(musicDetaUrl);

		touchShareImageView.setOnClickListener(this);

		// touchTitleTextView.setText((index + 1) + "/" + data.size());
		touchTitleTextView.setText(mTitle);
	}

	private void index() {
		touchBarLayout.setVisibility(View.VISIBLE);
		touchTitleTextView.setVisibility(View.VISIBLE);

		imageView = new ImageView(this);
		LayoutParams param = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		param.height = Tools.dip2px(this, 182);
		param.gravity = Gravity.CENTER;
		imageView.setAdjustViewBounds(true);
		imageView.setScaleType(ScaleType.FIT_XY);
		// imageView.setOnClickListener(this);
		bodyLayout.addView(imageView, param);

		touchView = new TabView(this);
		touchView.addTitle(detail);
		touchView.addTitle(comment);
		touchView.display(detail);
		touchView.addCommentCallback(this);
		// bodyLayout.addView(touchView);

		// detailLinearLayout = new LinearLayout(this);
		// detailLinearLayout.setOrientation(LinearLayout.VERTICAL);
		// detailLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
		// LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		//
		commentInteractView = new CommentInteractView(MusicDetailActivity.this,
				tid, data_id, CommentInteractStyle.listen);
		// commentInteractView = (CommentInteractView) musicLayout
		// .findViewById(R.id.music_comment);
		// commentInteractView.init(tid, data_id, CommentInteractStyle.listen);
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
		// mPager = (ViewPager) musicLayout.findViewById(R.id.music_viewpager);
		// mPager.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		// DefaultActivity.getScreenHeight() / 4));
		mPager.setAdapter(new ViewPagerAdapter(listViews));
		mPager.setCurrentItem(index);

		mPager.setOnPageChangeListener(this);
		content = new RelativeLayout(this);
		content.setLayoutParams(new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		rp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		content.addView(commentInteractView, rp);

		rp1.addRule(RelativeLayout.ABOVE, 1);
		content.addView(mPager, rp1);

//		 musicLayout.addView(mPager);
//		 musicLayout.addView(commentInteractView);
		// detailLinearLayout.addView(mPager);
		// detailLinearLayout.addView(commentInteractView, parm);
		// bodyLayout.addView(detailLinearLayout);
//		 musicLayout.setLayoutParams(new RelativeLayout.LayoutParams(
//		 LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
//		 bodyLayout.addView(musicLayout);
		bodyLayout.addView(content);

	}

	public void onClick(View v) {
		if (v == touchShareImageView) {
			showShareDialog(index);
			return;
		}

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
			;
			is = am.open(path);
			bo = new ByteArrayOutputStream();
			while ((i = is.read()) != -1)
				bo.write(i);
			bo.close();
			is.close();

			sb.append(bo.toString());
			// if (sb.indexOf("<!--title-->") > 0)
			// sb.insert(sb.indexOf("<!--title-->") + "<!--title-->".length(),
			// title);
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

	public void saveToSD(int index) {

		if (NetWorkState.isNetworkAvailable(MusicDetailActivity.this)) {
			DownloadManager downloadManager = DownloadManager.getInstance();
			DownloadModel model = new DownloadModel();
			model.setUrl(((MusicModel) data.get(index)).getUrl());
			model.setTitle(((MusicModel) data.get(index)).getTitle());
			// model.setNane(((MusicModel) data.get(index)).getTitle());
			// model.setTime(data.get(index).getTime());
			downloadManager.addTask(MusicDetailActivity.this, model);

			LayoutInflater factory = getLayoutInflater();
			View view = factory.inflate(R.layout.mytoast, null);
			Toast myToast = new Toast(MusicDetailActivity.this);
			myToast.setView(view);
			LinearLayout myToastBg = (LinearLayout) view
					.findViewById(R.id.myToastBg);
			myToastBg.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_background_color()));
			TextView txtToast = (TextView) view
					.findViewById(R.id.myToast_TextView_txtToast);
			// txtToast.setText(R.string.add_download);
			txtToast.setText(R.string.add_cdownload);
			txtToast.setTextColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			myToast.show();
		} else {
			Tools.showBadNetwork(MusicDetailActivity.this);
		}
	}

	private void shareTo(int to) {
		ShareInfo shareInfo = new ShareText();
		shareInfo.constructContext(((MusicModel) data.get(index)).getTitle());

		ShareManage.getInstance().share2weibo(MusicDetailActivity.this,
				shareInfo);
	}

	private void showShareDialog(final int index) {
		if (((MusicModel) data.get(index)).getIsDownload().equals("0")) {
			// æ–°å»ºAlertDialog
			final AlertDialog alertDialog = new AlertDialog.Builder(
					MusicDetailActivity.this).create();
			alertDialog.show();
			// è¯»å�–XMLé…�ç½®å¸ƒå±€æ–‡ä»¶
			alertDialog.setContentView(R.layout.shareanddownloaddialog);
			// èƒŒæ™¯
			LinearLayout videoDialogBg = (LinearLayout) alertDialog
					.findViewById(R.id.shareandrdown_Bg);
			videoDialogBg.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_background_color()));

			videoDialogBg.getBackground().setAlpha(150);
			// åˆ†äº«å¾®å�šæŒ‰é’®
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

		} else if (((MusicModel) data.get(index)).getIsDownload().equals("1")) {
			// æ–°å»ºAlertDialog
			final AlertDialog alertDialog = new AlertDialog.Builder(
					MusicDetailActivity.this).create();
			alertDialog.show();
			// è¯»å�–XMLé…�ç½®å¸ƒå±€æ–‡ä»¶
			alertDialog.setContentView(R.layout.shareanddownloaddialog);
			// èƒŒæ™¯
			LinearLayout videoDialogBg = (LinearLayout) alertDialog
					.findViewById(R.id.shareandrdown_Bg);
			videoDialogBg.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_background_color()));

			videoDialogBg.getBackground().setAlpha(150);
			// åˆ†äº«å¾®å�šæŒ‰é’®
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

	private void showBody(String flag) {
		if (flag.equals(detail)) {

			// detailLinearLayout.removeAllViews();
			//
			// detailLinearLayout.addView(mPager);
			// detailLinearLayout.addView(commentInteractView, parm);
			// musicLayout.removeAllViews();
			// musicLayout.addView(mPager);
			// musicLayout.addView(commentInteractView, parm);
		} else if (flag.equals(comment)) {
			bodyLayout.removeAllViews();
			// detailLinearLayout.removeAllViews();
			if (commentListView == null) {
				commentListView = new CommentListView(MusicDetailActivity.this,
						tid, data_id, data_name);
				commentListView.setAddCallback(this);
			}
			// detailLinearLayout.addView(commentListView);
			bodyLayout.addView(commentListView);
		}

		bodyLayout.invalidate();
	}

	public void actionCallback() {
		if (NetWorkState.isNetworkAvailable(MusicDetailActivity.this)) {
			MusicManage.getMusicManage(data).playMusic(index);

			theFirstOpenMusic = false;
			playState = true;
			isOpenPlay = true;

			musicImageView.setVisibility(View.GONE);
			musicPlayPauseImageView.setVisibility(View.VISIBLE);
			musicPlayPauseImageView.setImageResource(R.drawable.stop_icon);
			musicLinearLayout.setVisibility(View.VISIBLE);
			musicPlayPauseImageView.setVisibility(View.VISIBLE);
			newsLinearLayout.setVisibility(View.GONE);
			musicPlayPauseImageView.setImageResource(R.drawable.stop_icon);
		} else {
			Tools.showBadNetwork(MusicDetailActivity.this);
		}
	}

	public void editCallback() {
		touchView.display(comment);
		showBody(comment);
	}

	public void showDetailOrCommentList(String title) {
		if (title.equals(detail)) {
			showBody(title);
		} else if (title.equals(comment)) {
			showBody(title);
		}
	}

	public void callJoinSuccess(String url, JSONObject json) {
		super.callJoinSuccess(url, json);
		if (url.equals(musicDetaUrl)) {
			try {
				JSONArray jsonArray = json.getJSONArray("datas");

				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(0);

				mTitle = jsonObject2.getString("music_title");
				touchTitleTextView.setText(mTitle);
				mCaption = jsonObject2.getString("music_caption");
                Log.d("", "MusicDetailActivity----------------calljoinSucess");
				((MusicModel) data.get(index)).setIsDownload(jsonObject2
						.getString("download"));
				imageUrl = jsonObject2.getString("music_thumb");
				asyncImage = new AsyncImage();
				asyncImage.addImageLoadCallback(this);
				asyncImage.execute(imageUrl);
				htmlDetail = getHtml("music.html", mTitle, mCaption);
		        Log.d(" "+index, "MusicDetailActivity----------------"+htmlDetail);

				mWebViews[index].loadDataWithBaseURL(null,htmlDetail, "text/html", "utf-8",null);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			mWebViews[index].invalidate();
			musicLayout.invalidate();
			bodyLayout.invalidate();
		}
	}

	public void callJoinFailure(String url, String failMessage) {
		super.callJoinFailure(url, failMessage);
	}

	public void callImageSuccess(String url, Drawable drawable) {
		super.callImageSuccess(url, drawable);
		imageView.setBackgroundColor(this.getResources()
				.getColor(R.color.black));
		if (url.equals(imageUrl)) {
			if (drawable != null)
				imageView.setImageDrawable(drawable);
			else
				imageView.setImageResource(R.drawable.music_cover);

		}
//		htmlDetail = getHtml("music.html", mTitle, mCaption);
        Log.d("", "MusicDetailActivity----------------callImageSucess");

//		mWebViews[index].loadData(htmlDetail, "text/html", "utf-8");
//		mWebViews[index].invalidate();
		
		musicLayout.invalidate();
		bodyLayout.invalidate();
	}

	public void callImageFailure(String url) {

	}

	private void showPage() {

		// touchTitleTextView.setText((index + 1) + "/" + data.size());
		touchTitleTextView.setText(mTitle);
		data_id = ((MusicModel) data.get(index)).getId();

		commentInteractView = new CommentInteractView(MusicDetailActivity.this,
				tid, data_id, CommentInteractStyle.listen);
		commentInteractView.addCommentCallback(this);
		commentInteractView.invalidate();

		// detailLinearLayout.removeAllViews();
		//
		// detailLinearLayout.addView(mPager);
		// detailLinearLayout.addView(commentInteractView, parm);
		// musicLayout.removeAllViews();
		// musicLayout.addView(mPager);
		// musicLayout.addView(commentInteractView, parm);
		add();
		mPager.setCurrentItem(index);
		content.invalidate();
		// detailLinearLayout.invalidate();
//		 musicLayout.invalidate();

		imageUrl = ((MusicModel) data.get(index)).getThumb();
		asyncImage = new AsyncImage();
		asyncImage.addImageLoadCallback(this);
		asyncImage.execute(imageUrl);

		musicDetaUrl = Confi.getInstance().getMusicDetailApiUrl(data_id);
		async = new AsyncJson(this);
		async.addInterFace(this);
		async.execute(musicDetaUrl);
	}

	private void add() {
		content.removeAllViews();

		rp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		content.addView(commentInteractView, rp);
		commentInteractView.setId(1);

		rp1.addRule(RelativeLayout.ABOVE, 1);
		content.addView(mPager, rp1);
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

			data_id = ((MusicModel) data.get(index)).getId();
			musicDetaUrl = Confi.getInstance().getMusicDetailApiUrl(data_id);

			commentInteractView = new CommentInteractView(
					MusicDetailActivity.this, tid, data_id,
					CommentInteractStyle.listen);
			commentInteractView.addCommentCallback(this);
			commentInteractView.invalidate();

			// detailLinearLayout.removeAllViews();
			//
			// detailLinearLayout.addView(mPager);
			// detailLinearLayout.addView(commentInteractView, parm);
			// musicLayout.removeAllViews();
			// musicLayout.addView(mPager);
			// musicLayout.addView(commentInteractView, parm);
			AsyncRefreshJson asyncRefreshJson = new AsyncRefreshJson(this);
			asyncRefreshJson.addInterFace(this);
			asyncRefreshJson.execute(musicDetaUrl);
		}
			break;
		case DOWNLOAD: {
			Intent intent = new Intent(MusicDetailActivity.this,
					DownloadActivity.class);
			// intent.putExtra("tabName", "Download");
			intent.putExtra("tabName", "下载");
			MusicDetailActivity.this.startActivity(intent);
		}
			break;
		case SETTING: {
			Intent intent = new Intent(MusicDetailActivity.this,
					SettingActivity.class);
			intent.putExtra("tabName", "Setting");

			MusicDetailActivity.this.startActivity(intent);
		}
			break;
		}
		return true;
	}

	public void onPageScrollStateChanged(int arg0) {

	}

	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	public void onPageSelected(int arg0) {
		index = arg0;
		if (index < 0) {
			index = 0;
			LayoutInflater factory = getLayoutInflater();
			View view = factory.inflate(R.layout.mytoast, null);
			Toast myToast = new Toast(MusicDetailActivity.this);
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
			return;
		} else if (index < 0) {
			index = 0;
			LayoutInflater factory = getLayoutInflater();
			View view = factory.inflate(R.layout.mytoast, null);
			Toast myToast = new Toast(MusicDetailActivity.this);
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
			return;
		}
		showPage();
	}

	public void addCommentCallback(String url) {
		data_id = ((MusicModel) data.get(index)).getId();

		commentInteractView = new CommentInteractView(MusicDetailActivity.this,
				tid, data_id, CommentInteractStyle.listen);
		commentInteractView.addCommentCallback(this);
		commentInteractView.invalidate();
	}

	public void bottomMusicCallback(String url) {
		// TODO Auto-generated method stub
		if (url.equals(((MusicModel) data.get(index)).getUrl())) {
			commentInteractView = new CommentInteractView(
					MusicDetailActivity.this, tid, data_id,
					CommentInteractStyle.listen);
			commentInteractView.addCommentCallback(this);
			commentInteractView.invalidate();

			// detailLinearLayout.removeAllViews();
			//
			// detailLinearLayout.addView(mPager);
			// detailLinearLayout.addView(commentInteractView, parm);
			// musicLayout.removeAllViews();
			// musicLayout.addView(mPager);
			// musicLayout.addView(commentInteractView, parm);
		}

	}
}
