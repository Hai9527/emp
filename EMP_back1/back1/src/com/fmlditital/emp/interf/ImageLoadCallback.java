package com.fmlditital.emp.interf;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public interface ImageLoadCallback {
	
	public void callImageSuccess(String url,Drawable drawable ) ;
	public void callImageFailure(String url) ;

}
