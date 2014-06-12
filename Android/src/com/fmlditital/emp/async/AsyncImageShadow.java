package com.fmlditital.emp.async;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.fmlditital.emp.manage.ImageSaveManage;
import com.fmlditital.emp.manage.ImageSaveManage.ImageSaveDir;
import com.fmlditital.emp.tool.ImageTools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class AsyncImageShadow extends AsyncTask<ImageView, Void, Bitmap> {

	private ImageView gView;

	protected Bitmap doInBackground(ImageView... views) {
		Bitmap bmp = null;
		ImageView view = views[0];

		bmp = ImageSaveManage.getBitmapFromSD(view.getTag().toString(),
				ImageSaveDir.cachekey);
		if (bmp != null)
			return bmp;
		bmp = ImageSaveManage.loadBitmapFromUrl(view.getTag().toString(),
				ImageSaveDir.cachekey);

		this.gView = view;
		return bmp;
	}

	protected void onPostExecute(Bitmap bm) {
		if (bm != null) {

			bm = ImageTools.createReflectionImageWithOrigin(bm);
			this.gView.setImageBitmap(bm);
			this.gView = null;
		}
	}
}
