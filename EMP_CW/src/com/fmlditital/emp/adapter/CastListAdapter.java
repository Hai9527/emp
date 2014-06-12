package com.fmlditital.emp.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.adapter.ArticleAdapter.ViewHolder;
import com.fmlditital.emp.async.AsyncListViewImage;
import com.fmlditital.emp.async.AsyncImageLoader.ImageCallback;
import com.fmlditital.emp.model.AriticleModel;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.CastModel;
import com.fmlditital.emp.model.Global;

public class CastListAdapter extends AdapterViewAdapter {

	public CastListAdapter(Context context, List<BaseModel> data) {
		super(context, data);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {


		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.cast_list_item, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView
					.findViewById(R.id.cast_list_item_textView);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.cast_list_item_imageView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText(((CastModel) data.get(position)).getTitle());
		//
		// // for ui
		holder.title
				.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
		holder.title.setTextSize(16);

		final String imageUrl = ((CastModel) data.get(position)).getIcon();

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
		TextView title;
		ImageView icon;
	}

}
