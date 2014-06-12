package com.fmlditital.emp.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.async.AsyncImageLoader.ImageCallback;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.CastModel;

public class CastGridAdapter extends AdapterViewAdapter {

	public CastGridAdapter(Context context, List<BaseModel> data) {
		super(context, data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.castgrid_item, null);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView
					.findViewById(R.id.castgrid_imageView);

			holder.title = (TextView) convertView
					.findViewById(R.id.castgrid_textView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText(((CastModel) data.get(position)).getTitle());
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
			// holder.icon.setImageResource(R.drawable.icon);
		} else {
			holder.icon.setImageDrawable(cachedImage);

		}
		return convertView;
	}

	final class ViewHolder {
		ImageView icon;
		TextView title;
	}
}
