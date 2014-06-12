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
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.model.AriticleModel;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.GalleryModel;
import com.fmlditital.emp.model.Global;

/**
 * @author 
 * 相册数据适配器2
 */
public class GalleryListViewAdapter extends AdapterViewAdapter {
 

	public GalleryListViewAdapter(Context context, List<BaseModel> data) {
		super(context, data);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
 
		
		
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.gallery_list_item,
					null);
			holder = new ViewHolder();
			holder. title = (TextView) convertView
					.findViewById(R.id.gallery_list_item_title);
			holder. count = (TextView) convertView
					.findViewById(R.id.gallery_list_item_count);
			holder. icon = (ImageView) convertView
					.findViewById(R.id.gallery_list_item_imageView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText(((GalleryModel)data.get(position)).getgName());
		holder.count.setText(" ("+((GalleryModel)data.get(position)).getCount()+")");
		holder.title.setTextColor(Color.parseColor(Confi.getInstance().getuIConfig().getApp_text_color()));
		holder.count.setTextColor(Color.parseColor(Confi.getInstance().getuIConfig().getApp_text_color()));
		
		String imageUrl = ((GalleryModel) data.get(position))
				.getgThumbUrl();

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
		TextView title, count;
		ImageView icon;
	}
}