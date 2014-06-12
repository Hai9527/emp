package com.fmlditital.emp.preferences;

import java.io.File;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.base.TopBarActivity;
import com.fmlditital.emp.comment.CommentManage;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.interf.LoginfinishCallback;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.share.AuthorWeiboActivity;
import com.fmlditital.emp.share.UserInfo;

public class SettingActivity extends TopBarActivity implements
		OnClickListener, LoginfinishCallback {

	private LayoutInflater factory = null;
	private LinearLayout linearLayout = null;

	private Button sinaButton = null;
	private TextView cache = null, cacheSize = null,sina=null;

	protected UIConfig uiConfig = Confi.getInstance().getuIConfig();
	
	/** Called when the activity is first created. */
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		factory = LayoutInflater.from(this);
		linearLayout = (LinearLayout) factory.inflate(R.layout.setting, null);
		bodyLayout.addView(linearLayout);
		initView();

		cacheSize.setText(getCache());
	}

	private void initView() {
		sinaButton = (Button) linearLayout
				.findViewById(R.id.setting_button_sina);
		sinaButton.setOnClickListener(this);
		cache = (TextView) linearLayout.findViewById(R.id.setting_clearCache);
		cache.setOnClickListener(this);
		cacheSize = (TextView) findViewById(R.id.setting_cacheSize);
		sina=(TextView)findViewById(R.id.setting_sina_textview);
		
		//for ui
		cache.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
		cacheSize.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
		sina.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
//		sinaButton.setBackgroundColor(Color.parseColor(uiConfig.getTopbar_background_color()));
		sinaButton.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
	}

	
	protected void onResume() {
		super.onResume();

		if (Confi.getInstance().isWeiBoLogin()) {
			sinaButton.setText(this.getResources().getString(R.string.unbind));
		} else {
			sinaButton.setText(this.getResources().getString(R.string.binding));
		}
	}

	
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.setting_button_sina: {
			if (Confi.getInstance().isWeiBoLogin()) {
				List<UserInfo> userInfos = Confi.getInstance().getUserInfos();
					for (int i = userInfos.size() - 1; i >= 0; i--) {
						UserInfo userInfo=userInfos.get(i);
					 
					if (userInfo.getKey().endsWith(Confi.SHARE_WEIBO_ID)) {
						userInfos.remove(userInfo);
					sinaButton.setText(this.getResources().getString(
							R.string.binding));
					}
				}

			} else {
				Intent intent = new Intent(SettingActivity.this,
						AuthorWeiboActivity.class);
				intent.putExtra(CommentManage.COMMENT_MANAGE_LOGIN_FLAG,
						CommentManage.COMMENT_MANAGE_LOGIN_FLAG);
				AuthorWeiboActivity.addLoginfinishCallback(this);
				SettingActivity.this.startActivity(intent);
			}
		}
			break;
		case R.id.setting_clearCache: {
			clearCache();
			cacheSize.setText(getCache());
		}
			break;
		}
	}

	/**
	 * clear Cache
	 */
	private void clearCache() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			File fileDir = new File(Global.THE_APP_FILE_NAME + File.separator
					+ "cache");
			File[] files = fileDir.listFiles();
			for (int i = files.length - 1; i >= 0; i--) {
				if (files[i].exists())
					files[i].delete();
			}
		}
	}

	private String getCache() {
		String result = null;
		long blockSize = 0, totalsize = 0;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			File fileDir = new File(Global.THE_APP_FILE_NAME + File.separator
					+ "cache");

			File[] files = fileDir.listFiles();
			for (File file : files) {
				blockSize = file.length();
				totalsize = totalsize + blockSize;
			}
			result = totalsize / 1024 + "k";
		} else {
			result = "";
		}
		return result;
	}

	
	public void loginfinishCallback() {
		sinaButton.setText(this.getResources().getString(R.string.binding));

	}

}
