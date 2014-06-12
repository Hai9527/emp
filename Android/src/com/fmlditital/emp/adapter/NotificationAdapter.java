package com.fmlditital.emp.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.NotificationModel;
import com.fmlditital.emp.share.ShareManage;
import com.fmlditital.emp.share.info.ShareInfo;
import com.fmlditital.emp.share.info.ShareText;
import com.fmlditital.emp.tool.Tools;

public class NotificationAdapter extends AdapterViewAdapter {

	public NotificationAdapter(Context context, List<BaseModel> data) {
		super(context, data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int positionTemp = position;
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.notification_list_item,
					null);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView
					.findViewById(R.id.notification_list_item_imageView);
			holder.title = (TextView) convertView
					.findViewById(R.id.notification_list_item_title);
			holder.time = (TextView) convertView
					.findViewById(R.id.notification_list_item_time);
			holder.summary = (TextView) convertView
					.findViewById(R.id.notification_list_item_context);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText(((NotificationModel) data.get(position))
				.getTitle());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf
					.parse(((NotificationModel) data.get(position)).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int times = date.getHours();
		String apm = "";
		if (times >= 12) {
			apm = "PM";
		} else {
			apm = "AM";
		}

		holder.time.setText(((NotificationModel) data.get(position)).getTime()
				+ "" + "" + apm);
		holder.summary.setText(((NotificationModel) data.get(position))
				.getContent());

		// for ui
		holder.title
				.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
		holder.time
				.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
		holder.summary.setTextColor(Color.parseColor(uiConfig
				.getApp_text_color()));

		holder.icon.setFocusable(true);
		holder.icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ShareInfo shareInfo = new ShareText();
				shareInfo.constructContext(((NotificationModel) data
						.get(positionTemp)).getTitle());
				ShareManage.getInstance().share2weibo((Activity) context,
						shareInfo);
			}

		});
		holder.icon.setColorFilter(
				Color.parseColor(uiConfig.getTopbar_background_color()),
				Mode.SRC_ATOP);
		return convertView;
	}

	final class ViewHolder {
		TextView title, time, summary;
		ImageView icon;
	}

}
