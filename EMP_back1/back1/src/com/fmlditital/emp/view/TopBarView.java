package com.fmlditital.emp.view;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

	private TextView titleView = null;
	private String title = null;
	private Context context = null;

	public TopBarView(Context context, String title) {
		super(context);

		this.context = context;
		this.title = title;

		topbar_BG_color = uiConfig.getTopbar_background_color();
		title_color=uiConfig.getTopbar_text_color();
		appStyle = Confi.getInstance().getAPP_Type();

		this.setBackgroundColor(Color.parseColor(topbar_BG_color));
		
		this.setLayoutParams(new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				Tools.dip2px(context, 35)));

			titleView = new TextView(context);
			titleView.setText(title);
			titleView.setTextColor(Color.parseColor(title_color));
			titleView.setTextSize(16);
			
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.FILL_PARENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);

			if (appStyle.equals(Global.APP_STYLE[0])) {
				layoutParams.leftMargin=10;
				layoutParams.addRule(RelativeLayout.LEFT_OF | RelativeLayout.CENTER_VERTICAL);
				 
			} else if (appStyle.equals(Global.APP_STYLE[1])) {
				layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
				 
			} else if (appStyle.equals(Global.APP_STYLE[2])) {
//				 
				layoutParams.leftMargin=5;
				layoutParams.addRule(RelativeLayout.LEFT_OF | RelativeLayout.CENTER_VERTICAL);
				 
			}
			
			this.addView(titleView,layoutParams);
			
	}

}
