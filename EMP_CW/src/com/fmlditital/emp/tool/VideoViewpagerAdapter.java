package com.fmlditital.emp.tool;

import java.util.List;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class VideoViewpagerAdapter extends PagerAdapter {
	private List<Integer> mPaths;
	ImageView iv;
	private Context mContext;

	public VideoViewpagerAdapter(Context cx) {
		mContext = cx.getApplicationContext();
	}

	public void change(List<Integer> paths) {
		mPaths = paths;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewGroup) arg0).removeView((View) arg2);
	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCount() {
		return mPaths.size();
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		iv = new ImageView(mContext);
		iv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		try {
			iv.setImageResource(mPaths.get(arg1));
		} catch (OutOfMemoryError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((ViewPager) arg0).addView(iv, 0);
		return iv;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (View) arg1;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

}
