package com.fmlditital.emp.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.adapter.ArticleAdapter.ViewHolder;
import com.fmlditital.emp.async.AsyncImageLoader.ImageCallback;
import com.fmlditital.emp.model.AriticleModel;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.EventModel;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.tool.Tools;

public class EventAdapter extends AdapterViewAdapter {

	public EventAdapter(Context context, List<BaseModel> data) {
		super(context, data);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.event_listview_item, null);
			holder = new ViewHolder();
			holder.month = (TextView) convertView
					.findViewById(R.id.event_listview_item_month);
			holder.number = (TextView) convertView
					.findViewById(R.id.event_listview_item_number);

			holder.title = (TextView) convertView
					.findViewById(R.id.event_listview_item_title);
			holder.begintime = (TextView) convertView
					.findViewById(R.id.event_listview_item_begintime);
			holder.location = (TextView) convertView
					.findViewById(R.id.event_listview_item_location);

			holder.monthlinearLayout = (ImageView) convertView
					.findViewById(R.id.event_listview_item_monthlinearLayout);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText(((EventModel) data.get(position)).getTitle());
		holder.begintime.setText(((EventModel) data.get(position))
				.getBegintime());
		holder.location
				.setText(((EventModel) data.get(position)).getLocation());

		holder.month.setText(Tools.getMonthTime(((EventModel) data
				.get(position)).getBegintime()));
		holder.number.setText(Tools.getNumberTime(((EventModel) data
				.get(position)).getBegintime()));

		// for ui
		holder.month.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));
		holder.number.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));

		holder.title
				.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
		holder.begintime.setTextColor(Color.parseColor(uiConfig
				.getApp_text_color()));
		holder.location.setTextColor(Color.parseColor(uiConfig
				.getApp_text_color()));

		holder.monthlinearLayout.setColorFilter(
				Color.parseColor(uiConfig.getTopbar_background_color()),
				Mode.SRC_ATOP);

		// if (appStyle.equals(Global.APP_STYLE[0])) {
		// // holder.monthlinearLayout
		// // .setBackgroundResource(R.drawable.event_list_style1_1);
		// // holder.monthlinearLayout.setColorFilter(Color.BLACK,
		// // Mode.SRC_ATOP);
		//
		// holder.monthlinearLayout
		// .setBackgroundResource(R.drawable.event_list_style1_2);
		// holder.monthlinearLayout
		// .setColorFilter(Color.parseColor(uiConfig
		// .getTopbar_background_color()), Mode.SRC_ATOP);
		// } else if (appStyle.equals(Global.APP_STYLE[1])) {
		// holder.monthlinearLayout
		// .setBackgroundResource(R.drawable.event_list_style2);
		// // holder.monthlinearLayout.setColorFilter(
		// // Color.parseColor(uiConfig.getTopbar_background_color()),
		// // Mode.SRC_ATOP);
		// } else if (appStyle.equals(Global.APP_STYLE[2])) {
		// holder.monthlinearLayout
		// .setBackgroundResource(R.drawable.event_list_style3);
		// // holder.monthlinearLayout.setColorFilter(
		// // Color.parseColor(uiConfig.getTopbar_background_color()),
		// // Mode.SRC_ATOP);
		// }

		return convertView;
	}

	final class ViewHolder {
		TextView month, number, title, begintime, location;
		ImageView icon;
		// LinearLayout monthlinearLayout;
		ImageView monthlinearLayout;
	}

}
