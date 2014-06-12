package com.fmlditital.emp.gallery;

import java.io.File;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.fmlditital.emp.R;
import com.fmlditital.emp.app.EMPApp;
import com.fmlditital.emp.async.AsyncImage;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.async.AsyncRefreshJson;
import com.fmlditital.emp.base.DefaultActivity;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.dowmload.DownloadActivity;
import com.fmlditital.emp.dowmload.DownloadManager;
import com.fmlditital.emp.interf.AddCommentCallback;
import com.fmlditital.emp.interf.ImageLoadCallback;
import com.fmlditital.emp.manage.ImageSaveManage;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.DownloadModel;
import com.fmlditital.emp.model.GalleryThumbModel;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.preferences.SettingActivity;
import com.fmlditital.emp.share.ShareManage;
import com.fmlditital.emp.share.info.SharePicture;
import com.fmlditital.emp.tool.ImageTools;
import com.fmlditital.emp.tool.NetWorkState;
import com.fmlditital.emp.tool.Tools;
import com.fmlditital.emp.view.CommentInteractView;
import com.fmlditital.emp.view.CommentInteractView.CommentInteractStyle;
import com.fmlditital.emp.view.CommentListView;
import com.fmlditital.emp.view.TabView;

/**
 * @author å�•å¼ æ˜¾ç¤ºå›¾ç‰‡
 */
public class ShowPictureActivity extends DefaultActivity implements
		ViewSwitcher.ViewFactory, OnClickListener, OnTouchListener,
		ImageLoadCallback, AddCommentCallback {
	public static EMPApp app = EMPApp.getSingleton();
	public static List<BaseModel> data = app.getDataGalleryThumb();

	// for photo detail
	private ImageSwitcher mSwitcher = null;
	private LayoutInflater mInflater;
	private RelativeLayout detailRelativeLayout = null;
	private LinearLayout commentInteractLinearLayout = null;
	private CommentInteractView commentInteractView = null;

	private LinearLayout detailLinearLayout = null;

	private UIConfig uiConfig = Confi.getInstance().getuIConfig();

	private String imageUrl = null;

	private AsyncImage asyncImage = null;

	public static int index = 0;
	private int downX, upX;

	private TextView titleView = null;

	private CharSequence[] shareDownloadContext = { "Share to Sina WeiBo",
			"Download" };
	private CharSequence[] shareContext = { "Share to Sina WeiBo" };

	private String detail = Global.DETAIL, comment = Global.COMMENT;

	private String tid = null, data_id = null, data_name = null,
			dataUrl = null;

	private Animation slideLeftIn = null;
	private Animation slideLeftOut = null;
	private Animation slideRightIn = null;
	private Animation slideRightOut = null;

	private TabView tabView = null;

	private CommentListView commentListView = null;

	private AsyncJson asyncJson = null;

	private static final int REFRESH = Menu.FIRST;
	private static final int DOWNLOAD = Menu.FIRST + 1;
	private static final int SETTING = Menu.FIRST + 2;

	private ImageView shareImageView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		index = this.getIntent().getExtras().getInt("index");
		tid = this.getIntent().getExtras().getString("tid");
		dataUrl = this.getIntent().getExtras().getString("dataUrl");
		Log.d("showPictureActivity ===== ", "dataUrl ::: " + dataUrl);
		// data_id = ((GalleryThumbModel) data.get(index)).getId();
		data_id = ((GalleryThumbModel) data.get(index)).getpId();
		data_name = ((GalleryThumbModel) data.get(index)).getTitle();

		index();

		slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.slide_left_in);
		slideLeftOut = AnimationUtils
				.loadAnimation(this, R.anim.slide_left_out);
		slideRightIn = AnimationUtils
				.loadAnimation(this, R.anim.slide_right_in);
		slideRightOut = AnimationUtils.loadAnimation(this,
				R.anim.slide_right_out);

		// for comment Interact
		commentInteractView = new CommentInteractView(ShowPictureActivity.this,
				tid, data_id, CommentInteractStyle.photo);
		commentInteractView.addCommentCallback(this);
		commentInteractLinearLayout.addView(commentInteractView);
		
		shareImageView = (ImageView) commentInteractView
				.findViewById(R.id.comment_interact_view_share);
		shareImageView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				showShareDialog(index);
			}
		});
		titleView.setText(((GalleryThumbModel) data.get(index)).getpName());
		titleView.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
		touchTitleTextView.setText((index + 1) + "/" + data.size());

		imageUrl = ((GalleryThumbModel) data.get(index)).getpImageUrl();

		asyncImage = new AsyncImage();
		asyncImage.addImageLoadCallback(this);
		asyncImage.execute(imageUrl);

		touchShareImageView.setOnClickListener(this);
	}

	private void index() {
		touchBarLayout.setVisibility(View.VISIBLE);
		touchTitleTextView.setVisibility(View.VISIBLE);
		touchShareImageView.setVisibility(View.GONE);
		tabView = new TabView(this);
		tabView.addTitle(detail);
		tabView.addTitle(comment);
		tabView.display(detail);
		tabView.addCommentCallback(this);

		// bodyLayout.addView(tabView);
		detailLinearLayout = new LinearLayout(this);
		detailLinearLayout.setOrientation(LinearLayout.VERTICAL);

		mInflater = LayoutInflater.from(ShowPictureActivity.this);
		
		detailRelativeLayout = (RelativeLayout) mInflater.inflate(
				R.layout.showpicturedetail, null);

		mSwitcher = (ImageSwitcher) detailRelativeLayout
				.findViewById(R.id.showpicturedetail_imageSwitcher);
		mSwitcher.setFactory(this);
		mSwitcher.setOnTouchListener(this);
		mSwitcher.setClickable(true);
		mSwitcher.requestFocus();
		commentInteractLinearLayout = (LinearLayout) detailRelativeLayout
				.findViewById(R.id.showpicturedetail_linearLayout_count);

		titleView = (TextView) detailRelativeLayout
				.findViewById(R.id.showpicturedetail_title);

		detailLinearLayout.addView(detailRelativeLayout);
		bodyLayout.addView(detailLinearLayout);
		
	}

	@Override
	public void onConfigurationChanged(Configuration conf) {
		super.onConfigurationChanged(conf);
		if (conf.orientation == Configuration.ORIENTATION_LANDSCAPE) {

		} else if (conf.orientation == Configuration.ORIENTATION_PORTRAIT) {

		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.d("ShowPictureActivity :::: ", "ShowPictureActivity  onTouch");
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			downX = (int) event.getX();

			return true;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			upX = (int) event.getX();
			if (upX - downX > 100) { // Left onto the right, previousPic
				previousPic();
			} else if (downX - upX > 100) {// Right onto the left, nextPic
				nextPic();
			}
		}
		return false;
	}

	private void previousPic() {
		index--;
		if (index < 0) {
			index = 0;
			LayoutInflater factory = getLayoutInflater();
			View view = factory.inflate(R.layout.mytoast, null);
			Toast myToast = new Toast(ShowPictureActivity.this);
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
			// Toast.makeText(ShowPictureActivity.this, R.string.top,
			// Toast.LENGTH_SHORT).show();
			return;
		}

		commentListView = null;

		mSwitcher.setInAnimation(slideRightIn);
		mSwitcher.setOutAnimation(slideRightOut);

		data_id = ((GalleryThumbModel) data.get(index)).getpId();

		commentInteractView = new CommentInteractView(ShowPictureActivity.this,
				tid, data_id, CommentInteractStyle.photo);
		commentInteractView.addCommentCallback(this);
		commentInteractLinearLayout.removeAllViews();
		commentInteractLinearLayout.addView(commentInteractView);

		titleView.setText(((GalleryThumbModel) data.get(index)).getpName());
		touchTitleTextView.setText((index + 1) + "/" + data.size());

		imageUrl = ((GalleryThumbModel) data.get(index)).getpImageUrl();
		asyncImage = new AsyncImage();
		asyncImage.addImageLoadCallback(this);
		asyncImage.execute(imageUrl);

	}

	private void nextPic() {
		index++;
		if (index >= data.size()) {
			index = data.size() - 1;
			LayoutInflater factory = getLayoutInflater();
			View view = factory.inflate(R.layout.mytoast, null);
			Toast myToast = new Toast(ShowPictureActivity.this);
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
			// Toast.makeText(ShowPictureActivity.this, R.string.end,
			// Toast.LENGTH_SHORT).show();
			return;
		}

		commentListView = null;

		mSwitcher.setInAnimation(slideLeftIn);
		mSwitcher.setOutAnimation(slideLeftOut);

		data_id = ((GalleryThumbModel) data.get(index)).getpId();

		commentInteractView = new CommentInteractView(ShowPictureActivity.this,
				tid, data_id, CommentInteractStyle.photo);
		commentInteractView.addCommentCallback(this);
		commentInteractLinearLayout.removeAllViews();
		commentInteractLinearLayout.addView(commentInteractView);

		titleView.setText(((GalleryThumbModel) data.get(index)).getpName());
		touchTitleTextView.setText((index + 1) + "/" + data.size());

		imageUrl = ((GalleryThumbModel) data.get(index)).getpImageUrl();
		asyncImage = new AsyncImage();
		asyncImage.addImageLoadCallback(this);
		asyncImage.execute(imageUrl);
	}

	@Override
	public void onClick(View v) {
		Log.d("ShowPictureActivity :::: ", "ShowPictureActivity  onClick");
		if (v == touchShareImageView) {
			showShareDialog(index);
		}

	}

	private void showShareDialog(final int index) {

		Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>data.size() 	" + data.size());
		Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>getIsDownload() 	"
				+ ((GalleryThumbModel) data.get(index)).getIsDownload());
		if (((GalleryThumbModel) data.get(index)).getIsDownload().equals("0")) {
			final AlertDialog alertDialog = new AlertDialog.Builder(this)
					.create();
			alertDialog.show();
			alertDialog.setContentView(R.layout.shareanddownloaddialog);
			LinearLayout videoDialogBg = (LinearLayout) alertDialog
					.findViewById(R.id.shareandrdown_Bg);
			videoDialogBg.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_background_color()));

			videoDialogBg.getBackground().setAlpha(150);
			TextView txtShare = (TextView) alertDialog
					.findViewById(R.id.shareanddown_textView_share);
			txtShare.setTextColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			txtShare.setText("Share to Sina WeiBo");
			txtShare.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					shareTo(index);
					alertDialog.cancel();
				}
			});
			TextView txtLine = (TextView) alertDialog
					.findViewById(R.id.shareanddown_textView_lines);
			txtLine.setVisibility(View.GONE);
			TextView txtDown = (TextView) alertDialog
					.findViewById(R.id.shareanddown_textView_down);
			txtDown.setVisibility(View.GONE);
		} else if (((GalleryThumbModel) data.get(index)).getIsDownload()
				.equals("1")) {
			final AlertDialog alertDialog = new AlertDialog.Builder(this)
					.create();
			alertDialog.show();
			alertDialog.setContentView(R.layout.shareanddownloaddialog);
			LinearLayout videoDialogBg = (LinearLayout) alertDialog
					.findViewById(R.id.shareandrdown_Bg);
			videoDialogBg.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_background_color()));

			videoDialogBg.getBackground().setAlpha(150);
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

	public void saveToSD(int index) {
		if (NetWorkState.isNetworkAvailable(ShowPictureActivity.this)) {
			DownloadManager downloadManager = DownloadManager.getInstance();
			DownloadModel model = new DownloadModel();
			model.setUrl(((GalleryThumbModel) data.get(index)).getpImageUrl());
			model.setTitle(((GalleryThumbModel) data.get(index)).getpName());

			downloadManager.addTask(ShowPictureActivity.this, model);
			LayoutInflater factory = getLayoutInflater();
			View view = factory.inflate(R.layout.mytoast, null);
			Toast myToast = new Toast(ShowPictureActivity.this);
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
			Tools.showBadNetwork(ShowPictureActivity.this);
		}

	}

	public void shareTo(int to) {
		SharePicture shareInfo = new SharePicture();
		if ((((GalleryThumbModel) data.get(to)).getDescription() != null)
				&& (((GalleryThumbModel) data.get(to)).getDescription()
						.equals("null")))
			shareInfo.constructContext("Image \""
					+ (((GalleryThumbModel) data.get(to)).getpName() + "\""));
		else
			shareInfo.constructContext("Image");

		File fileName = ImageSaveManage
				.getDrawablePatch(((GalleryThumbModel) data.get(to))
						.getpImageUrl());

		shareInfo.setImagePath(fileName.toString());

		ShareManage.getInstance().share2weibo(ShowPictureActivity.this,
				shareInfo);
	}

	@Override
	public View makeView() {
		ImageView i = new ImageView(this);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		// i.setScaleType(ImageView.ScaleType.CENTER_CROP);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		// i.setLayoutParams(new ImageSwitcher.LayoutParams(
		// DefaultActivity.getScreenWidth(),
		// DefaultActivity.getScreenHeight()));

		return i;
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

	private void showBody(String title) {
		if (title.equals(detail)) {
			detailLinearLayout.removeAllViews();
			detailLinearLayout.addView(detailRelativeLayout);
		} else if (title.equals(comment)) {
			detailLinearLayout.removeAllViews();
			if (commentListView == null) {
				data_id = ((GalleryThumbModel) data.get(index)).getpId();
				commentListView = new CommentListView(ShowPictureActivity.this,
						tid, data_id, data_name);
				commentListView.setAddCallback(this);
			}
			detailLinearLayout.addView(commentListView);
		}
	}

	public void editCallback() {
		super.editCallback();
		tabView.display(comment);
		showBody(comment);
	}

	public void callImageSuccess(String url, Drawable drawable) {
		super.callImageSuccess(url, drawable);
		if (url.equals(imageUrl)) {
			if (drawable != null) {
				drawable = ImageTools.createReflectionDrawable(drawable);
				// drawable = ImageTools.zoomDrawable(drawable, 600, 860);
				int width = drawable.getIntrinsicWidth();
				int height = drawable.getIntrinsicHeight();
				int mWidth = DefaultActivity.getScreenWidth() / 125 * 100;
				int mHeight = DefaultActivity.getScreenHeight() / 143 * 100;
				// if (width > mWidth) {
				// width = 800;

				// drawable = ImageTools.zoomDrawable(drawable,
				// DefaultActivity.getScreenWidth() / 125 * 100,
				// DefaultActivity.getScreenHeight() / 143 * 100);
				// }
				// if (height > mHeight) {
				// height = 600;
				// }
				Log.v("callImageSuccess",
						">>>>>>>>>>>>>>> ShowPictureActivity " + imageUrl);
				// Log.v("",
				// ">>>>>>>>>>>>>> drawable "+drawable.getIntrinsicWidth());
				mSwitcher.setImageDrawable(drawable);
				mSwitcher.invalidate();
			}
		}
	}

	public void callImageFailure(String url) {

	}

	public void callJoinSuccess(String url, JSONObject json) {
		super.callJoinSuccess(url, json);
		Log.v("callJoinSuccess>>>>>",
				">>>>>>>>>>>>callJoinSuccess>>>>>>>>>>>>>> " + url + ":::"
						+ url.equals(dataUrl));
		if (url.equals(dataUrl)) {
			data.clear();
			try {
				JSONArray jsonArray = json.getJSONArray("datas");

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
					if (jsonObject2.getString("gstatus").equals("1")) {
						GalleryThumbModel model = new GalleryThumbModel();
						model.setId(jsonObject2.getString("gid"));
						model.setpThumbUrl(jsonObject2.getString("photo_thumb"));
						model.setpImageUrl(jsonObject2.getString("photo_image"));
						model.setpName(jsonObject2.getString("photo_title"));
						model.setDescription(jsonObject2
								.getString("gdescription"));
						model.setIsDownload(jsonObject2.getString("isdownload"));
						model.setTitle(jsonObject2.getString("gname"));
						model.setpId(jsonObject2.getString("photo_id"));
						data.add(model);
					}
				}
			} catch (JSONException e) {
			}

			if (index > data.size())
				index = data.size() - 1;

			titleView.setText(((GalleryThumbModel) data.get(index)).getpName());
			imageUrl = ((GalleryThumbModel) data.get(index)).getpImageUrl();

			Log.v("", ">>>>>>>>>>>>show>>>>>>>>>>>>>>> imageUrl " + imageUrl);
			asyncImage = new AsyncImage();
			asyncImage.addImageLoadCallback(this);
			asyncImage.execute(imageUrl);
		}
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
			commentInteractView = new CommentInteractView(
					ShowPictureActivity.this, tid, data_id,
					CommentInteractStyle.photo);
			commentInteractView.addCommentCallback(this);
			commentInteractLinearLayout.removeAllViews();
			commentInteractLinearLayout.addView(commentInteractView);

			// asyncJson = new AsyncJson(this);
			// asyncJson.addInterFace(this);
			// asyncJson.execute(dataUrl);

			AsyncRefreshJson asyncRefreshJson = new AsyncRefreshJson(this);
			asyncRefreshJson.addInterFace(this);
			asyncRefreshJson.execute(dataUrl);

		}
			break;
		case DOWNLOAD: {
			Intent intent = new Intent(ShowPictureActivity.this,
					DownloadActivity.class);
			intent.putExtra("tabName", "Download");
			ShowPictureActivity.this.startActivity(intent);
		}
			break;
		case SETTING: {
			Intent intent = new Intent(ShowPictureActivity.this,
					SettingActivity.class);
			intent.putExtra("tabName", "Setting");

			ShowPictureActivity.this.startActivity(intent);
		}
			break;
		}
		return true;
	}

	public void addCommentCallback(String url) {
		data_id = ((GalleryThumbModel) data.get(index)).getpId();
		commentInteractView = new CommentInteractView(ShowPictureActivity.this,
				tid, data_id, CommentInteractStyle.photo);
		commentInteractView.addCommentCallback(this);
		commentInteractView.invalidate();

		commentInteractLinearLayout.removeAllViews();
		commentInteractLinearLayout.addView(commentInteractView);
		commentInteractLinearLayout.invalidate();
	}

}
