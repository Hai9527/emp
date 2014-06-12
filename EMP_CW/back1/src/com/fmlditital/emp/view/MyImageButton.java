package com.fmlditital.emp.view;

import com.fmlditital.emp.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MyImageButton extends FrameLayout {
	private ImageView iv;
	private TextView tv;
	private String title = null;

	public String getTitle() {
		return title;
	}

	public MyImageButton(Context context) {
		this(context, null);

		LayoutInflater.from(context)
				.inflate(R.layout.myimagebutton, this, true);
		iv = (ImageView) findViewById(R.id.myImageButton_iv);
		tv = (TextView) findViewById(R.id.myImageButton_tv);
	}

	public MyImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public void setImageResource(int resId) {
		iv.setImageResource(resId);
	}

	public void setTextViewText(String text) {
		title = text;
		tv.setText(text);
	}

	public void setTextViewColor(int c) {
		tv.setTextColor(c);
	}

	public void setImageButtonColor(int drawable, int color) {
		iv.setImageResource(drawable);
		iv.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
	}

	public void setTextSize(int no) {
		tv.setTextSize(no);
	}

	public void setHeight(int height) {
		tv.setHeight(height);
	}

}
