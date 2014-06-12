package com.fmlditital.emp.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.async.AsyncImageLoader;
import com.fmlditital.emp.async.AsyncImageLoader.ImageCallback;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.model.AriticleModel;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.Global;

public class ArticleAdapter extends AdapterViewAdapter {
	
	public ArticleAdapter(Context context, List<BaseModel> data) {
		super(context, data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.artticle_listview_item,
					null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView
					.findViewById(R.id.artticle_listview_item_title);
			holder.time = (TextView) convertView
					.findViewById(R.id.artticle_listview_item_time);
			holder.summary = (TextView) convertView
					.findViewById(R.id.artticle_listview_item_summary);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.artticle_listview_item_imageView);
			holder.count = (TextView) convertView
					.findViewById(R.id.artticle_listview_commentcount);

			holder.countLinearLayout = (LinearLayout) convertView
					.findViewById(R.id.artticle_listview_item_linearLayout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// // for ui
		holder.title
				.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
		holder.summary.setTextColor(Color.parseColor(uiConfig
				.getApp_text_color()));
		holder.time
				.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
		holder.count.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));

		if (appStyle.equals(Global.APP_STYLE[0])) {
			holder.countLinearLayout
					.setBackgroundResource(R.drawable.news_comment_count_3);

		} else if (appStyle.equals(Global.APP_STYLE[1])) {
			holder.countLinearLayout
					.setBackgroundResource(R.drawable.news_comment_count_3);
		} else if (appStyle.equals(Global.APP_STYLE[2])) {
			holder.countLinearLayout
					.setBackgroundResource(R.drawable.news_comment_count_3);
		}

		holder.title.setText(((AriticleModel) data.get(position)).getTitle());
		holder.summary.setText(((AriticleModel) data.get(position))
				.getSummary());
		holder.time.setText(((AriticleModel) data.get(position)).getTime());
		holder.count.setText(((AriticleModel) data.get(position))
				.getComment_count() + "");

		final String imageUrl = ((AriticleModel) data.get(position))
				.getThumbUrl();

		
		final Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,
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
		TextView title, time, summary, count;
		ImageView icon;
		LinearLayout countLinearLayout;
	}

}
