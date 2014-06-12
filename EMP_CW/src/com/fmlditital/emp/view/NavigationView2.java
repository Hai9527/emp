package com.fmlditital.emp.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fmlditital.emp.R;
import com.fmlditital.emp.adapter.NavigationGridAdapter;
import com.fmlditital.emp.adapter.ViewPagerAdapter;
import com.fmlditital.emp.app.EMPApp;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.model.NavigationModel;
import com.fmlditital.emp.tool.Tools;

/**
 * @author å¯¼èˆªæ �
 */
public class NavigationView2 extends LinearLayout implements
		OnPageChangeListener {

	private EMPApp app = EMPApp.getSingleton();
	private List<NavigationModel> tabList = app.getTabList();
	private int navigationPage = 0;

	private Context context = null;

	private ViewPager mPager;
	private List<View> listViews;

	private GridView[] mGrids = null;
	private List<NavigationModel>[] datasList = null;
	private NavigationGridAdapter[] adapters = null;

	private UIConfig uiConfig = null;
	private LinearLayout.LayoutParams lLayoutParams = null;
	private int bmpW;// 动画图片宽度
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	ImageView i;

	public NavigationView2(Context cont) {
		super(cont);
		this.context = cont;

		lLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lLayoutParams.gravity = Gravity.CENTER;
		lLayoutParams.topMargin = Tools.dip2px(context, 10);
		lLayoutParams.height = Tools.dip2px(context, 52);
		// this.setLayoutParams(lLayoutParams);
		this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		this.setOrientation(LinearLayout.VERTICAL);

		navigationPage = getnavigationPageInt(tabList.size(), 5);
		Log.d("navigationPage", " " + navigationPage + "::::" + tabList.size());
		mGrids = new GridView[navigationPage];
		datasList = new List[navigationPage];
		adapters = new NavigationGridAdapter[navigationPage];

		uiConfig = Confi.getInstance().getuIConfig();
		this.setBackgroundColor(Color.parseColor(uiConfig
				.getNavbar_background_color()));
		setGridsData();
	}

	/**
	 * add the gridview's data
	 */
	private void setGridsData() {

		mPager = new ViewPager(context);
		mPager.setLayoutParams(new ViewPager.LayoutParams(
				ViewPager.LayoutParams.FILL_PARENT,
				ViewPager.LayoutParams.WRAP_CONTENT));

		listViews = new ArrayList<View>();

		for (int i = 0, j = 0; i < navigationPage; i++) {
			j = i * 5;

			if (i != navigationPage - 1)
				datasList[i] = tabList.subList(j, j + 5);
			else
				datasList[i] = tabList.subList(j, tabList.size());

			mGrids[i] = new GridView(context);
			mGrids[i].setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			mGrids[i].setNumColumns(5);
			// mGrids[i].setBackgroundColor(Color.parseColor(uiConfig
			// .getNavbar_background_color()));
			// mGrids[i].setClickable(false);
			// mGrids[i].setFocusable(false);
			mGrids[i].setFocusableInTouchMode(false);
			// mGrids[i].setOnTouchListener(this);
			mGrids[i].setOnItemClickListener(new OnItemClickListener() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * android.widget.AdapterView.OnItemClickListener#onItemClick
				 * (android.widget.AdapterView, android.view.View, int, long)
				 * å¯¼èˆªå¿«æ�·æ–¹å¼�çš„ç‚¹å‡»å¤„ç�†
				 */
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					NavigationModel model = (NavigationModel) parent
							.getItemAtPosition(position);

					Intent intent = getIntent(model.getFunction(),
							model.getTitle(), model.getTab_id());
					context.startActivity(intent);
				}

			});

			listViews.add(mGrids[i]);

			adapters[i] = new NavigationGridAdapter(context, datasList[i]);
			mGrids[i].setAdapter(adapters[i]);

		}

		mPager.setAdapter(new ViewPagerAdapter(listViews));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
			int two = one * 2;// 页卡1 -> 页卡3 偏移量

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				Animation animation = null;
				switch (arg0) {
				case 0:
					if (currIndex == 1) {
						animation = new TranslateAnimation(one, 0, 0, 0);
					} else if (currIndex == 2) {
						animation = new TranslateAnimation(two, 0, 0, 0);
					}
					break;
				case 1:
					if (currIndex == 0) {
						animation = new TranslateAnimation(offset, one, 0, 0);
					} else if (currIndex == 2) {
						animation = new TranslateAnimation(two, one, 0, 0);
					}
					break;
				case 2:
					if (currIndex == 0) {
						animation = new TranslateAnimation(offset, two, 0, 0);
					} else if (currIndex == 1) {
						animation = new TranslateAnimation(one, two, 0, 0);
					}
					break;
				}
				currIndex = arg0;
				animation.setFillAfter(true);// True:图片停在动画结束位置
				animation.setDuration(300);
				i.startAnimation(animation);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		// Vheigh=BitmapFactory.decodeResource(getResources(), R.drawable.a)
		// .getHeight();
		// mPager.setLayoutParams(new ViewPager.LayoutParams(
		// ViewPager.LayoutParams.FILL_PARENT,ViewPager.LayoutParams.WRAP_CONTENT));
		// ViewPager.LayoutParams lp = new ViewPager.LayoutParams(
		// ViewPager.LayoutParams.WRAP_CONTENT,
		// ViewPager.LayoutParams.WRAP_CONTENT);
		//
		// lp.height = Tools.dip2px(context, 60);
		// this.addView(mPager, lp);
		// lLayoutParams.height=Tools.dip2px(context, 60);
		// lLayoutParams.topMargin=Tools.dip2px(context, 10);
		i = new ImageView(context);
		i.setImageResource(R.drawable.dong);

		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.dong)
				.getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		i.setImageMatrix(matrix);// 设置动画初始位置
		//this.addView(i);
		this.addView(mPager, lLayoutParams);

	}

	private Intent getIntent(String function, String tabName, String tid) {
		Intent intent = null;
		Bundle bundle = new Bundle();
		bundle.putString("tabName", tabName);
		bundle.putString("tid", tid);

		for (int i = 0; i < Global.functions.length; i++) {
			if (function.equals(Global.functions[i])) {
				intent = new Intent(context, Global.classes[i]);
				intent.putExtras(bundle);
				return intent;
			}
		}
		return intent;
	}

	private int getnavigationPageInt(int a, int b) {
		return (((double) a / (double) b) > (a / b) ? a / b + 1 : a / b);
	}

	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	public void onPageSelected(int arg0) {

	}
}
