package com.fmlditital.emp.async;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.fmlditital.emp.manage.ImageSaveManage;
import com.fmlditital.emp.manage.ImageSaveManage.ImageSaveDir;
import com.fmlditital.emp.tool.ImageUtil;

public class AsyncImageLoader {

	private static HashMap<String, SoftReference<Drawable>> imageCache;

	static {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	public AsyncImageLoader() {

	}

	public Drawable loadDrawable(final String imageUrl,
			final ImageView imageView, final ImageCallback imageCallback) {
		if (imageCache.containsKey(imageUrl)) {
			// ä»Žç¼“å­˜ä¸­èŽ·å�–
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				return drawable;
			}
//			Log.d("AsyncImageLoader","AsyncImageLoader-----------------loadDrawable");
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((Drawable) message.obj, imageView,
						imageUrl);
			}
		};
		// å»ºç«‹æ–°ä¸€ä¸ªæ–°çš„çº¿ç¨‹ä¸‹è½½å›¾ç‰‡
		new Thread() {
			@Override
			public void run() {
				System.out.println("&&&&&&&&&&----LoadDrawable----&&&&&&&&&&&&&");
				Drawable drawable = null;
				try {
//					drawable = ImageUtil.geRoundDrawableFromUrl(imageUrl, 20);
					drawable =ImageSaveManage.getDrawableFromSD(imageUrl, ImageSaveDir.cachekey);
					if(drawable ==null) 
						drawable =ImageSaveManage.loadDrawableFromUrl(imageUrl, ImageSaveDir.cachekey);
				} catch (Exception e) {
//					e.printStackTrace();
				}
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
		}.start();
		return null;
	}

	// å›žè°ƒæŽ¥å�£
	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, ImageView imageView,
				String imageUrl);
	}
}
