package com.fmlditital.emp.base;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.fmlditital.emp.R;
import com.fmlditital.emp.app.EMPApp;
import com.fmlditital.emp.async.AsyncImage;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.interf.BottomMusicCallback;
import com.fmlditital.emp.interf.CommentInteractCallback;
import com.fmlditital.emp.interf.ImageLoadCallback;
import com.fmlditital.emp.interf.JsonInterface;
import com.fmlditital.emp.manage.MusicManage;
import com.fmlditital.emp.manage.NewsManage;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.MusicModel;
import com.fmlditital.emp.model.NavigationModel;
import com.fmlditital.emp.music.MusicPlayer;
import com.fmlditital.emp.tool.NetWorkState;
import com.fmlditital.emp.tool.Tools;

/**
 * @author lftx
 * 
 */
public class DefaultActivity extends Activity implements JsonInterface,
		CommentInteractCallback, ImageLoadCallback, BottomMusicCallback {

	private EMPApp app = EMPApp.getSingleton();

	// music data
	private List<BaseModel> musicData = app.getBottomDataDataMusic();

	private static int screenWidth = 0, screenHeight = 0;

	protected static boolean hasNews = false, hasMusic = false;

	protected LinearLayout bodyLayout = null, touchBarLayout = null;
	public static LinearLayout bottomBarLayout = null;

	protected ImageView touchShareImageView = null;
	protected TextView touchTitleTextView = null;

	private UIConfig uiConfig = null;

	// for background
	private AsyncImage asyncImage = null;
	private String backgroundUrl = null;
	protected static boolean loadtheImage = true;
	private static Drawable backgroundDrawable = null;

	// for bottom shortcut ui
	protected static ImageView newsImageView = null,
			musicPlayPauseImageView = null, musicnextImageView = null,
			musicImageView = null;
	protected LinearLayout musicLinearLayout = null, newsLinearLayout = null;
	public static SeekBar seekBar = null;
	public static TextView newsTitleView1 = null, newsTitleView2 = null,
			newsTitleView3 = null, musicTitleView = null;
	protected static ViewFlipper mNewsFlipper = null;

	protected static boolean initiBottomShortCutData = true;
	// private boolean hasAddBottomShortCut = false;
	public static boolean playState = false;
	public static boolean isOpenPlay = false;
	protected static boolean theFirstOpenMusic = true;
	// protected static boolean theFirstMusicIcon = true;

	private MusicManage musicManage = null;
	private NewsManage newsManage = null;

	private Map<String, String> sawMap = app.getSawMap();

	// for music insert
	private String mSawInsertUrl = null, mTid = null, mData_id = null;
	private int musicIndex = 0;

	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.defaultactivity);

		viewId();

		// bottomBarLayout.setBackgroundColor(Color.parseColor(uiConfig
		// .getTopbar_background_color()));
		// bodyLayout.setBackgroundColor(Color.parseColor(uiConfig
		// .getApp_background_color()));
		// touchBarLayout.setBackgroundColor(Color.parseColor(uiConfig
		// .getTopbar_background_color()));
		// touchBarLayout.setLayoutParams(new LinearLayout.LayoutParams(
		// LinearLayout.LayoutParams.FILL_PARENT, Tools.dip2px(this, 35)));

		screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();

		screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();

		backgroundUrl = uiConfig.getApp_background_image();
		loadtheImage = false;

	}

	private void viewId() {
		bottomBarLayout = (LinearLayout) findViewById(R.id.defalt_linearLayout_bottombar);
		bodyLayout = (LinearLayout) findViewById(R.id.defalt_linearLayout_body);

		touchBarLayout = (LinearLayout) findViewById(R.id.defalt_linearLayout_touchbar);
		touchBarLayout.setVisibility(View.GONE);

		LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bottomBarLayout
				.getLayoutParams();
		linearParams.height = Tools.dip2px(this, 35);
		bottomBarLayout.setLayoutParams(linearParams);
		uiConfig = Confi.getInstance().getuIConfig();

		touchShareImageView = (ImageView) findViewById(R.id.touchview_imageView);
		touchTitleTextView = (TextView) findViewById(R.id.touchview_textView);
		touchTitleTextView.setVisibility(View.INVISIBLE);
		touchTitleTextView.setTextSize(21);
		touchTitleTextView.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));
		// touchTitleTextView.setTextColor(Color.parseColor(uiConfig
		// .getTopbar_text_color()));

		bottomBarLayout.setBackgroundColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));
		// bottomBarLayout.setBackgroundColor(Color.RED);
		bottomBarLayout.setVisibility(View.GONE);
		bodyLayout.setBackgroundColor(Color.parseColor(uiConfig
				.getApp_background_color()));
		touchBarLayout.setBackgroundColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));
		touchBarLayout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, Tools.dip2px(this, 35)));
		// musicImageView = (ImageView)
		// findViewById(R.id.bottomshortcut_imageView_music);
		// musicnextImageView = (ImageView)
		// findViewById(R.id.bottomshortcut_imageView_musicnext);
		// musicPlayPauseImageView = (ImageView)
		// findViewById(R.id.bottomshortcut_imageView_playpause);
		// newsImageView = (ImageView)
		// findViewById(R.id.bottomshortcut_imageView_news);
		//
		// musicImageView.setVisibility(View.VISIBLE);
		// newsImageView.setVisibility(View.VISIBLE);
		// musicPlayPauseImageView.setVisibility(View.GONE);
		//
		//
		// mNewsFlipper = (ViewFlipper)
		// findViewById(R.id.bottomshortcut_news_flipper);
		// //
		// musicLinearLayout = (LinearLayout)
		// findViewById(R.id.bottomshortcut_linearLayout_music);
		// newsLinearLayout = (LinearLayout)
		// findViewById(R.id.bottomshortcut_linearLayout_news);
		//
		// musicLinearLayout.setVisibility(View.GONE);
		// newsLinearLayout.setVisibility(View.GONE);

		// seekBar = (SeekBar) findViewById(R.id.bottomshortcut_mseekBar);
		// seekBar.setOnSeekBarChangeListener(new SeekBarChangeEvent());

		// newsTitleView1 = (TextView)
		// findViewById(R.id.bottomshortcut_newstitle1);
		// newsTitleView2 = (TextView)
		// findViewById(R.id.bottomshortcut_newstitle2);
		// newsTitleView3 = (TextView)
		// findViewById(R.id.bottomshortcut_newstitle3);
		// musicTitleView = (TextView)
		// findViewById(R.id.bottomshortcut_musictitle);
		// newsTitleView1.setTextColor(Color.parseColor(uiConfig
		// .getTopbar_text_color()));
		// newsTitleView2.setTextColor(Color.parseColor(uiConfig
		// .getTopbar_text_color()));
		// newsTitleView3.setTextColor(Color.parseColor(uiConfig
		// .getTopbar_text_color()));
		// musicTitleView.setTextColor(Color.parseColor(uiConfig
		// .getTopbar_text_color()));
	}

	protected void onResume() {
		super.onResume();

		musicImageView = (ImageView) findViewById(R.id.bottomshortcut_imageView_music);
		// bottomBarLayout.setVisibility(View.GONE);
		musicnextImageView = (ImageView) findViewById(R.id.bottomshortcut_imageView_musicnext);
		musicPlayPauseImageView = (ImageView) findViewById(R.id.bottomshortcut_imageView_playpause);
		newsImageView = (ImageView) findViewById(R.id.bottomshortcut_imageView_news);

		mNewsFlipper = (ViewFlipper) findViewById(R.id.bottomshortcut_news_flipper);
		//
		musicLinearLayout = (LinearLayout) findViewById(R.id.bottomshortcut_linearLayout_music);
		newsLinearLayout = (LinearLayout) findViewById(R.id.bottomshortcut_linearLayout_news);
		musicImageView.setOnClickListener(bottomShortCutListener);
		musicnextImageView.setOnClickListener(bottomShortCutListener);
		newsImageView.setOnClickListener(bottomShortCutListener);
		musicPlayPauseImageView.setOnClickListener(bottomShortCutListener);

		// mNewsFlipper = (ViewFlipper)
		// findViewById(R.id.bottomshortcut_news_flipper);

		// musicLinearLayout = (LinearLayout)
		// findViewById(R.id.bottomshortcut_linearLayout_music);
		// newsLinearLayout = (LinearLayout)
		// findViewById(R.id.bottomshortcut_linearLayout_news);

		// if(theFirstMusicIcon==true) {
		// musicImageView.setVisibility(View.GONE);
		// }

		newsTitleView1 = (TextView) findViewById(R.id.bottomshortcut_newstitle1);
		newsTitleView2 = (TextView) findViewById(R.id.bottomshortcut_newstitle2);
		newsTitleView3 = (TextView) findViewById(R.id.bottomshortcut_newstitle3);
		musicTitleView = (TextView) findViewById(R.id.bottomshortcut_musictitle);
		newsTitleView1.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));
		newsTitleView2.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));
		newsTitleView3.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));
		musicTitleView.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));

		seekBar = (SeekBar) findViewById(R.id.bottomshortcut_mseekBar);
		seekBar.setOnSeekBarChangeListener(new SeekBarChangeEvent());

		bottomBarLayout.setVisibility(View.GONE);

		// if (theFirstOpenMusic == true) {
		// musicImageView.setVisibility(View.VISIBLE);
		// newsImageView.setVisibility(View.VISIBLE);
		//
		// musicImageView.invalidate();
		// musicPlayPauseImageView.invalidate();
		// musicPlayPauseImageView.setVisibility(View.GONE);
		// musicLinearLayout.setVisibility(View.GONE);
		// newsLinearLayout.setVisibility(View.GONE);
		// } else {
		// musicImageView.setVisibility(View.GONE);
		// musicPlayPauseImageView.setVisibility(View.VISIBLE);
		// musicImageView.invalidate();
		// musicPlayPauseImageView.invalidate();
		// if (playState == true) {
		// musicPlayPauseImageView.setImageResource(R.drawable.stop_icon);
		// } else {
		// musicPlayPauseImageView.setImageResource(R.drawable.play_icon);
		// }
		// if (isOpenPlay == true) {
		// musicLinearLayout.setVisibility(View.VISIBLE);
		// newsLinearLayout.setVisibility(View.GONE);
		// } else {
		// musicLinearLayout.setVisibility(View.GONE);
		// newsLinearLayout.setVisibility(View.VISIBLE);
		// }
		// }

		if (backgroundDrawable != null) {
			bodyLayout.setBackgroundDrawable(backgroundDrawable);
		} else {
			asyncImage = new AsyncImage();
			asyncImage.addImageLoadCallback(this);
			asyncImage.execute(backgroundUrl);
		}

		if (hasNews == false && hasMusic == false) {
			bottomBarLayout.setVisibility(View.GONE);
		} else {
			bottomBarLayout.setVisibility(View.VISIBLE);

			if (theFirstOpenMusic == true) {
				musicImageView.setVisibility(View.VISIBLE);
				newsImageView.setVisibility(View.VISIBLE);

				musicImageView.invalidate();
				musicPlayPauseImageView.invalidate();
				musicPlayPauseImageView.setVisibility(View.GONE);
				// musicLinearLayout.setVisibility(View.GONE);
				// newsLinearLayout.setVisibility(View.GONE);
			} else {
				musicImageView.setVisibility(View.GONE);
				musicPlayPauseImageView.setVisibility(View.VISIBLE);
				musicImageView.invalidate();
				musicPlayPauseImageView.invalidate();
				if (playState == true) {
					musicPlayPauseImageView
							.setImageResource(R.drawable.stop_icon);
				} else {
					musicPlayPauseImageView
							.setImageResource(R.drawable.play_icon);
				}
				if (isOpenPlay == true) {
					musicLinearLayout.setVisibility(View.VISIBLE);
					newsLinearLayout.setVisibility(View.GONE);
				} else {
					musicLinearLayout.setVisibility(View.GONE);
					newsLinearLayout.setVisibility(View.VISIBLE);
				}
			}
		}

		if (hasNews == true) {

			newsImageView.setVisibility(View.VISIBLE);
			// add news
			mNewsFlipper.startFlipping();
			mNewsFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_up_in));
			mNewsFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_up_out));

			newsManage = NewsManage.getNewsManage(this);
			newsManage.updateNewsTextView(newsTitleView1, newsTitleView2,
					newsTitleView3);
		} else {
			newsImageView.setVisibility(View.GONE);
		}
		System.out.println(theFirstOpenMusic
				+ "<<<<<<<<< DefaultActivity ><>>>>>>>>：：：" + isOpenPlay
				+ ":::::" + newsLinearLayout.getVisibility());
		// if (hasMusic == true) {
		// musicImageView.setVisibility(View.VISIBLE);
		// }else {
		// musicImageView.setVisibility(View.GONE);
		// }

	}

	public void callJoinSuccess(String url, JSONObject json) {

		if (url.equals(mSawInsertUrl)) {
			try {
				if (json.getInt("status") == 1) {
					JSONObject jSONObject = json.getJSONObject("data");
					bottomMusicCallback(MusicPlayer.getMusicUri());
				}
			} catch (JSONException e) {
				callJoinFailure(url, e.getMessage().toString());
			}
		}
	}

	public void callJoinFailure(String url, String failMessage) {
		Log.v("url", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>json url:  failMessage"
				+ url + failMessage);

	}

	public void actionCallback() {

	}

	public void editCallback() {
		// TODO Auto-generated method stub

	}

	public void showDetailOrCommentList(String title) {
		// TODO Auto-generated method stub

	}

	public static int getScreenWidth() {

		return screenWidth;
	}

	public static int getScreenHeight() {
		return screenHeight;
	}

	protected void onPause() {
		super.onPause();

	}

	protected BottomShortCutListener bottomShortCutListener = new BottomShortCutListener();

	protected class BottomShortCutListener implements OnClickListener {

		public void onClick(View v) {

			int id = v.getId();
			System.out.println("\\\\\\\\BottomClick\\\\\\\\===" + id);
			switch (id) {
			case R.id.bottomshortcut_imageView_music: {

				if (NetWorkState.isNetworkAvailable(DefaultActivity.this)) {
					if (musicData.size() > 0) {
						theFirstOpenMusic = false;
						playState = true;
						isOpenPlay = true;
						musicImageView.setVisibility(View.GONE);
						musicPlayPauseImageView.setVisibility(View.VISIBLE);
						musicPlayPauseImageView
								.setImageResource(R.drawable.stop_icon);
						musicLinearLayout.setVisibility(View.VISIBLE);
						musicPlayPauseImageView.setVisibility(View.VISIBLE);
						newsLinearLayout.setVisibility(View.GONE);

						musicPlayPauseImageView
								.setImageResource(R.drawable.stop_icon);
						musicManage = MusicManage.getMusicManage(musicData);
						musicManage.playMusic(0);

						if (MusicPlayer.data != null
								|| MusicPlayer.data.size() > 0) {
							for (NavigationModel model : app.getTabList()) {
								if (model.getFunction().equals("music")) {
									mTid = model.getTab_id();
								}
							}
							MusicModel model = (MusicModel) MusicPlayer.data
									.get(0);

							mData_id = model.getId();
							if (sawMap.get(mTid + mData_id) == null
									|| !sawMap.get(mTid + mData_id).equals(
											mTid + mData_id)) {
								mSawInsertUrl = Confi.getInstance()
										.getInsertAccumulativeCountsApiUrl(
												mTid, mData_id, "1");

								sawMap.put(mTid + mData_id, mTid + mData_id);
								AsyncJson sawAsyncJson = new AsyncJson(
										DefaultActivity.this);
								sawAsyncJson.addInterFace(DefaultActivity.this);
								sawAsyncJson.execute(mSawInsertUrl);
							}
						}
					}
				} else {
					Tools.showBadNetwork(DefaultActivity.this);
				}
			}
				break;

			case R.id.bottomshortcut_imageView_playpause: {
				// if (NetWorkState.isNetworkAvailable(DefaultActivity.this)) {
				musicLinearLayout.setVisibility(View.VISIBLE);
				musicPlayPauseImageView.setVisibility(View.VISIBLE);
				newsLinearLayout.setVisibility(View.GONE);
				if (playState == true) {
					musicPlayPauseImageView
							.setImageResource(R.drawable.play_icon);
					playState = false;
					musicManage = MusicManage.getMusicManage(musicData);
					musicManage.pauseMusic();

				} else {

					musicManage = MusicManage.getMusicManage(musicData);
					musicManage.replayMusic();
					musicPlayPauseImageView
							.setImageResource(R.drawable.stop_icon);
					playState = true;

				}
				// } else {
				// Tools.showBadNetwork(DefaultActivity.this);
				// }
			}
				break;

			case R.id.bottomshortcut_imageView_musicnext: {
				if (NetWorkState.isNetworkAvailable(DefaultActivity.this)) {
					musicPlayPauseImageView
							.setImageResource(R.drawable.stop_icon);
					musicManage = MusicManage.getMusicManage(musicData);
					musicManage.nextMusic();

					if (MusicPlayer.data != null || MusicPlayer.data.size() > 0) {
						for (NavigationModel model : app.getTabList()) {
							if (model.getFunction().equals("music")) {
								mTid = model.getTab_id();
							}
						}

						musicIndex++;
						musicIndex = MusicPlayer.reviceIndex(musicIndex);

						MusicModel model = (MusicModel) MusicPlayer.data
								.get(musicIndex);

						mData_id = model.getId();
						if (sawMap.get(mTid + mData_id) == null
								|| !sawMap.get(mTid + mData_id).equals(
										mTid + mData_id)) {
							mSawInsertUrl = Confi.getInstance()
									.getInsertAccumulativeCountsApiUrl(mTid,
											mData_id, "1");

							sawMap.put(mTid + mData_id, mTid + mData_id);
							AsyncJson sawAsyncJson = new AsyncJson(
									DefaultActivity.this);
							sawAsyncJson.addInterFace(DefaultActivity.this);
							sawAsyncJson.execute(mSawInsertUrl);
						}
					}
				} else {
					Tools.showBadNetwork(DefaultActivity.this);
				}

			}
				break;

			case R.id.bottomshortcut_imageView_news: {
				System.out.println(isOpenPlay
						+ "=============music============"
						+ musicLinearLayout.getVisibility());
				if (isOpenPlay == true) {
					musicLinearLayout.setVisibility(View.GONE);
					newsLinearLayout.setVisibility(View.VISIBLE);
					isOpenPlay = false;
				} else {
					musicLinearLayout.setVisibility(View.VISIBLE);
					newsLinearLayout.setVisibility(View.GONE);
					isOpenPlay = true;
				}
			}
				break;
			}

		}
	}

	class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
		int progress;

		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			this.progress = progress;
			seekBar.getMax();
		}

		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		public void onStopTrackingTouch(SeekBar seekBar) {
			musicManage = MusicManage.getMusicManage(musicData);
			musicManage.seek_to(progress);

		}
	}

	public void callImageSuccess(String url, Drawable drawable) {
		if (url.equals(backgroundUrl)) {
			if (drawable != null) {
				backgroundDrawable = drawable;
				bodyLayout.setBackgroundDrawable(drawable);
			}
		}
	}

	public void callImageFailure(String url) {

	}

	public void callJoinMore(String url, JSONObject json) {

	}

	public void bottomMusicCallback(String url) {

	}
}
