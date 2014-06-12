package com.fmlditital.emp.interf;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class ViewPagerOnPageChangeListener implements OnPageChangeListener {

//	int one = offset * 2 + bmpW; 
//	int two = one * 2;// é¡µå�¡1 -> é¡µå�¡3 å��ç§»é‡�

	
	public void onPageSelected(int arg0) {
//		Animation animation = null;
//		switch (arg0) {
//		case 0:
//			if (currIndex == 1) {
//				animation = new TranslateAnimation(one, 0, 0, 0);
//			} else if (currIndex == 2) {
//				animation = new TranslateAnimation(two, 0, 0, 0);
//			}
//			break;
//		case 1:
//			if (currIndex == 0) {
//				animation = new TranslateAnimation(offset, one, 0, 0);
//			} else if (currIndex == 2) {
//				animation = new TranslateAnimation(two, one, 0, 0);
//			}
//			break;
//		case 2:
//			if (currIndex == 0) {
//				animation = new TranslateAnimation(offset, two, 0, 0);
//			} else if (currIndex == 1) {
//				animation = new TranslateAnimation(one, two, 0, 0);
//			}
//			break;
//		}
//		currIndex = arg0;
//		animation.setFillAfter(true);// True:å›¾ç‰‡å�œåœ¨åŠ¨ç”»ç»“æ�Ÿä½�ç½®
//		animation.setDuration(300);
//		cursor.startAnimation(animation);
	}

	
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	
	public void onPageScrollStateChanged(int arg0) {
	}
}

 
