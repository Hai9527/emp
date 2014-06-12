package com.fmlditital.emp.view;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.Gallery;

public class CastGalleryView extends Gallery {

	public CastGalleryView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	 @Override
	 public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	 float velocityY) {
	 // TODO Auto-generated method stub
	 return false;
	 }
}
