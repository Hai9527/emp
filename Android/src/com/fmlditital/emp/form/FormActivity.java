package com.fmlditital.emp.form;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.fmlditital.emp.HomeActivity;
import com.fmlditital.emp.LoadingActivity;
import com.fmlditital.emp.R;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.async.AsyncRefreshJson;
import com.fmlditital.emp.base.TopBarActivity;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.dowmload.DownloadActivity;
import com.fmlditital.emp.model.FormModel;
import com.fmlditital.emp.preferences.SettingActivity;
import com.fmlditital.emp.tool.NetWorkState;
import com.fmlditital.emp.tool.StringUtil;
import com.fmlditital.emp.tool.Tools;

public class FormActivity extends TopBarActivity {

	private static final int REFRESH = Menu.FIRST;
	private static final int DOWNLOAD = Menu.FIRST + 1;
	private static final int SETTING = Menu.FIRST + 2;

	private String FORM_URL = null;
	private String FORM_SUMIT_URL = null;
	private String tid = null;

	protected AsyncJson asyncJson = null;

	private String htmlDetail = null;
	// private LayoutParams param = null;
	private String htmlBody = "";
	private String htmlScript = "";

	private Button submitBtn = null;
	private String formMessage = null, formIntroduction = null;

	private List<FormModel> formData = null;

	private SubscribeAsync async;
	private WebView mWebView = null;

	private boolean isFristRadio = true;

	private  UIConfig uiConfig = Confi.getInstance().getuIConfig();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tid = this.getIntent().getExtras().getString("tid");

		mWebView = new WebView(this);
		mWebView.setBackgroundColor(0);
		mWebView.setHorizontalFadingEdgeEnabled(false);
		mWebView.setHorizontalScrollbarOverlay(false);
		mWebView.setScrollBarStyle(0);

		mWebView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(false);
		webSettings.setBuiltInZoomControls(false);
		mWebView.setWebViewClient(wvc);
		mWebView.setWebChromeClient(new MyWebChromeClient());

		formData = new ArrayList<FormModel>();

		// FORM_URL = Confi.getInstance().getFormApiUrl(tid);
		FORM_URL = Confi.getInstance().getFormApiUrl(Integer.parseInt(tid));
		asyncJson = new AsyncJson(this);
		asyncJson.addInterFace(this);
		asyncJson.execute(FORM_URL);

		submitBtn = new Button(this);
		submitBtn.setText(this.getResources().getString(R.string.submit));
		submitBtn.setTextColor(Color.parseColor(Confi.getInstance()
				.getuIConfig().getTopbar_text_color()));
		submitBtn.setBackgroundColor(Color.parseColor(Confi.getInstance()
				.getuIConfig().getTopbar_background_color()));
		submitBtn.setTextSize(16);

		submitBtn.setGravity(Gravity.CENTER);

		RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		lp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		topBarView.addView(submitBtn, lp1);
		topBarView.invalidate();

		submitBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (NetWorkState.isNetworkAvailable(FormActivity.this)) {
					mWebView.loadUrl("javascript:done()");
				} else {
					Tools.showBadNetwork(FormActivity.this);
				}
			}

		});

		bodyLayout.addView(mWebView);
	}

	@Override
	public void callJoinSuccess(String url, JSONObject json) {
		super.callJoinSuccess(url, json);
		if (url.equals(FORM_URL)) {
			formData.clear();
			try {
				JSONObject formJsonObject = (JSONObject) json
						.getJSONObject("datas");

				formMessage = formJsonObject.getString("form_message");
				formIntroduction = formJsonObject
						.getString("form_introduction");

				JSONArray jsonArray = formJsonObject.getJSONArray("fields");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
					FormModel model = new FormModel();
					model.setFvid(jsonObject2.getString("fvid"));
					model.setFv_name(jsonObject2.getString("fv_name"));
					model.setFv_title(jsonObject2.getString("fv_title"));
					model.setFv_type(jsonObject2.getString("fv_type"));
					model.setFv_required(jsonObject2.getString("fv_required"));
					model.setFv_value(jsonObject2.getString("fv_value"));
					model.setPrompting_message(jsonObject2
							.getString("prompting_message"));
					model.setVis_hits(jsonObject2.getString("vis_hits"));
					model.setFv_text(jsonObject2.getString("fv_text"));
					formData.add(model);
				}

				htmlDetail = getHtml("form.html", formIntroduction, formData);

				// Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>htmlDetail  " +
				// htmlDetail);

			} catch (JSONException e) {
				// e.printStackTrace();
			}
			// mWebView.loadData(htmlDetail, "text/html", "utf-8");
			mWebView.loadDataWithBaseURL(null, htmlDetail, "text/html",
					"utf-8", null);
		}

	}

	private String getHtml(String path, String introduction,
			List<FormModel> data) {
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

			introduction = StringUtil.replace(introduction);
			if (sb.indexOf("<!--introduction-->") > 0) {
				sb.replace(sb.indexOf("<!--introduction-->"),
						sb.indexOf("<!--introduction-->")
								+ "<!--introduction-->".length(), introduction);
			}

			getBody(data);
			getScript(data);

			if (sb.indexOf("<!--content-->") > 0) {
				sb.replace(
						sb.indexOf("<!--content-->"),
						sb.indexOf("<!--content-->")
								+ "<!--content-->".length(), htmlBody);
			}

			if (sb.indexOf("<!--script-->") > 0) {
				sb.replace(sb.indexOf("<!--script-->"),
						sb.indexOf("<!--script-->") + "<!--script-->".length(),
						htmlScript);
			}

			if (sb.indexOf("<!--colorbg-->") > 0) {
				sb.replace(
						sb.indexOf("<!--colorbg-->"),
						sb.indexOf("<!--colorbg-->")
								+ "<!--colorbg-->".length(), Confi
								.getuIConfig().getTopbar_background_color());

			}

			if (sb.indexOf("<!--colorbg2-->") > 0) {
				sb.replace(
						sb.indexOf("<!--colorbg2-->"),
						sb.indexOf("<!--colorbg2-->")
								+ "<!--colorbg2-->".length(), Confi
								.getuIConfig().getTopbar_text_color());
			}

			if (sb.indexOf("<!--colortext-->") > 0) {
				sb.replace(
						sb.indexOf("<!--colortext-->"),
						sb.indexOf("<!--colortext-->")
								+ "<!--colortext-->".length(), Confi
								.getuIConfig().getTopbar_text_color());
			}
			result = sb.toString();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void getBody(List<FormModel> data) {
		htmlBody = "";
		for (FormModel model : data) {
			String star = (model.getFv_required().endsWith("0")) ? "" : "*";
			switch (Integer.parseInt(model.getFv_type())) {
			case 1:
				htmlBody += "<p><label>" + model.getFv_title() + star
						+ "</label><input type=\"text\" name=\""
						+ model.getFv_name() + "\" /></p>";
				break;
			case 2:
				htmlBody += "<p><label>" + model.getFv_title() + star
						+ "</label><input type=\"checkbox\" name=\""
						+ model.getFv_name() + "\" value=\""
						+ model.getFv_value() + "\" /><span>"
						+ model.getFv_text() + "</span></p>";
				break;
			case 3: {

				if (isFristRadio) {
					htmlBody += "<p><label>" + model.getFv_title() + star
							+ "</label><input type=\"radio\" name=\""
							+ model.getFv_name() + "\" value=\""
							+ model.getFv_value()
							+ "\" checked=\"checked\" /><span>"
							+ model.getFv_text() + "</span></p>";
					isFristRadio = false;
				} else {
					htmlBody += "<p><label></label><input type=\"radio\" name=\""
							+ model.getFv_name()
							+ "\" value=\""
							+ model.getFv_value()
							+ "\" /><span>"
							+ model.getFv_text() + "</span></p>";
				}
			}

				break;
			case 4:
				htmlBody += "<p><label>" + model.getFv_title() + star
						+ "</label><textarea name=\"" + model.getFv_name()
						+ "\"></textarea></p>";
				break;
			case 5:
				htmlBody += "<p></p>";
				break;
			}
		}
	}

	private void getScript(List<FormModel> data) {
		htmlScript = "";
		for (FormModel model : data) {
			if (model.getFv_required().equals("1")) {
				if (model.getFv_type().equals("2")
						|| model.getFv_type().equals("3")) {
					htmlScript += "if(!checkedBoxToggle(\""
							+ model.getFv_name()
							+ "\")) {document.getElementById(\"Tdialog\").style.display=\"block\";document.getElementById(\"Tdialogbg\").style.display=\"block\";document.getElementById(\"message\").innerText=\""
							+ model.getPrompting_message()
							+ "\"; return false;}";
				} else {
					htmlScript += "if(document.getElementsByName(\""
							+ model.getFv_name()
							+ "\")[0].value == \"\") {document.getElementById(\"Tdialog\").style.display=\"block\";document.getElementById(\"Tdialogbg\").style.display=\"block\";document.getElementById(\"message\").innerText=\""
							+ model.getPrompting_message()
							+ "\";  return false;}";
					htmlScript += "if(checkedFormat(\""
							+ model.getFv_name()
							+ "\") != \"\") {document.getElementById(\"Tdialog\").style.display=\"block\";document.getElementById(\"Tdialogbg\").style.display=\"block\";document.getElementById(\"message\").innerText=\""
							+ model.getPrompting_message()
							+ "\";  return false;}";
				}
			}
		}
		htmlScript += "document.apiForm.submit(); status = 0;";
	}

	final class MyWebChromeClient extends WebChromeClient {

		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {

			// final AlertDialog alertDialog = new AlertDialog.Builder(
			// FormActivity.this).create();
			// alertDialog.show();
			// alertDialog.setContentView(R.layout.formdialog);
			// // 接收最外层LinearLayout
			// LinearLayout dialogBground = (LinearLayout) alertDialog
			// .findViewById(R.id.dialog_linearlayout_bg);
			// // 修改LinearLayout颜色
			// dialogBground.setBackgroundColor(Color.parseColor(uiConfig
			// .getTopbar_background_color()));
			// // 设置LinearLayout透明度
			// dialogBground.getBackground().setAlpha(130);
			// // dialogBground.setLayoutParams(dialogLayout);
			// // 接收title
			// TextView dialogTxtTitle = (TextView) alertDialog
			// .findViewById(R.id.dialog_textview_title);
			// dialogTxtTitle.setText(message);
			// dialogTxtTitle.setTextColor(Color.parseColor(uiConfig
			// .getTopbar_text_color()));
			// // 接收OKButton
			// Button dialogBtnOk = (Button) alertDialog
			// .findViewById(R.id.dialog_button_ok);
			// // 修改OKButton字体颜色
			// dialogBtnOk.setTextColor(Color.parseColor(uiConfig
			// .getTopbar_text_color()));
			// // 修改OKButton颜色
			// dialogBtnOk.setBackgroundColor(Color.parseColor(uiConfig
			// .getTopbar_background_color()));
			// dialogBtnOk.setOnClickListener(new OnClickListener() {
			//
			// public void onClick(View v) {
			// alertDialog.cancel();
			// }
			// });
			new AlertDialog.Builder(view.getContext())
					.setMessage(message)
					.setTitle(R.string.message)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
								}

							}).setCancelable(true).show();
			result.confirm();

			return true;
		}

	}

	private WebViewClient wvc = new WebViewClient() {
		public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
			return super.shouldOverrideKeyEvent(view, event);
		}

		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			try {

				url = URLDecoder.decode(url, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			String urlFormat = "http://myapp/subscribe";

			if (url.startsWith(urlFormat)) {
				String submitPath = getJson(url);
				if (submitPath.startsWith("http://")) {
					async = new SubscribeAsync();
					async.execute(submitPath);
				}
			} else {
				String mess = url.substring(url.indexOf("=") + 1, url.length());
				new AlertDialog.Builder(FormActivity.this)
						.setTitle(R.string.message)
						.setMessage(mess)

						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {

									}
								}).show();

				return true;
			}
			return true;
		}

		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
	};

	private String getJson(String url) {

		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		try {
			String[] array = url.split("\\?");
			StringTokenizer st = new StringTokenizer(array[1], "&");
			while (st.hasMoreTokens()) {
				String pairs = st.nextToken();
				String key = pairs.substring(0, pairs.indexOf('='));
				String value = pairs.substring(pairs.indexOf('=') + 1);

				obj.put(key, value);
			}
			obj2.put("formdata", obj);
			obj2.put("mobileid", Tools.getAndroid_ID(this));
		} catch (JSONException e) {
			
		}
	
		return FORM_SUMIT_URL = Confi.getInstance()
				.getFormSumitApiUrl(tid, obj2);
	}

	class SubscribeAsync extends AsyncTask<String, Void, String> {

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
				return result = jsonObject.getString("status");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String result) {
			if (result.equals("1")) {
				LayoutInflater factory = LayoutInflater.from(FormActivity.this);
				View view = factory.inflate(R.layout.mytoast, null);
				Toast myToast = new Toast(FormActivity.this);
				myToast.setView(view);
				LinearLayout myToastBg = (LinearLayout) view
						.findViewById(R.id.myToastBg);
				myToastBg.setBackgroundColor(Color.parseColor(uiConfig
						.getTopbar_background_color()));
				TextView txtToast = (TextView) view
						.findViewById(R.id.myToast_TextView_txtToast);
				txtToast.setText(formMessage);
				txtToast.setTextColor(Color.parseColor(uiConfig
						.getTopbar_text_color()));
				myToast.show();

				mWebView.loadDataWithBaseURL(null, htmlDetail, "text/html",
						"utf-8", null);
				mWebView.invalidate();
				bodyLayout.invalidate();

			}
			super.onPostExecute(result);
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
			isFristRadio = true;
			AsyncRefreshJson asyncRefreshJson = new AsyncRefreshJson(this);
			asyncRefreshJson.addInterFace(this);
			asyncRefreshJson.execute(FORM_URL);
		}
			break;
		case DOWNLOAD: {
			Intent intent = new Intent(FormActivity.this,
					DownloadActivity.class);
			intent.putExtra("tabName", "Download");
			FormActivity.this.startActivity(intent);
		}
			break;
		case SETTING: {
			Intent intent = new Intent(FormActivity.this, SettingActivity.class);
			intent.putExtra("tabName", "Setting");

			FormActivity.this.startActivity(intent);
		}
			break;
		}
		return true;
	}
}
