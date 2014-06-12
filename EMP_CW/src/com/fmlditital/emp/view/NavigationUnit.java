package com.fmlditital.emp.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

import com.fmlditital.emp.R;

public class NavigationUnit extends LinearLayout {
	private ImageView imageview = null;
	private TextView textView = null;

	private Context context = null;

	public NavigationUnit(Context context, Drawable drawable, String title,
			String titleColor) {
		super(context);
		this.context = context;

		inflate(context, R.layout.tabwidget, this);

		imageview = (ImageView) findViewById(R.id.tabwidget_icon);
		textView = (TextView) findViewById(R.id.tabwidget_name);

		textView.setTextColor(Color.parseColor(titleColor));

		imageview.setImageDrawable(drawable);
		textView.setText(title);

	}

}
