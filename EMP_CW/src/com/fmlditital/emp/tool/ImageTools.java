package com.fmlditital.emp.tool;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageTools {
	// 放大缩小图片
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return newBmp;
	}

	// 将Drawable转化为Bitmap
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	// 圆角图片
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	// 获得带倒影的图片方法
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
				width, height / 2, matrix, false);

		 Bitmap bitmapWithReflection = Bitmap.createBitmap(width, 
		 (height + height / 4), Config.ARGB_8888);

//		Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + 20),
//				Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
//		Paint deafalutPaint = new Paint();
//		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

//		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		canvas.drawBitmap(reflectionImage, 0, height , null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}
 

	/**
	 * 获得 倒影的drawable
	 * @param drawable
	 * @return
	 */
	public static Drawable createReflectionDrawable(Drawable drawable) {
		if(drawable==null){
			return null;
		}
		Drawable result = null;
		Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
		bitmap = createReflectionImageWithOrigin(bitmap);

		result = new BitmapDrawable(bitmap);

		return result;
	}
 
	 
	/**
	 * 获得 圆角的drawable
	 * @param drawable
	 * @return
	 */
	public static Drawable RoundedCornerDrawable(Drawable drawable, float roundPx) {
		Drawable result = null;
		Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
		bitmap = getRoundedCornerBitmap(bitmap,  roundPx);

		result = new BitmapDrawable(bitmap);

		return result;
	}
	
	/**
	 * Drawable缩放
	 * @param drawable
	 * @param w
	 * @param h
	 * @return
	 */
	public static Drawable zoomDrawable(Drawable drawable, int w, int h) {  
	    int width = drawable.getIntrinsicWidth();  
	    int height = drawable.getIntrinsicHeight();  
	    // drawable转换成bitmap  
	    Bitmap oldbmp = drawableToBitmap(drawable);  
	    // 创建操作图片用的Matrix对象  
	    Matrix matrix = new Matrix();  
	    // 计算缩放比例  
	    float sx = ((float) w / width);  
	    float sy = ((float) h / height);  
	    // 设置缩放比例  
	    matrix.postScale(sx, sy);  
	    // 建立新的bitmap，其内容是对原bitmap的缩放后的图  
	    Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,  
	            matrix, true);  
	    return new BitmapDrawable(newbmp);  
	} 
 
}
