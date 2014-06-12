package com.fmlditital.emp.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fmlditital.emp.LoadingActivity;
import com.fmlditital.emp.R;
import com.fmlditital.emp.base.DefaultActivity;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.tool.Tools;

public class TopBarView extends RelativeLayout {

	private String appStyle = null;
	private String topbar_BG_color = null;
	private String title_color = null;
	private UIConfig uiConfig = Confi.getInstance().getuIConfig();

	public static TextView titleView = null;
	private String title = null;
	private Context context = null;
	public static Button topButton;
	LayoutInflater mInflater;
	RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
			RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

	public TopBarView(Context context, String title) {
		super(context);

		this.context = context;
		this.title = title;
		mInflater = LayoutInflater.from(context);
		View topview = mInflater.inflate(R.layout.topbar, null);

		topbar_BG_color = uiConfig.getTopbar_background_color();

		title_color = uiConfig.getTopbar_text_color();

		appStyle = Confi.getInstance().getAPP_Type();

		RelativeLayout topbarbg = (RelativeLayout) topview
				.findViewById(R.id.topbarBg);
		topbarbg.setBackgroundColor(Color.parseColor(topbar_BG_color));
		// this.setBackgroundColor(Color.parseColor(topbar_BG_color));
		topbarbg.setLayoutParams(new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT, Tools.dip2px(context,
						38)));
		
		// this.setLayoutParams(new RelativeLayout.LayoutParams(
		// RelativeLayout.LayoutParams.FILL_PARENT, Tools.dip2px(context,
		// 35)));
		topButton = (Button) topview.findViewById(R.id.TopBarView_button);
		topButton.setText("popular");
		topButton.setTextSize(16);
		topButton.setVisibility(View.GONE);
		if(!title.equals("Video"))
		{
			topButton.setVisibility(View.GONE);
		}
		titleView = (TextView) topview.findViewById(R.id.TopBarView_textview);
		titleView.setText(title);
		titleView.setTextColor(Color.parseColor(title_color));
		titleView.setTextSize(21);

		if (appStyle.equals(Global.APP_STYLE[0])) {

			layoutParams.leftMargin = 10;
			layoutParams.addRule(RelativeLayout.LEFT_OF
					| RelativeLayout.CENTER_VERTICAL);
			titleView.setLayoutParams(layoutParams);

		} else if (appStyle.equals(Global.APP_STYLE[1])) {
			layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			titleView.setLayoutParams(layoutParams);
		} else if (appStyle.equals(Global.APP_STYLE[2])) {
			//
			layoutParams.leftMargin = 5;
			layoutParams.addRule(RelativeLayout.LEFT_OF
					| RelativeLayout.CENTER_VERTICAL);
			titleView.setLayoutParams(layoutParams);
		}

		this.addView(topview);

	}

}
