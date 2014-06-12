package com.fmlditital.emp.comment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fmlditital.emp.R;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.form.FormActivity;
import com.fmlditital.emp.model.CommentModel;
import com.fmlditital.emp.share.UserInfo;

public class CommentManage {
	public final static int COMMENT_MANAGE_LOGIN = 1;
	public final static String COMMENT_MANAGE_LOGIN_FLAG = "COMMENT_MANAGE_LOGIN_FLAG";
	private static CommentManage instance = null;

	private static int commentResult = 0; // 0 for no 1 for ok

	private CommentManage() {

	}

	public static CommentManage getInstance() {
		if (instance == null)
			instance = new CommentManage();

		return instance;
	}

	public CommentModel packagedCommentModel(CommentModel commentModel) {
		UserInfo userInfo = null;
		CommentModel result = commentModel;
		Log.d("", "packagedCommentModel::::::::::: "+Confi.getInstance().getUserInfos().size());
		for (UserInfo temp : Confi.getInstance().getUserInfos()) {
			Log.d("", "::::::::::: "+temp.getUerName()+":::::"+temp.getId());
			if (temp.getKey().equals(Confi.SHARE_WEIBO_ID))
				userInfo = temp;
		}

		result.setUserId(userInfo.getId());
		result.setUserName(userInfo.getUerName());
		result.setUserPortrait(userInfo.getUserIcon());
		

		return result;
	}
}
