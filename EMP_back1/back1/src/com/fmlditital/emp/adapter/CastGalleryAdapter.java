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
import com.fmlditital.emp.async.AsyncImageLoader.ImageCallback;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.CastModel;
import com.fmlditital.emp.tool.ImageTools;
import com.fmlditital.emp.tool.Tools;

public class CastGalleryAdapter extends AdapterViewAdapter {

	public CastGalleryAdapter(Context context, List<BaseModel> data) {
		super(context, data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		final ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.castgallery_listitem, null);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView
					.findViewById(R.id.castgallery_listitem_imageView);
			holder.title = (TextView) convertView
					.findViewById(R.id.castgallery_listitem_title);
			holder.count = (TextView) convertView
					.findViewById(R.id.castgallery_listitem_currentcount);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// if (lstPosition.contains(position) == false) {
		// if (lstPosition.size() > 75) {
		// lstPosition.remove(0);
		// lstView.remove(0);
		// }
		//
		// convertView = mInflater.inflate(R.layout.castgallery_listitem,
		// null);
		//
		// ImageView icon = (ImageView) convertView
		// .findViewById(R.id.castgallery_listitem_imageView);
		// TextView title = (TextView) convertView
		// .findViewById(R.id.castgallery_listitem_title);
		//
		//
		// TextView count = (TextView) convertView
		// .findViewById(R.id.castgallery_listitem_currentcount);
		//
		//
		//
		// for data

		 holder.icon .setLayoutParams(new LinearLayout
		 .LayoutParams(Tools.dip2px(context, 250),Tools.dip2px(context,
		 320)));
		 
		holder.title.setText(((CastModel) data.get(position)).getTitle());

		holder.count.setText(position + 1 + " / " + data.size());

		// for ui

		holder.title.setTextColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));

		holder.count.setTextColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));

		final String imageUrl = ((CastModel) data.get(position)).getIcon();

		Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,
				holder.icon, new ImageCallback() {

					public void imageLoaded(Drawable imageDrawable,
							ImageView imageView, String imageUrl) {
						imageView.setImageDrawable(imageDrawable);
					}
				});
		if (cachedImage == null) {
			// holder.icon.setImageResource(R.drawable.icon);
		} else {
			cachedImage = ImageTools.RoundedCornerDrawable(cachedImage, 20.0f);
			holder.icon.setImageDrawable(cachedImage);
		}

		return convertView;
	}

	final class ViewHolder {
		ImageView icon;
		TextView title, count;
	}

}
