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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
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
import com.fmlditital.emp.gallery.ShowPictureActivity;
import com.fmlditital.emp.model.EventModel;
import com.fmlditital.emp.preferences.SettingActivity;
import com.fmlditital.emp.share.ShareManage;
import com.fmlditital.emp.share.info.ShareInfo;
import com.fmlditital.emp.share.info.ShareText;
import com.fmlditital.emp.tool.Tools;
import com.fmlditital.emp.view.CommentInteractView;

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

	private String eTitle;
	private SharedPreferences sharedPreferences = null;

	private static final int REFRESH = Menu.FIRST;
	private static final int DOWNLOAD = Menu.FIRST + 1;
	private static final int SETTING = Menu.FIRST + 2;

	private UIConfig uiConfig = Confi.getInstance().getuIConfig();

//	private LinearLayout mmLinear;
	// private LinearLayout mainLinear;
	private Button buttonAttend, buttonNoAttend;
	private CommentInteractView commentInteractView = null;

	private RelativeLayout mainLayout;
    /**隐藏菜单**/
	private SlidingDrawer menu;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// touchBarLayout.setVisibility(View.GONE);
		type = this.getIntent().getExtras().getString("type");
		if (type.equals("upcoming")) {
			data = app.getDataEvent();
		} else if (type.equals("past")) {
			data = app.getDataEventPast();
		}
		System.out.println("======Event Data=====" + data.size());
		data_name = ((EventModel) data.get(index)).getTitle();

//		menu=new SlidingDrawer(this);
		
		touchTitleTextView.setVisibility(View.VISIBLE);
		// touchTitleTextView.setText((index + 1) + "/" + data.size());
		touchTitleTextView.setText(eTitle);
		imageView = new ImageView(this);
		LayoutParams param = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		param.height = Tools.dip2px(this, 182);
		// param.gravity = Gravity.CENTER;
		// imageView.setAdjustViewBounds(true);
		imageView.setScaleType(ScaleType.CENTER_CROP);
		bodyLayout.addView(imageView, param);

//		mainLayout = new RelativeLayout(this);
		
		LayoutInflater factory = getLayoutInflater();
		View view = factory.inflate(R.layout.event_detail, null);
		mainLayout= (RelativeLayout)view.findViewById(R.id.mainlayout);
		System.out.println("mainLaout ======  "+mainLayout);
		
		
		menu = (SlidingDrawer)view.findViewById(R.id.drawer);
//		mainLayout.setLayoutParams(new RelativeLayout.LayoutParams(
//				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		//
		// //-------------Event Content
		// LinearLayout[] listLayout=new LinearLayout[data.size()];
		// // listLayout.setOrientation(LinearLayout.VERTICAL);
		// listViews = new ArrayList<View>();
		// mWebViews = new WebView[data.size()];
		// for (int i = 0; i < data.size(); i++) {
		// mWebViews[i] = new WebView(this);
		// mWebViews[i].setLayoutParams(new LinearLayout.LayoutParams(
		// LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		// // mWebViews[i].setFocusable(false);
		// // mWebViews[i].setFocusableInTouchMode(false);
		// // mWebViews[i].getSettings().setJavaScriptEnabled(true);
		// // mWebViews[i].getSettings().setUseWideViewPort(true);
		//
		// mWebViews[i].setBackgroundColor(0);
		//
		// WebSettings webSettings = mWebViews[i].getSettings();
		// webSettings.setJavaScriptEnabled(false);
		// webSettings.setSupportZoom(false);
		// webSettings.setBuiltInZoomControls(false);
		//
		// listLayout[i]=new LinearLayout(this);
		// listLayout[i].setOrientation(LinearLayout.VERTICAL);
		// listLayout[i].addView(mWebViews[i]);
		// // listViews.add(mWebViews[i]);
		// listViews.add(listLayout[i]);
		// }
		// mPager = new ViewPager(this);
		// LinearLayout.LayoutParams LL_P = new LinearLayout.LayoutParams(
		// LayoutParams.FILL_PARENT, DefaultActivity.getScreenHeight() / 3);
		//
		// mPager.setLayoutParams(LL_P);
		// // Log.d("::::"+mPager.getLayoutParams().height,
		// // "Fill_parent :::: "+(new ViewPagerAdapter(listViews)).toString());
		// mPager.setAdapter(new ViewPagerAdapter(listViews));
		// mPager.setCurrentItem(index);
		// mPager.setOnPageChangeListener(this);
		//
		// // mainLinear.addView(mPager);
		// bodyLayout.addView(mPager);

		// ------------Event attend button
//		mmLinear = new LinearLayout(this);
		// mmLinear=(LinearLayout)mainLinear.findViewById(R.id.widget35);
//		mmLinear.setOrientation(LinearLayout.HORIZONTAL);
//		mmLinear.setId(1);
		// mmLinear.setLayoutParams(new LinearLayout.LayoutParams(
		// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//		buttonAttend = new Button(this);
//		buttonAttend.setBackgroundDrawable(Tools.addStateDrawable(this,
//				R.drawable.button2, R.drawable.button1, R.drawable.button1));
//		buttonAttend.setId(1);
		// buttonAttend.set
//		buttonNoAttend = new Button(this);
//		buttonNoAttend.setBackgroundDrawable(Tools.addStateDrawable(this,
//				R.drawable.button2, R.drawable.button1, R.drawable.button1));
//		buttonNoAttend.setId(2);
		// buttonAttend.setBackgroundColor(Color.BLACK);
		// buttonNoAttend.setBackgroundColor(Color.BLACK);
		// buttonNoAttend.setPadding(0, 0, 50, 50);
//		buttonAttend.setTextColor(Color.parseColor(uiConfig
//				.getTopbar_text_color()));
//		buttonNoAttend.setTextColor(Color.parseColor(uiConfig
//				.getTopbar_text_color()));
//		buttonAttend.setText("参加");
//		buttonNoAttend.setText("可能参加");
//		buttonAttend.setTextSize(10);
//		buttonNoAttend.setTextSize(10);
//		LinearLayout.LayoutParams LP_FF = new LinearLayout.LayoutParams(
//				DefaultActivity.getScreenWidth() / 5, LayoutParams.WRAP_CONTENT);
		// LP_FF.gravity=Gravity.RIGHT;
//		buttonAttend.setLayoutParams(LP_FF);
//		LinearLayout.LayoutParams LP_RR = new LinearLayout.LayoutParams(
//				DefaultActivity.getScreenWidth() / 5, LayoutParams.WRAP_CONTENT);
//		LP_RR.leftMargin = 10;
//		buttonNoAttend.setLayoutParams(LP_RR);
		
		buttonAttend=(Button)view.findViewById(R.id.attend);
		buttonAttend.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				insertJoin(insertJoin[0]);
				// alertDialog.cancel();
			}
		});
        buttonNoAttend=(Button)view.findViewById(R.id.mayattend);
		buttonNoAttend.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				insertJoin(insertJoin[1]);
				// alertDialog.cancel();
			}
		});
		// Log.d("::::"+mPager.getLayoutParams().,
		// "Fill_parent :::: "+LL_P.height);

//		mmLinear.addView(buttonAttend);
//		mmLinear.addView(buttonNoAttend);

//		mmLinear.setGravity(Gravity.RIGHT);

		// -------------Event Content
		// LinearLayout[] listLayout=new LinearLayout[data.size()];
		// listLayout.setOrientation(LinearLayout.VERTICAL);
		listViews = new ArrayList<View>();
		mWebViews = new WebView[data.size()];
		for (int i = 0; i < data.size(); i++) {
			mWebViews[i] = new WebView(this);
			mWebViews[i].setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			// mWebViews[i].setFocusable(false);
			// mWebViews[i].setFocusableInTouchMode(false);
			// mWebViews[i].getSettings().setJavaScriptEnabled(true);
			// mWebViews[i].getSettings().setUseWideViewPort(true);

			mWebViews[i].setBackgroundColor(0);

			WebSettings webSettings = mWebViews[i].getSettings();
			webSettings.setJavaScriptEnabled(false);
			webSettings.setSupportZoom(false);
			webSettings.setBuiltInZoomControls(false);

			// listLayout[i]=new LinearLayout(this);
			// listLayout[i].setOrientation(LinearLayout.VERTICAL);
			// listLayout[i].addView(mWebViews[i]);
			// Log.d("", "button---------------create::"+mmLinear);
			// if(i>0)
			// listLayout[i-1].removeAllViews();
			// listLayout[i].addView(mmLinear);
			listViews.add(mWebViews[i]);
			// listViews.add(listLayout[i]);

		}
//		mPager = new ViewPager(this);
		
		mPager=(ViewPager)view.findViewById(R.id.viewpager);
//		LinearLayout.LayoutParams LL_P = new LinearLayout.LayoutParams(
//				LayoutParams.FILL_PARENT, DefaultActivity.getScreenHeight() / 3);
//
//		mPager.setLayoutParams(LL_P);
		Log.d("::height::" + buttonAttend.getMeasuredHeight(),
				"Fill_parent :::: "
						+ (new ViewPagerAdapter(listViews)).toString());
		mPager.setAdapter(new ViewPagerAdapter(listViews));
		mPager.setCurrentItem(index);
		mPager.setOnPageChangeListener(this);

		// mainLinear.addView(mPager);
		// mPager.setBackgroundColor(Color.GREEN);
		// bodyLayout.addView(mPager);

		LinearLayout.LayoutParams mm = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		// mmLinear.setBackgroundColor(Color.BLUE);
		RelativeLayout.LayoutParams mLP = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, Tools.dip2px(this, 40));
		mLP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//		mainLayout.addView(mmLinear, mLP);

		RelativeLayout.LayoutParams mmLP = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		mmLP.addRule(RelativeLayout.ABOVE, 1);
//		mainLayout.addView(mPager, mmLP);

		bodyLayout.addView(mainLayout, mm);
		// bodyLayout.setBackgroundColor(Color.YELLOW);
		// bodyLayout.addView(buttonNoAttend);

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

	protected void onResume() {
		super.onResume();
		touchBarLayout.setVisibility(View.VISIBLE);
	}

	private void showPage() {
		mPager.setCurrentItem(index);
		// touchTitleTextView.setText((index + 1) + "/" + data.size());
		touchTitleTextView.setText(eTitle);
		// mainLinear.removeAllViews();
		// mainLinear.addView(mPager);
		// mainLinear.invalidate();

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

	public void onClick(View v) {
		if (v == touchShareImageView) {
			showShareDialog(index);
		}

	}

	public void joinCount() {
		// shareContext[0] = "Share to Sina WeiBo";
		shareContext[0] = "分享到微博";
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
			// txtToast.setText("Sorry. You have made a reply to this event.");
			txtToast.setText("对不起,你已经选过了.");
			txtToast.setTextColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			myToast.show();
			// Toast.makeText(this,
			// "Sorry. You have made a reply to this event.",
			// Toast.LENGTH_LONG).show();
		}
	}

	private void showShareDialog(final int index) {

		if (type.equals("upcoming")) {
			final AlertDialog alertDialog = new AlertDialog.Builder(
					EventDetailActivity.this).create();
			alertDialog.show();
			alertDialog.setContentView(R.layout.eventsdialog);
			LinearLayout eventBg = (LinearLayout) alertDialog
					.findViewById(R.id.eventDialogBg);
			eventBg.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_background_color()));
			eventBg.getBackground().setAlpha(150);

			// TextView txtLineOne = (TextView) alertDialog
			// .findViewById(R.id.event_textView_txtLineOne);
			// txtLineOne.setBackgroundColor(Color.parseColor(uiConfig
			// .getTopbar_text_color()));
			// TextView txtLineTwo = (TextView) alertDialog
			// .findViewById(R.id.event_textView_txtLineTwo);
			// txtLineTwo.setBackgroundColor(Color.parseColor(uiConfig
			// .getTopbar_text_color()));
			// TextView txtLineThree = (TextView) alertDialog
			// .findViewById(R.id.event_textView_txtLineThree);
			// txtLineThree.setBackgroundColor(Color.parseColor(uiConfig
			// .getTopbar_text_color()));

			TextView txtShare = (TextView) alertDialog
					.findViewById(R.id.event_textView_txtShare);
			txtShare.setTextColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			txtShare.setText(shareContext[0]);
			txtShare.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					shareTo(index);
					alertDialog.cancel();
				}
			});
			// TextView txtAttending = (TextView) alertDialog
			// .findViewById(R.id.event_textView_txtAttending);
			// txtAttending.setText(shareContext[1]);
			// txtAttending.setTextColor(Color.parseColor(uiConfig
			// .getTopbar_text_color()));
			// txtAttending.setOnClickListener(new OnClickListener() {
			//
			// public void onClick(View v) {
			// insertJoin(insertJoin[0]);
			// alertDialog.cancel();
			// }
			// });
			// Might AttendingæŒ‰é’®
			// TextView txtMight = (TextView) alertDialog
			// .findViewById(R.id.event_textView_txtMight);
			// txtMight.setText(shareContext[2]);
			// txtMight.setTextColor(Color.parseColor(uiConfig
			// .getTopbar_text_color()));
			// txtMight.setOnClickListener(new OnClickListener() {
			//
			// public void onClick(View v) {
			// insertJoin(insertJoin[1]);
			// alertDialog.cancel();
			// }
			// });
			// No AttendingæŒ‰é’®
			// TextView txtNo = (TextView) alertDialog
			// .findViewById(R.id.event_textView_txtNo);
			// txtNo.setText(shareContext[3]);
			// txtNo.setTextColor(Color.parseColor(uiConfig.getTopbar_text_color()));
			// txtNo.setOnClickListener(new OnClickListener() {
			//
			// public void onClick(View v) {
			//
			// alertDialog.cancel();
			// }
			// });

		} else if (type.equals("past")) {
			final AlertDialog alertDialog = new AlertDialog.Builder(
					EventDetailActivity.this).create();
			alertDialog.show();
			alertDialog.setContentView(R.layout.eventsdialog);
			LinearLayout eventBg = (LinearLayout) alertDialog
					.findViewById(R.id.eventDialogBg);
			eventBg.setBackgroundColor(Color.parseColor(uiConfig
					.getTopbar_background_color()));
			eventBg.getBackground().setAlpha(150);
			// TextView txtLineOne = (TextView) alertDialog
			// .findViewById(R.id.event_textView_txtLineOne);
			// txtLineOne.setVisibility(View.GONE);
			//
			// TextView txtLineTwo = (TextView) alertDialog
			// .findViewById(R.id.event_textView_txtLineTwo);
			// txtLineTwo.setBackgroundColor(Color.parseColor(uiConfig
			// .getTopbar_text_color()));
			// TextView txtLineThree = (TextView) alertDialog
			// .findViewById(R.id.event_textView_txtLineThree);
			// txtLineThree.setBackgroundColor(Color.parseColor(uiConfig
			// .getTopbar_text_color()));
			// èŽ·å�–ShareæŒ‰é’®
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
			// TextView txtAttending = (TextView) alertDialog
			// .findViewById(R.id.event_textView_txtAttending);
			// txtAttending.setText(paseContext[0]);
			// txtAttending.setTextColor(Color.GRAY);
			// txtAttending.setOnClickListener(new OnClickListener() {
			//
			// public void onClick(View v) {
			// // insertJoin(insertJoin[0]);
			// alertDialog.cancel();
			// }
			// });
			// TextView txtMight = (TextView) alertDialog
			// .findViewById(R.id.event_textView_txtMight);
			// txtMight.setText(paseContext[1]);
			// txtMight.setTextColor(Color.GRAY);
			// txtMight.setOnClickListener(new OnClickListener() {
			//
			// public void onClick(View v) {
			// // insertJoin(insertJoin[1]);
			// alertDialog.cancel();
			// }
			// });
			// TextView txtNo = (TextView) alertDialog
			// .findViewById(R.id.event_textView_txtNo);
			// txtNo.setText(paseContext[2]);
			// txtNo.setTextColor(Color.GRAY);
			// txtNo.setOnClickListener(new OnClickListener() {
			//
			// public void onClick(View v) {
			//
			// alertDialog.cancel();
			// }
			// });

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
			// "é€�æ˜Žå¯¹è¯�æ¡†").setPositiveButton("ç¡®å®š", null).create();
			// Window window = alertDialog.getWindow();
			// WindowManager.LayoutParams lp = window.getAttributes();
			// // è®¾ç½®é€�æ˜Žåº¦ä¸º0.3
			// // lp.c
			// lp.alpha = 0.3f;
			// window.setAttributes(lp);
			// alertDialog.show();

		}
	}

	private void shareTo(int to) {
		ShareInfo shareInfo = new ShareText();
		shareInfo.constructContext(((EventModel) data.get(to)).getTitle()
				+ " , " + ((EventModel) data.get(to)).getBegintime() + " "
				+ ((EventModel) data.get(to)).getLocation() + " , 详情请联系以下邮箱 "
				+ ((EventModel) data.get(to)).getEmail());
		// shareInfo.constructContext(((EventModel) data.get(to)).getTitle()
		// + " on " + ((EventModel) data.get(to)).getBegintime() + " "
		// + ((EventModel) data.get(to)).getLocation()
		// + " , please email " + ((EventModel) data.get(to)).getEmail()
		// + " for details.");
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
				// txtToast.setText(R.string.join_success);
				txtToast.setText("参加成功");
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

			System.out.println("getHtml :::: " + sb);
			sb.replace(sb.indexOf("<!--color-->"), sb.indexOf("<!--color-->")
					+ "<!--color-->".length(), Confi.getInstance()
					.getuIConfig().getTopbar_text_color());
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

			// System.out.println("getHtml ::>>>>>:: "+sb);

			result = sb.toString().trim();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

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
				eTitle = jsonObject2.getString("event_title");
				touchTitleTextView.setText(eTitle);
				mWebViews[index].loadDataWithBaseURL(null, htmlDetail,
						"text/html", "utf-8", null);
			} catch (JSONException e) {
				// e.printStackTrace();
			}
			mWebViews[index].invalidate();
		}

	}

	public void callJoinFailure(String url, String failMessage) {
		super.callJoinFailure(url, failMessage);
	}

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
			// intent.putExtra("tabName", "Download");
			intent.putExtra("tabName", "下载");
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
