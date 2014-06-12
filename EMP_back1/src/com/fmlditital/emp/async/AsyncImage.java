package com.fmlditital.emp.async;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.fmlditital.emp.app.EMPApp;
import com.fmlditital.emp.interf.ImageLoadCallback;
import com.fmlditital.emp.manage.ImageSaveManage;
import com.fmlditital.emp.manage.ImageSaveManage.ImageSaveDir;

public class AsyncImage extends AsyncTask<String, Void, Drawable> {
	
	private static HashMap<String, SoftReference<Drawable>> drawableCache=EMPApp.getSingleton().getDrawableCache();

	private ImageLoadCallback imageLoadCallback = null;
	private String imageUrl = null;

	public void addImageLoadCallback(ImageLoadCallback imageLoadCallback) {
		this.imageLoadCallback = imageLoadCallback;
	}

	@Override
	protected Drawable doInBackground(String[] params) {
		imageUrl = params[0];
		Drawable drawable = null;
		
		if (drawableCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = drawableCache.get(imageUrl);

			Drawable drawableCache = softReference.get();
			if (drawable != null) {
				return drawable;
			}
		}
		
		drawable = ImageSaveManage.getDrawableFromSD(imageUrl,ImageSaveDir.cachekey);
		if (drawable != null) {
			drawableCache.put(imageUrl, new SoftReference<Drawable>(drawable));
			return drawable;
		}else {
			drawable = ImageSaveManage.loadDrawableFromUrl(imageUrl,ImageSaveDir.cachekey);
			drawableCache.put(imageUrl, new SoftReference<Drawable>(drawable));
			return drawable;
		}
	}

	@Override
	protected void onPostExecute(Drawable result) {
		super.onPostExecute(result);
		if (result != null)
			imageLoadCallback.callImageSuccess(imageUrl, result);
		else
			imageLoadCallback.callImageFailure(imageUrl);
	}

}
