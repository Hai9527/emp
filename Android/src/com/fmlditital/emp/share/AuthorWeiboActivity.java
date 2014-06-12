package com.fmlditital.emp.share;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fmlditital.emp.R;
import com.fmlditital.emp.comment.CommentManage;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.interf.LoginfinishCallback;
import com.weibo.net.RequestToken;
import com.weibo.net.ShareActivity;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;

public class AuthorWeiboActivity extends Activity {

	private WebView mWebView;

	// for Sina Weibo
	private Weibo mWeibo = Weibo.getInstance();
	private static final String URL_ACTIVITY_CALLBACK = "weiboandroidsdk://TimeLineActivity";
	private static final String FROM = "xweibo";

	private static final String CONSUMER_KEY = "815274840";
	private static final String CONSUMER_SECRET = Confi.WEIBO_APP_SECRET;

	private String url = null;
	private String content = null;
	private String picPath = null;
	private String commentLogin = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.authorweibo);

		content = this.getIntent().getExtras()
				.getString(ShareActivity.EXTRA_WEIBO_CONTENT);
		picPath = this.getIntent().getExtras()
				.getString(ShareActivity.EXTRA_PIC_URI);

		commentLogin = this.getIntent().getExtras()
				.getString(CommentManage.COMMENT_MANAGE_LOGIN_FLAG);

		mWebView = (WebView) findViewById(R.id.authorweibo_webView);
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSavePassword(true);
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(false);
		mWebView.setWebViewClient(wvc);
		// mWebView.setWebChromeClient(new MyWebChromeClient());

		Weibo weibo = Weibo.getInstance();
		weibo.setupConsumerConfig(CONSUMER_KEY, CONSUMER_SECRET);
		try {

			RequestToken requestToken = weibo.getRequestToken(
					AuthorWeiboActivity.this, Weibo.APP_KEY, Weibo.APP_SECRET,
					AuthorWeiboActivity.URL_ACTIVITY_CALLBACK);

			url = Weibo.URL_AUTHENTICATION + "?display=wap2.0&oauth_token="
					+ requestToken.getToken() + "&from="
					+ AuthorWeiboActivity.FROM;
			mWebView.loadUrl(url);
			// activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

	// final class MyWebChromeClient extends WebChromeClient {
	//
	// @Override
	// public boolean onJsAlert(WebView view, String url, String message,
	// JsResult result) {
	// new AlertDialog.Builder(view.getContext())
	// .setMessage(message)
	// .setTitle(R.string.message)
	// .setPositiveButton("OK",
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	// }
	//
	// }).setCancelable(true).show();
	// result.confirm();
	//
	// return true;
	// // return super.onJsAlert(view, url, message, result);
	// }
	//
	// }

	// private Handler handler=new Handler() ;

	WebViewClient wvc = new WebViewClient() {

		@Override
		public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
			// TODO Auto-generated method stub
			return super.shouldOverrideKeyEvent(view, event);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {


			Uri uri = Uri.parse(url);
			String oauth_verifier = uri.getQueryParameter("oauth_verifier");
			mWeibo.addOauthverifier(oauth_verifier);
			try {
				mWeibo.generateAccessToken(AuthorWeiboActivity.this, null);
			} catch (WeiboException e1) {
				e1.printStackTrace();
			}

			UserInfo userInfo = new UserInfo();
			userInfo.setKey(Confi.SHARE_WEIBO_ID);
			userInfo.setToken(mWeibo.getAccessToken().getToken());
			userInfo.setTokenSecret(mWeibo.getAccessToken().getSecret());

			try {
				getUserInfo(mWeibo, userInfo);
			} catch (WeiboException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			Confi.getInstance().getUserInfos().add(userInfo);

			if (commentLogin != null) {  //comment login
				loginfinishCallback.loginfinishCallback();
				AuthorWeiboActivity.this.finish();
			} else {
				// share to weibo
				try {
					mWeibo.share2weibo(AuthorWeiboActivity.this, mWeibo
							.getAccessToken().getToken(), mWeibo
							.getAccessToken().getSecret(), content, picPath);

				} catch (WeiboException e1) {
					e1.printStackTrace();
				}

			}

			AuthorWeiboActivity.this.finish();
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
	};


	/**
	 * get User Info
	 * 
	 * @param weibo
	 * @param userInfo
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws WeiboException
	 */
	private void getUserInfo(Weibo weibo, UserInfo userInfo)
			throws MalformedURLException, IOException, WeiboException {
		UserInfo result = userInfo;

		String url = Weibo.SERVER + "account/verify_credentials.json";
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", Weibo.APP_KEY);
		String rlt = weibo.request(this, url, bundle, "GET",
				mWeibo.getAccessToken());

		try {

			JSONObject jsonObject = new JSONObject(rlt);
			result.setId(jsonObject.getString("id"));
			result.setUerName(jsonObject.getString("screen_name"));
			result.setUserIcon(jsonObject.getString("profile_image_url"));
		} catch (JSONException e) {
//			e.printStackTrace();
		}
	}
	
	private  static  LoginfinishCallback loginfinishCallback=null;
	public static void addLoginfinishCallback(LoginfinishCallback loginfinishCallback) {
		 AuthorWeiboActivity.loginfinishCallback=loginfinishCallback;
	}


}
