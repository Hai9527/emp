package com.fmlditital.emp.async;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.fmlditital.emp.model.AriticleModel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class AsyncListViewImage2 extends AsyncTask<Object, Object, Void> {
	AriticleModel ariticleModel=null;
	private String url=null;
	@Override
	protected Void doInBackground(Object... params) {

		try {
			ImageView imageView = (ImageView) params[0];
			ariticleModel=(AriticleModel)params[1];
			url=ariticleModel.getThumbUrl();
			
//			String url = (String) params[1];
//			Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>ImageView: url " + url);
//			if(ariticleModel.getThumbBitmap()!=null) 
//				imageView.setImageBitmap(ariticleModel.getThumbBitmap());
//			else {
				Bitmap bitmap = getBitmapByUrl(url);
				publishProgress(new Object[] { imageView, bitmap });
//			}
			
		} catch (MalformedURLException e) {
//			e.printStackTrace();
		} catch (IOException e) {
//			e.printStackTrace();
		}
		return null;
	}

	protected void onProgressUpdate(Object... progress) {
		ImageView imageView = (ImageView) progress[0];
		imageView.setImageBitmap((Bitmap) progress[1]);
		ariticleModel.setThumbBitmap((Bitmap) progress[1]);
	}
	
	  public Bitmap getBitmapByUrl(String urlString)
			throws MalformedURLException, IOException {
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(25000);
		connection.setReadTimeout(90000);
		Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
		return bitmap;
	}

}
