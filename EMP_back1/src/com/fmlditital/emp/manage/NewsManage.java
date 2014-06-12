package com.fmlditital.emp.manage;

import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fmlditital.emp.HomeActivity;
import com.fmlditital.emp.R;
import com.fmlditital.emp.app.EMPApp;
import com.fmlditital.emp.article.ArticleActivity;
import com.fmlditital.emp.article.ArticleDetailActivity;
import com.fmlditital.emp.model.AriticleModel;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.NavigationModel;

public class NewsManage {
	private EMPApp app = EMPApp.getSingleton();
	private List<BaseModel> data = app.getBottomDataAriticle();

	private List<NavigationModel> tabList = app.getTabList();

	private static NewsManage newsManage = null;
	private static Random mRandom = null;

	private TextView textView1 = null, textView2 = null, textView3 = null;

	private static Context context = null;

	private String newsTid = null, newsTitle = null, comment_count = null;

	private int newsCount = 0;

	private NewsManage() {

	}

	public static NewsManage getNewsManage(Context context) {
		if (newsManage == null)
			newsManage = new NewsManage();
		if (mRandom == null)
			mRandom = new Random();
		NewsManage.context = context;
		return newsManage;
	}

	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1: {
				updateNews();
			}
				break;
			}
			super.handleMessage(msg);
		}

	};

	public void updateNewsTextView(TextView textView1, TextView textView2,
			TextView textView3) {

		this.textView1 = textView1;
		this.textView2 = textView2;
		this.textView3 = textView3;
		if (textView1 != null && textView2 != null) {
			Runnable run = new Runnable() {

				public void run() {
					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);
				}

			};
			handler.post(run);
		}
	}

	private void updateNews() {
		if (data == null || data.size() == 0)
			return;
		newsCount = data.size();
		if (data.size() == 1) {
			textView1.setText(((AriticleModel) data.get(0)).getTitle());
			textView1.setOnClickListener(newsOnClickListener);

		} else if (data.size() == 2) {
			textView1.setText(((AriticleModel) data.get(0)).getTitle());
			textView2.setText(((AriticleModel) data.get(1)).getTitle());
			textView1.setOnClickListener(newsOnClickListener);
			textView2.setOnClickListener(newsOnClickListener);
		}
		if (data.size() >= 3) {
			textView1.setText(((AriticleModel) data.get(0)).getTitle());
			textView2.setText(((AriticleModel) data.get(1)).getTitle());
			textView3.setText(((AriticleModel) data.get(2)).getTitle());
			textView1.setOnClickListener(newsOnClickListener);
			textView2.setOnClickListener(newsOnClickListener);
			textView3.setOnClickListener(newsOnClickListener);
		}
	}

	private int getRandomIndex() {
		int size = data.size();
		if (size == 0) {
			return 0;
		}
		return Math.abs(mRandom.nextInt() % size);
	}

	private NewsOnClickListener newsOnClickListener = new NewsOnClickListener();

	private class NewsOnClickListener implements OnClickListener {

		public void onClick(View v) {
			int id = v.getId();

			for (NavigationModel model : tabList) {
				if (model.getFunction().equals("article")) {

					newsTid = model.getTab_id();
					newsTitle = model.getTitle();
				}
			}

			switch (id) {
			case R.id.bottomshortcut_newstitle1: {
				Intent intent = new Intent(context, ArticleDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("path", "bottom");
				bundle.putInt("index", 0);
				bundle.putString("tabName", newsTitle);
				bundle.putString("comment_count", HomeActivity.comment_count);
				bundle.putString("tid", newsTid);

				intent.putExtras(bundle);
				context.startActivity(intent);

				// Log.v("",
				// "???????????????????????>>>>>>>>>>>>NewsOnClickListener textView1");
				// Toast.makeText(context, "kkk", Toast.LENGTH_LONG).show();
			}
				break;
			case R.id.bottomshortcut_newstitle2: {
				Intent intent = new Intent(context, ArticleDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("path", "bottom");
				bundle.putInt("index", 1);
				bundle.putString("tabName", newsTitle);
				bundle.putString("tid", newsTid);
				bundle.putString("comment_count", HomeActivity.comment_count);
				intent.putExtras(bundle);
				context.startActivity(intent);
				// Log.v("",
				// "???????????????????????>>>>>>>>>>>>NewsOnClickListener textView2");
				// Toast.makeText(context, "bbb", Toast.LENGTH_LONG).show();
			}
				break;
			case R.id.bottomshortcut_newstitle3: {
				Intent intent = new Intent(context, ArticleDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("path", "bottom");
				bundle.putInt("index", 2);
				bundle.putString("tabName", newsTitle);
				bundle.putString("tid", newsTid);
				bundle.putString("comment_count", HomeActivity.comment_count);
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
				break;
			}

		}

	}

}
