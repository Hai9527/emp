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
import android.widget.Toast;

import com.fmlditital.emp.R;
import com.fmlditital.emp.comment.CommentManage;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.interf.LoginfinishCallback;
//import com.weibo.android.example.AuthorizeActivity;
//import com.weibo.android.example.TestActivity;
import com.weibo.net.AccessToken;
import com.weibo.net.DialogError;
import com.weibo.net.RequestToken;
import com.weibo.net.ShareActivity;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboDialogListener;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;

public class AuthorWeiboActivity extends Activity {

	private WebView mWebView;

	// for Sina Weibo
	private Weibo mWeibo = Weibo.getInstance();
	private static final String URL_ACTIVITY_CALLBACK = "weiboandroidsdk://TimeLineActivity";
	private static final String FROM = "xweibo";

	private static final String CONSUMER_KEY = "437817123";
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
//		try {

//			RequestToken requestToken = weibo.getRequestToken(
//					AuthorWeiboActivity.this, Weibo.APP_KEY, Weibo.APP_SECRET,
//					AuthorWeiboActivity.URL_ACTIVITY_CALLBACK);
//            System.out.println(Weibo.APP_KEY+"=============requestToken================= "+requestToken.getToken());
            weibo.setRedirectUrl("http://www.sina.com");
            weibo.authorize(this,
					new AuthDialogListener());
//            url = Weibo.URL_AUTHENTICATION + "?display=wap2.0&oauth_token="
//					+ requestToken.getToken() + "&from="
//					+ AuthorWeiboActivity.FROM;
//            url=Weibo.URL_OAUTH2_ACCESS_AUTHORIZE+"?client_id="+Weibo.APP_KEY+"&redirect_uri=http://www.sina.com&response_type=token";
//			System.out.println("sina url ===== "+url);
//            mWebView.loadUrl(url);
//            Uri uri =Uri.parse(URL_ACTIVITY_CALLBACK);
//			startActivity(new Intent(Intent.ACTION_VIEW, uri));
//		} catch (WeiboException e) {
//			e.printStackTrace();
//		}
	}
    private String uid=null;
	class AuthDialogListener implements WeiboDialogListener {

		@Override
		public void onComplete(Bundle values) {
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
//			mToken.setText("access_token : " + token + "  expires_in: "
//					+ expires_in);
			System.out.println("token ======= "+token);
			AccessToken accessToken = new AccessToken(token, CONSUMER_SECRET);
			accessToken.setExpiresIn(expires_in);
			Weibo.getInstance().setAccessToken(accessToken);
//			Intent intent = new Intent();
//			intent.setClass(this, TestActivity.class);
//			startActivity(intent);
			uid=values.getString("uid");
			UserInfo userInfo = new UserInfo();
			userInfo.setKey(Confi.SHARE_WEIBO_ID);
			userInfo.setToken(mWeibo.getAccessToken().getToken());
			userInfo.setTokenSecret(mWeibo.getAccessToken().getSecret());

			try {
				getUserInfo(mWeibo, userInfo);
			} catch (WeiboException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
            System.out.println("shouldOverrideUrlLoading::::authorWeibo::::"+userInfo.getUerName());
			Confi.getInstance().getUserInfos().add(userInfo);

			if (commentLogin != null) { // comment login
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
		}

		@Override
		public void onError(DialogError e) {
			Toast.makeText(getApplicationContext(),
					"Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(getApplicationContext(), "Auth cancel",
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(getApplicationContext(),
					"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}

	}

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
			System.out.println(url+"::::::::oauth_verifier=================== "+oauth_verifier);
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
			} catch (Exception e1) {
				e1.printStackTrace();
			}
            System.out.println("shouldOverrideUrlLoading::::::::"+userInfo.getUerName());
			Confi.getInstance().getUserInfos().add(userInfo);

			if (commentLogin != null) { // comment login
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

//		String url = Weibo.SERVER + "account/verify_credentials.json";
		String url = Weibo.SERVER + "users/show.json";
		
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", Weibo.APP_KEY);
//		bundle.add("access_token", mWeibo.getAccessToken().getToken());
		bundle.add("uid", uid);
		System.out.println("AccessToken ============ "+mWeibo.getAccessToken().getToken());
		String rlt = weibo.request(this, url, bundle, "GET",
				mWeibo.getAccessToken());
        System.out.println("request ::::::::: "+rlt);
		try {

			JSONObject jsonObject = new JSONObject(rlt);
			result.setId(jsonObject.getString("id"));
			result.setUerName(jsonObject.getString("screen_name"));
			result.setUserIcon(jsonObject.getString("profile_image_url"));
		} catch (JSONException e) {
			Log.d("JSONException", "-------JSONException-------");
			 e.printStackTrace();
		}
	}

	private static LoginfinishCallback loginfinishCallback = null;

	public static void addLoginfinishCallback(
			LoginfinishCallback loginfinishCallback) {
		AuthorWeiboActivity.loginfinishCallback = loginfinishCallback;
	}

}
