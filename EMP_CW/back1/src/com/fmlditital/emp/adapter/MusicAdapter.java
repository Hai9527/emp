package com.fmlditital.emp.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.adapter.ArticleAdapter.ViewHolder;
import com.fmlditital.emp.async.AsyncImageLoader.ImageCallback;
import com.fmlditital.emp.model.AriticleModel;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.model.MusicModel;

public class MusicAdapter extends AdapterViewAdapter {

	public MusicAdapter(Context context, List<BaseModel> data) {
		super(context, data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

//		if (lstPosition.contains(position) == false) {
//			if (lstPosition.size() > 75) {
//				lstPosition.remove(0);
//				lstView.remove(0);
//			}
//			convertView = mInflater.inflate(R.layout.music_list_item, null);
//
//			TextView text = (TextView) convertView
//					.findViewById(R.id.music_list_item_title);
//			ImageView arrow = (ImageView) convertView
//					.findViewById(R.id.music_list_item_imageView);
//
//			text.setText(((MusicModel) data.get(position)).getTitle());
//
//			text.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
//			if (appStyle.equals(Global.APP_STYLE[0])) {
//				arrow.setImageResource(R.drawable.go_button);
//			} else if (appStyle.equals(Global.APP_STYLE[1])) {
//				arrow.setImageResource(R.drawable.go_button);
//			} else if (appStyle.equals(Global.APP_STYLE[2])) {
//				arrow.setImageResource(R.drawable.go_button);
//			}
//
//			lstPosition.add(position);
//			lstView.add(convertView);
////		} else {
////			convertView = lstView.get(lstPosition.indexOf(position));
////		}
		
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.music_list_item,
					null);
			holder = new ViewHolder();
			holder. text = (TextView) convertView
					.findViewById(R.id.music_list_item_title);
			holder. arrow = (ImageView) convertView
					.findViewById(R.id.music_list_item_imageView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.text.setText(((MusicModel) data.get(position)).getTitle());

		holder.text.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
		if (appStyle.equals(Global.APP_STYLE[0])) {
			holder.arrow.setImageResource(R.drawable.go_button);
		} else if (appStyle.equals(Global.APP_STYLE[1])) {
			holder.arrow.setImageResource(R.drawable.go_button);
		} else if (appStyle.equals(Global.APP_STYLE[2])) {
			holder.arrow.setImageResource(R.drawable.go_button);
		}

		return convertView;
	}

	final class ViewHolder {
		TextView text;
		ImageView arrow;
	}
}
