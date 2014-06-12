package com.fmlditital.emp.share;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.share.info.ShareInfo;
import com.fmlditital.emp.share.info.SharePicture;
import com.weibo.net.AccessToken;
import com.weibo.net.ShareActivity;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboException;

public class ShareManage {
	private static ShareManage instance = null;
	UserInfo userInfo;

	private ShareManage() {

	}

	public static ShareManage getInstance() {
		if (instance == null)
			instance = new ShareManage();
		return instance;
	}

	// //for Sina Weibo
	// private static final String URL_ACTIVITY_CALLBACK =
	// "weiboandroidsdk://TimeLineActivity";
	// private static final String FROM = "xweibo";
	//
	// private static final String CONSUMER_KEY = "815274840";
	// private static final String CONSUMER_SECRET
	// =Configuration.WEIBO_APP_SECRET;

	/**
	 * 分享到新浪
	 * 
	 * @param activity
	 * @param userInfo
	 * @param shareInfo
	 */
	public void share2weibo(Activity activity, ShareInfo shareInfo) {
		UserInfo userInfo = null;
//        System.out.println("分享------------到-------------新浪");
		// have author
		if (Confi.getInstance().getUserInfos() != null
				&& Confi.getInstance().getUserInfos().size() > 0) {
			for (UserInfo temp : Confi.getInstance().getUserInfos()) {
				if (temp.getKey().equals(Confi.SHARE_WEIBO_ID))
					userInfo = temp;
			}

			AccessToken accessToken = new AccessToken(userInfo.getToken(),
					userInfo.getTokenSecret());
			Weibo weibo = Weibo.getInstance();
			weibo.setAccessToken(accessToken);

			try {
				if (shareInfo instanceof SharePicture) { // share picture and
															// text

					weibo.share2weibo(activity, weibo.getAccessToken()
							.getToken(), weibo.getAccessToken().getSecret(),
							shareInfo.getContext(), ((SharePicture) shareInfo)
									.getImagePath());
				} else { // share text
					weibo.share2weibo(activity, weibo.getAccessToken()
							.getToken(), weibo.getAccessToken().getSecret(),
							shareInfo.getContext(), "");
				}

			} catch (WeiboException e1) {
				e1.printStackTrace();
			}
		} else { // for author
			Intent intent = new Intent(activity, AuthorWeiboActivity.class);
			intent.putExtra(ShareActivity.EXTRA_WEIBO_CONTENT,
					shareInfo.getContext());
			intent.putExtra(ShareActivity.EXTRA_PIC_URI, "");
			activity.startActivity(intent);

		}

	}

}
