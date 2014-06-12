package com.fmlditital.emp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fmlditital.emp.app.EMPApp;
import com.fmlditital.emp.async.AsyncAppImage;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.interf.ImageLoadCallback;
import com.fmlditital.emp.interf.JsonInterface;
import com.fmlditital.emp.manage.ImageSaveManage;
import com.fmlditital.emp.manage.ImageSaveManage.ImageSaveDir;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.model.NavigationModel;
import com.fmlditital.emp.tool.NetWorkState;
import com.fmlditital.emp.tool.SDCardState;

public class LoadingActivity extends Activity implements AnimationListener,
		ImageLoadCallback, JsonInterface {
	private ImageView logo;

	private EMPApp app = EMPApp.getSingleton();
	private List<NavigationModel> tabList = app.getTabList();

	private UIConfig uIConfig = Confi.getInstance().getuIConfig();

	private String tabURL = Confi.getInstance().getAppApiUrl();

	private NavigationModel homeModel = null;

	private AsyncJson asyncJson = null;
	private static int navigationCount = 0;

	// timer check for network
	// Timer timer = new Timer();
	// final Handler handler = new Handler() {
	//
	// public void handleMessage(Message msg) {
	// switch (msg.what) {
	// case 1: {
	// // if(downloadingData!=null &&downloadingData.size()<=0 ) {
	// // if(alertDialog!=null && alertDialog.isShowing())
	// // alertDialog.dismiss();
	// // }
	// }
	// break;
	// }
	// super.handleMessage(msg);
	// }
	//
	// };
	// TimerTask task = new TimerTask() {
	//
	// @Override
	// public void run() {
	// Message message = new Message();
	// message.what = 1;
	// handler.sendMessage(message);
	//
	// }
	// };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.loading);

		logo = (ImageView) findViewById(R.id.loading_image);
		AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.5f);
		// alpha.setAnimationListener(this);
		AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
		alpha.setInterpolator(interpolator);
		alpha.setDuration(35 * 100);

		logo.setAnimation(alpha);
		alpha.startNow();
		if (SDCardState.isSDCardAvailable()) {
			File fileDir = new File(Global.THE_APP_FILE_NAME);
			if (!fileDir.exists())
				fileDir.mkdir();
		}

	
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (NetWorkState.isNetworkAvailable(this)) { // load form NetWork

			asyncJson = new AsyncJson(this);
			asyncJson.addInterFace(this);
			asyncJson.execute(tabURL);
		} else if (SDCardState.isSDCardAvailable()) { // load form sdCard

			GetFromSDAysnc getFromSDAysnc = new GetFromSDAysnc();
			getFromSDAysnc.execute();
		}
	}

	@Override
	public void onAnimationEnd(Animation arg0) {
	}

	@Override
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub
	}

	// save navigation data
	private void serialization() {

		navigationCount = tabList.size();
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {

			String foldername = Global.THE_APP_FILE_NAME + File.separator
					+ ".configuration";
			File folder = new File(foldername);
			if (folder == null || !folder.exists()) {
				folder.mkdir();
			}

			File targetFile = new File(foldername + File.separator
					+ "configuration");
			if (targetFile.exists())
				targetFile.delete();

			FileOutputStream fs = null;
			ObjectOutputStream os = null;
			try {
				fs = new FileOutputStream(targetFile);

				os = new ObjectOutputStream(fs);
				os.writeObject(Confi.getInstance().getAPP_Type());
				os.writeInt(navigationCount);
				for (NavigationModel model : tabList) {
					os.writeObject(model);
				}
				os.writeObject(uIConfig);
				os.writeObject(homeModel);

			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (fs != null) {
					try {
						fs.close();
						fs = null;
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				if (os != null) {
					try {
						os.close();
						os = null;
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	// get navigation data
	private boolean deserialization() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			tabList.clear();

			String foldername = Global.THE_APP_FILE_NAME + File.separator
					+ ".configuration";
			File folder = new File(foldername);
			if (folder == null || !folder.exists()) {
				folder.mkdir();
			}
			// else {
			// return false;
			// }

			File targetFile = new File(foldername + File.separator
					+ "configuration");

			FileInputStream fos = null;
			ObjectInputStream ois = null;
			try {
				fos = new FileInputStream(targetFile);
				ois = new ObjectInputStream(fos);

				Confi.getInstance().setAPP_Type((String) ois.readObject());
				navigationCount = ois.readInt();
				for (int i = 0; i < navigationCount; i++) {
					NavigationModel model = (NavigationModel) ois.readObject();
					model.setIcon(ImageSaveManage.getDrawableFromSD(
							model.getIconPath(), ImageSaveDir.appkey));
					tabList.add(model);
				}
				uIConfig = (UIConfig) ois.readObject();
				homeModel = (NavigationModel) ois.readObject();
				Confi.getInstance().setuIConfig(uIConfig);
				EMPApp.getSingleton().setHomeModel(homeModel);
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.close();
						fos = null;
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				if (ois != null) {
					try {
						ois.close();
						ois = null;
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}

			if (uIConfig != null && homeModel != null)
				return true;
			else
				return false;
		} else {
			return false;
		}

	}

	private class GetFromSDAysnc extends AsyncTask<String, Void, Void> {

		private boolean resultGetFromSD = false;

		@Override
		protected Void doInBackground(String... params) {
			resultGetFromSD = deserialization();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (resultGetFromSD == true) {
				LayoutInflater factory = LayoutInflater
						.from(LoadingActivity.this);
				View view = factory.inflate(R.layout.mytoast, null);
				Toast myToast = new Toast(LoadingActivity.this);
				myToast.setView(view);
				LinearLayout myToastBg = (LinearLayout) view
						.findViewById(R.id.myToastBg);
				myToastBg.setBackgroundColor(Color.parseColor(uIConfig
						.getTopbar_background_color()));
				TextView txtToast = (TextView) view
						.findViewById(R.id.myToast_TextView_txtToast);
				txtToast.setText(R.string.bad_network);
				txtToast.setTextColor(Color.parseColor(uIConfig
						.getTopbar_text_color()));
				myToast.show();
				postIntent();
			} else {

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						LoadingActivity.this);

				alertDialog
						// .setTitle("BadNetwork")
						.setMessage(R.string.check_network_message)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										LoadingActivity.this
												.startActivity(new Intent(
														Settings.ACTION_WIRELESS_SETTINGS));
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										LoadingActivity.this.finish();
									}

								})
						// .setCancelable(false)
						.create().show();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fmlditital.emp.interf.JsonInterface#callJoinSuccess(java.lang.String,
	 * org.json.JSONObject) 从服务器获取导航栏的内容
	 */
	@Override
	public void callJoinSuccess(String url, JSONObject json) {
		if (url.equals(tabURL)) {
			try {
				// for tab
				tabList.clear();
				JSONArray datasJSONArray = json.getJSONArray("datas");
				JSONObject jSONObject = datasJSONArray.optJSONObject(0);

				// cancel the share name
				// Confi.SnsAppName = jSONObject.getString("app_name").trim()
				// .replaceAll(" ", "")
				// + " ";

				JSONArray jsonArray = jSONObject.getJSONArray("tabs");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);

					if (jsonObject2.getString("tab_type").equals("-1")) {

						homeModel = new NavigationModel();
						homeModel.setId(i);
						homeModel
								.setIconPath(jsonObject2.getString("tab_icon"));
						homeModel.setTitle(jsonObject2.getString("tab_name"));
						homeModel.setTab_id(jsonObject2.getString("tab_id"));
						homeModel
								.setTab_type(jsonObject2.getString("tab_type"));
						homeModel.setFunction(jsonObject2.getString(
								"function_name").toLowerCase());

						EMPApp.getSingleton().setHomeModel(homeModel);
						continue;
					}

					NavigationModel model = new NavigationModel();
					model.setId(i);
					model.setIconPath(jsonObject2.getString("tab_icon"));
					model.setTitle(jsonObject2.getString("tab_name"));
					model.setTab_id(jsonObject2.getString("tab_id"));
					model.setTab_type(jsonObject2.getString("tab_type"));
					model.setFunction(jsonObject2.getString("function_name")
							.toLowerCase());

					tabList.add(model);
				}

				// for ui
				JSONObject uiJSON = jSONObject.getJSONObject("style");

				uIConfig.setApp_background_color(uiJSON
						.getString("app_background_color"));
				uIConfig.setApp_background_image(uiJSON
						.getString("app_background_image"));
				uIConfig.setApp_text_color(uiJSON.getString("app_text_color"));
				uIConfig.setNavbar_background_color(uiJSON
						.getString("navbar_background_color"));
				uIConfig.setNavbar_text_color(uiJSON
						.getString("navbar_text_color"));
				uIConfig.setTopbar_background_color(uiJSON
						.getString("topbar_background_color"));
				uIConfig.setTopbar_text_color(uiJSON
						.getString("topbar_text_color"));
				uIConfig.setOpacity(uiJSON.getInt("opacity"));
				uIConfig.setApp_icon_type(uiJSON.getString("icon_type"));

				// for Template_type
				Confi.getInstance().setAPP_Type(
						jSONObject.getString("Template_type"));

			} catch (JSONException e) {
				callJoinFailure(tabURL, e.getMessage().toString());
			}

			serialization();

			if (tabList.size() <= 0) {
				postIntent();
			} else {
				// load the navagation icon
				for (NavigationModel model : tabList) {
					AsyncAppImage asyncAppImage = new AsyncAppImage();
					asyncAppImage.addImageLoadCallback(this);
					asyncAppImage.execute(model.getIconPath());

				}
			}

		}

	}

	@Override
	public void callJoinFailure(String url, String failMessage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void callImageSuccess(String url, Drawable drawable) {
		for (NavigationModel model : tabList) {
			if (model.getIconPath().equals(url))
				model.setIcon(drawable);
		}

		if (url.equals(tabList.get(tabList.size() - 1).getIconPath())) {
			postIntent();
		}

	}

	@Override
	public void callImageFailure(String url) {
		// TODO Auto-generated method stub

	}

	private void postIntent() {
		Intent intent = new Intent(LoadingActivity.this, HomeActivity.class);
		LoadingActivity.this.startActivity(intent);
		LoadingActivity.this.finish();
	}

	@Override
	public void callJoinMore(String url, JSONObject json) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			System.gc();
			LoadingActivity.this.finish();
			int nPid = android.os.Process.myPid();
			android.os.Process.killProcess(nPid);
		}
		return false;
	}
}