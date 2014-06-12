package com.fmlditital.emp.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.async.AsyncImageLoader.ImageCallback;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.EventModel;

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
			// holder.month = (TextView) convertView
			// .findViewById(R.id.event_listview_item_month);
			// holder.number = (TextView) convertView
			// .findViewById(R.id.event_listview_item_number);

			holder.title = (TextView) convertView
					.findViewById(R.id.event_listview_item_title);
			holder.begintime = (TextView) convertView
					.findViewById(R.id.event_listview_item_begintime);
			holder.location = (TextView) convertView
					.findViewById(R.id.event_listview_item_location);

			holder.monthlinearLayout = (ImageView) convertView
					.findViewById(R.id.event_listview_item_monthlinearLayout);
			holder.attend = (TextView) convertView
					.findViewById(R.id.event_listview_item_attend);
			holder.mightAttend = (TextView) convertView
					.findViewById(R.id.event_listview_item_mightAttend);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText(((EventModel) data.get(position)).getTitle());
		holder.begintime.setText(((EventModel) data.get(position))
				.getBegintime());
		holder.location
				.setText(((EventModel) data.get(position)).getLocation());
		// holder.attend.setText(((EventModel) data.get(position)).getAttend()
		// + " Attend");
		holder.attend.setText(((EventModel) data.get(position)).getAttend()
				+ "参加");
		// holder.mightAttend.setText(((EventModel) data.get(position))
		// .getMightAttend() + " Might Attend");
		holder.mightAttend.setText(((EventModel) data.get(position))
				.getMightAttend() + "可能参加");
		// System.out.println("::::::: "+((EventModel) data.get(position)));
		// holder.month.setText(Tools.getMonthTime(((EventModel) data
		// .get(position)).getBegintime()));
		// holder.number.setText(Tools.getNumberTime(((EventModel) data
		// .get(position)).getBegintime()));

		// for ui
		// holder.month.setTextColor(Color.parseColor(uiConfig
		// .getTopbar_text_color()));
		// holder.number.setTextColor(Color.parseColor(uiConfig
		// .getTopbar_text_color()));

		holder.title
				.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
		holder.begintime.setTextColor(Color.parseColor(uiConfig
				.getApp_text_color()));
		holder.location.setTextColor(Color.parseColor(uiConfig
				.getApp_text_color()));
		holder.attend.setTextColor(Color.parseColor(uiConfig
				.getApp_text_color()));
		holder.mightAttend.setTextColor(Color.parseColor(uiConfig
				.getApp_text_color()));
		final String imageUrl = ((EventModel) data.get(position)).getImage();
		final Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,
				holder.monthlinearLayout, new ImageCallback() {

					public void imageLoaded(Drawable imageDrawable,
							ImageView imageView, String imageUrl) {
						imageView.setImageDrawable(imageDrawable);
					}
				});
		if (cachedImage == null) {
			holder.monthlinearLayout.setImageResource(R.drawable.icon);
		} else {
			holder.monthlinearLayout.setImageDrawable(cachedImage);
		}
		// holder.monthlinearLayout.setColorFilter(
		// Color.parseColor(uiConfig.getTopbar_background_color()),
		// Mode.SRC_ATOP);

		// if (appStyle.equals(Global.APP_STYLE[0])) {
		// holder.monthlinearLayout
		// .setBackgroundResource(R.drawable.event_list_style1_1);
		// holder.monthlinearLayout.setColorFilter(Color.BLACK,
		// Mode.SRC_ATOP);

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
		TextView title, begintime, location, attend, mightAttend;
		// LinearLayout monthlinearLayout;
		ImageView monthlinearLayout;
	}

}
