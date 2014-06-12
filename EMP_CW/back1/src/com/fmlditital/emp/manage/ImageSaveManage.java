package com.fmlditital.emp.manage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.tool.SDCardState;

public class ImageSaveManage {

	public static enum ImageSaveDir {
		cachekey, appkey, downloadkey
	}

	private static Map<String, String> imageTableMap = Confi.getInstance()
			.getImageTableMap();

	/**
	 * 从网络获取 Bitmap
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap loadBitmapFromUrl(String url, ImageSaveDir dir) {
		if (imageTableMap.get(url) != null) {
			imageTableMap.put(url, url);
		}
		URL m = null;
		Bitmap bitmap = null;
		InputStream i = null;
		try {
			m = new URL(url);
			i = (InputStream) m.getContent();
		} catch (MalformedURLException e1) {
			return null;
		} catch (IOException e) {
			return null;
		}
		if (m != null) {
			// 图片大小大于300k时显示为原图1/4
			if (m.getFile().length() > 300 * 1024) {
				BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
				// 设置图片长宽为原图的1/2
				bitmapOptions.inSampleSize = 2;
				bitmap = BitmapFactory.decodeStream(i, null, bitmapOptions);
			} else {
				bitmap = BitmapFactory.decodeStream(i);
			}
		}

		if (bitmap != null) {
			Log.v("",
					">>>>>>>>>>>>>>>>>>bitmap.getWidth()   "
							+ bitmap.getWidth());
			Log.v("",
					">>>>>>>>>>>>>>>>>>bitmap.getHeight()  "
							+ bitmap.getHeight());
		}

		if (i != null) {
			try {
				i.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (SDCardState.isSDCardAvailable())
			saveToSD(bitmap, url, dir);

		return bitmap;
	}

	/**
	 * 从网络获取 Drawable
	 * 
	 * @param url
	 * @return
	 */
	public static Drawable loadDrawableFromUrl(String url, ImageSaveDir dir) {
		if (imageTableMap.get(url) != null) {
			imageTableMap.put(url, url);
		}
		Bitmap bitmap = null;
		Drawable d = null;

		bitmap = loadBitmapFromUrl(url, dir);

		if (bitmap != null) {
			d = new BitmapDrawable(bitmap);
			System.out.println(">>>>>>>>>>>>>>>>>>bitmap.getWidth()  "
					+ bitmap.getHeight());
			System.out.println(">>>>>>>>>>>>>>>>>>bitmap.getHeight()  "
					+ bitmap.getWidth());
			System.out.println(d.getIntrinsicHeight());
			System.out.println(d.getIntrinsicWidth());
			if (SDCardState.isSDCardAvailable())
				saveToSD(bitmap, url, dir);
		}

		return d;
	}

	/**
	 * 从SD卡读取Drawable 图片
	 * 
	 * @param url
	 * @return
	 */
	public static Drawable getDrawableFromSD(String url, ImageSaveDir dir) {
		if (!SDCardState.isSDCardAvailable())
			return null;
		Bitmap bitmap = getBitmapFromSD(url, dir);
		if (bitmap == null)
			return null;

		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}

	/**
	 * 从SD卡读取Drawable 图片的文件
	 * 
	 * @param url
	 * @return
	 */
	public static File getDrawablePatch(String url) {
		if (!SDCardState.isSDCardAvailable())
			return null;

		// String fileName = url.substring(url.lastIndexOf("/") + 1,
		// url.length());
		String fileName = url.substring(url.lastIndexOf("/") + 1,
				url.lastIndexOf("."));

		File fileDir = new File(Global.THE_APP_FILE_NAME + File.separator
				+ "cache");
		if (!fileDir.exists())
			fileDir.mkdir();

		File f = new File(fileDir + File.separator + fileName);
		return f;
	}

	/**
	 * 从SD卡获取图片 Bitmap
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap getBitmapFromSD(String path, ImageSaveDir dir) {
		Bitmap bm = null;
		if (imageTableMap.size() == 0)
			return null;
		for (int i = 0; i < imageTableMap.size(); i++) {

		}

		Iterator iterator = imageTableMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = (String) imageTableMap.get(key);
			if (value.equals(path)) {
				String fileName = path.substring(path.lastIndexOf("/") + 1,
						path.length());
				File fileDir = null;
				if (dir == ImageSaveDir.cachekey)
					fileDir = new File(Global.THE_APP_FILE_NAME
							+ File.separator + "cache");
				else if (dir == ImageSaveDir.appkey)
					fileDir = new File(Global.THE_APP_FILE_NAME
							+ File.separator + "appcation");

				bm = BitmapFactory.decodeFile(fileDir + File.separator
						+ fileName);
				break;
			} else {
				imageTableMap.remove(path);
			}
		}

		return bm;
	}

	/**
	 * 保存到SD卡
	 * 
	 * @param photo
	 * @param path
	 */
	public static void saveToSD(Bitmap photo, String path, ImageSaveDir dir) {
		if (!SDCardState.isSDCardAvailable())
			return;
		if (photo == null)
			return;
		String fileName = path.substring(path.lastIndexOf("/") + 1,
				path.lastIndexOf("."));

		File fileDir = null;
		if (dir == ImageSaveDir.cachekey)
			fileDir = new File(Global.THE_APP_FILE_NAME + File.separator
					+ "cache");
		else if (dir == ImageSaveDir.appkey)
			fileDir = new File(Global.THE_APP_FILE_NAME + File.separator
					+ "appcation");

		if (!fileDir.exists())
			fileDir.mkdir();

		File f = new File(fileDir + File.separator + fileName);
		if (f.exists())
			f.delete();

		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		photo.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
