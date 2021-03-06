package com.fmlditital.emp.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.async.AsyncImageLoader;
import com.fmlditital.emp.async.AsyncImageLoader.ImageCallback;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.GalleryModel;
import com.fmlditital.emp.tool.ImageTools;
import com.fmlditital.emp.tool.Tools;

public class GalleryGalleryAdapter extends AdapterViewAdapter {

	public GalleryGalleryAdapter(Context context, List<BaseModel> data) {
		super(context, data);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView i = new ImageView(context);

		i.setScaleType(ImageView.ScaleType.FIT_XY);
		// i.setLayoutParams(new Gallery.LayoutParams(100, 80));
		// i.setLayoutParams(new Gallery.LayoutParams(80, 120));
		i.setLayoutParams(new Gallery.LayoutParams(Tools.dip2px(context, 70),
				Tools.dip2px(context, 100)));
		// i.setLayoutParams(new Gallery.LayoutParams(Tools.dip2px(context, 60),
		// Tools.dip2px(context, 90)));

		final String imageUrl = ((GalleryModel) data.get(position))
				.getgThumbUrl();

		Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl, i,
				new ImageCallback() {

					public void imageLoaded(Drawable imageDrawable,
							ImageView imageView, String imageUrl) {
						imageView.setImageDrawable(imageDrawable);
					}
				});
		if (cachedImage != null) {
			cachedImage = ImageTools.createReflectionDrawable(cachedImage);
			i.setImageDrawable(cachedImage);
			// i.setImageResource(R.drawable.icon);
		} else {
//			try {
//				cachedImage = ImageTools.createReflectionDrawable(cachedImage);
//			} finally {
//				i.setImageDrawable(cachedImage);
//			}
		}

		return i;

	}
}
