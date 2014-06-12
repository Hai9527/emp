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
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.GalleryModel;

/**
 * @author 相册数据
 */
public class GalleryGalleryAdapter extends AdapterViewAdapter {

	public GalleryGalleryAdapter(Context context, List<BaseModel> data) {
		super(context, data);
		// Log.d("GalleryGalleryAdapter ","Create");
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		// ImageView i = new ImageView(context);

		// Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl, i,
		// new ImageCallback() {
		//
		// public void imageLoaded(Drawable imageDrawable,
		// ImageView imageView, String imageUrl) {
		// // System.out.println("getView ::::::: "+imageDrawable);
		// if(imageDrawable==null){
		// return;
		// }
		// imageDrawable=ImageTools.createReflectionDrawable(imageDrawable);
		// imageView.setImageDrawable(imageDrawable);
		// }
		// });
		// i.setScaleType(ImageView.ScaleType.FIT_XY);
		// i.setLayoutParams(new Gallery.LayoutParams(100, 80));
		// i.setLayoutParams(new Gallery.LayoutParams(80, 120));
		// i.setLayoutParams(new GridView.LayoutParams(Tools.dip2px(context,
		// 70),
		// Tools.dip2px(context, 100)));
		// i.setLayoutParams(new Gallery.Layou tParams(Tools.dip2px(context,
		// 60),
		// Tools.dip2px(context, 90)));

		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.gridview_item,
					null);
			holder = new ViewHolder();
			holder. title = (TextView) convertView
					.findViewById(R.id.gridview_item_itextView);
//			holder. count = (TextView) convertView
//					.findViewById(R.id.gallery_list_item_count);
			holder. icon = (ImageView) convertView
					.findViewById(R.id.gridview_item_iimageView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText(((GalleryModel)data.get(position)).getgName()+" ("+((GalleryModel)data.get(position)).getCount()+")");
//		holder.count.setText(" ("+((GalleryModel)data.get(position)).getCount()+")");
		holder.title.setTextColor(Color.parseColor(Confi.getInstance().getuIConfig().getApp_text_color()));
//		holder.count.setTextColor(Color.parseColor(Confi.getInstance().getuIConfig().getApp_text_color()));
		
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
		TextView title;
		ImageView icon;
	}
}
