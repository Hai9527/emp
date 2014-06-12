package com.fmlditital.emp.async;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import com.fmlditital.emp.app.EMPApp;
import com.fmlditital.emp.manage.ImageSaveManage;
import com.fmlditital.emp.manage.ImageSaveManage.ImageSaveDir;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class AsyncListViewImage extends AsyncTask<ImageView, Void, Bitmap> {

//	private HashMap<String, SoftReference<Drawable>> drawableCache=EMPApp.getSingleton().getDrawableCache();
	
	private ImageView gView;
	private String imageUrl = null;

	protected Bitmap doInBackground(ImageView... views) {
		gView = views[0];
		Bitmap bitmap =  null;
		if (gView.getTag() != null) {
			imageUrl = gView.getTag().toString();
//			if (drawableCache.containsKey(imageUrl)) {
//				SoftReference<Drawable> softReference = drawableCache.get(imageUrl);
//
//				Drawable drawableCache = softReference.get();
//				if (drawable != null) {
//					return drawable;
//				}
//			}
			
			bitmap = ImageSaveManage.getBitmapFromSD(imageUrl,ImageSaveDir.cachekey);
			if (bitmap != null) {
//				drawableCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				return bitmap;
			}else {
				bitmap = ImageSaveManage.loadBitmapFromUrl(imageUrl,ImageSaveDir.cachekey);
//				drawableCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				return bitmap;
			}
		}
		
		return bitmap;
			 
	}

	protected void onPostExecute(Bitmap bitmap) {
		if (bitmap != null) {
			this.gView.setImageBitmap(bitmap);
			this.gView = null;
		}
	}
}
