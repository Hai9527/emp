package com.fmlditital.emp.adapter;

import java.text.SimpleDateFormat;
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
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.CommentModel;

public class CommentListTwoAdapter extends AdapterViewAdapter {
	public CommentListTwoAdapter(Context context, List<BaseModel> data) {
		super(context, data);
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.comment_list_item2, null);
			holder = new ViewHolder();
			holder.username = (TextView) convertView
					.findViewById(R.id.comment_list_item_username);
			holder.time = (TextView) convertView
					.findViewById(R.id.comment_list_item_time);
			holder.context = (TextView) convertView
					.findViewById(R.id.comment_list_item_context);

			holder.icon = (ImageView) convertView
					.findViewById(R.id.comment_list_item_imageView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.username.setText(((CommentModel) data.get(position))
				.getUserName());

		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String date = sDateFormat.format(new java.util.Date());

		holder.time.setText(date);

		holder.context.setText(((CommentModel) data.get(position))
				.getCommentDetail());

		// for ui
		holder.username.setTextColor(Color.parseColor(Confi.getInstance()
				.getuIConfig().getApp_text_color()));
		holder.time.setTextColor(Color.parseColor(Confi.getInstance()
				.getuIConfig().getApp_text_color()));
		holder.context.setTextColor(Color.parseColor(Confi.getInstance()
				.getuIConfig().getApp_text_color()));

		String imageUrl = ((CommentModel) data.get(position)).getUserPortrait();

		Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,
				holder.icon, new ImageCallback() {

					public void imageLoaded(Drawable imageDrawable,
							ImageView imageView, String imageUrl) {
						imageView.setImageDrawable(imageDrawable);
					}
				});
		if (cachedImage == null) {
			holder.icon.setImageResource(R.drawable.icon);
		} else {
			holder.icon.setImageDrawable(cachedImage);
		}

		return convertView;
	}

	final class ViewHolder {
		TextView username, time, context;
		ImageView icon;
	}
}
