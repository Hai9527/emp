package com.fmlditital.emp.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.adapter.ArticleAdapter.ViewHolder;
import com.fmlditital.emp.async.AsyncListViewImage;
import com.fmlditital.emp.async.AsyncImageLoader.ImageCallback;
import com.fmlditital.emp.base.DefaultActivity;
import com.fmlditital.emp.model.AriticleModel;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.model.VideoModel;
import com.fmlditital.emp.tool.Tools;
import com.fmlditital.emp.video.VideoActivity;
import com.fmlditital.emp.video.VideoDetailActivity;
import com.fmlditital.emp.video.VideoPlayerActivity;
import android.widget.FrameLayout.LayoutParams;

public class VideoAdapter extends AdapterViewAdapter {

	public VideoAdapter(Context context, List<BaseModel> data) {
		super(context, data);
		// TODO Auto-generated constructor stub
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// final int positionTemp = position;
		//
		// if (lstPosition.contains(position) == false) {
		// if (lstPosition.size() > 75) {
		// lstPosition.remove(0);
		// lstView.remove(0);
		// }
		// convertView = mInflater.inflate(R.layout.video_list_item, null);
		//
		// ImageView imageView = (ImageView) convertView
		// .findViewById(R.id.video_listitem_imageView);
		// TextView title = (TextView) convertView
		// .findViewById(R.id.video_listitem_title);
		// LinearLayout titleLinearLayout = (LinearLayout) convertView
		// .findViewById(R.id.video_listitem_titlelinearLayout);
		//
		// ImageView arrowimageView = (ImageView) convertView
		// .findViewById(R.id.video_listitem_imageView_arrow);
		//
		//
		// //have problom
		// LinearLayout commentLinearLayout = (LinearLayout) convertView
		// .findViewById(R.id.video_listitem_commentBG);
		//
		// TextView eye = (TextView) convertView
		// .findViewById(R.id.video_listitem_eyecount);
		// TextView good = (TextView) convertView
		// .findViewById(R.id.video_listitem_goodcount);
		// TextView comment = (TextView) convertView
		// .findViewById(R.id.video_listitem_commentcount);
		//
		// ImageView eyeimageView = (ImageView) convertView
		// .findViewById(R.id.video_listitem_eye);
		// ImageView goodimageView = (ImageView) convertView
		// .findViewById(R.id.video_listitem_good);
		// ImageView commentimageView = (ImageView) convertView
		// .findViewById(R.id.video_listitem_comment);
		//
		// // for data
		//
		// title.setText(((VideoModel) data.get(position)).getTitle());
		//
		// eye.setText(((VideoModel) data.get(position)).getPlayers_count()
		// + "");
		// good.setText(((VideoModel) data.get(position)).getLikes_count()
		// + "");
		// comment.setText(((VideoModel) data.get(position))
		// .getComment_count() + "");
		//
		// // for ui
		//
		// titleLinearLayout
		// .setBackgroundResource(R.drawable.video_list_name_bg1);
		//
		// //
		// commentLinearLayout.setBackgroundResource(Color.parseColor(uiConfig
		// .getApp_background_color()));
		//
		// title.setTextColor(Color.parseColor(uiConfig
		// .getApp_text_color()));
		// eye.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
		// good.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
		// comment.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
		//
		// if (uiConfig.getApp_icon_type()==2) {
		// eyeimageView.setImageResource(R.drawable.eye_white);
		// goodimageView.setImageResource(R.drawable.good_white);
		// commentimageView.setImageResource(R.drawable.comment_white);
		//
		// arrowimageView.setImageResource(R.drawable.arrow1);
		// } else if (uiConfig.getApp_icon_type()==0) {
		// eyeimageView.setImageResource(R.drawable.eye_black);
		// goodimageView.setImageResource(R.drawable.good_black);
		// commentimageView.setImageResource(R.drawable.comment_black);
		// arrowimageView.setImageResource(R.drawable.go_button);
		// } else if (uiConfig.getApp_icon_type()==1) {
		// eyeimageView.setImageResource(R.drawable.eye_gray);
		// goodimageView.setImageResource(R.drawable.good_gray);
		// commentimageView.setImageResource(R.drawable.comment_gray);
		// arrowimageView.setImageResource(R.drawable.go_button);
		//
		// }
		// if (appStyle.equals(Global.APP_STYLE[0])) {
		// eyeimageView.setImageResource(R.drawable.eye_white);
		// goodimageView.setImageResource(R.drawable.good_white);
		// commentimageView.setImageResource(R.drawable.comment_white);
		//
		// arrowimageView.setImageResource(R.drawable.arrow1);
		// } else if (appStyle.equals(Global.APP_STYLE[1])) {
		// eyeimageView.setImageResource(R.drawable.eye_black);
		// goodimageView.setImageResource(R.drawable.good_black);
		// commentimageView.setImageResource(R.drawable.comment_black);
		// arrowimageView.setImageResource(R.drawable.go_button);
		// } else if (appStyle.equals(Global.APP_STYLE[2])) {
		// eyeimageView.setImageResource(R.drawable.eye_gray);
		// goodimageView.setImageResource(R.drawable.good_gray);
		// commentimageView.setImageResource(R.drawable.comment_gray);
		// arrowimageView.setImageResource(R.drawable.go_button);
		//
		// }
		//
		// imageView.requestFocus();
		// imageView.setFocusable(true);
		// imageView.setOnClickListener(new OnClickListener() {
		//
		//
		// public void onClick(View v) {
		// String videoName = ((VideoModel)
		// data.get(positionTemp)).getTitle();
		// Intent intent = new Intent(context,
		// VideoDetailActivity.class);
		// Bundle bundle = new Bundle();
		// bundle.putString("tabName",
		// ((Activity)context).getIntent().getExtras().getString("tabName"));
		// bundle.putString("videoName", videoName);
		// bundle.putString("tid",
		// ((Activity)context).getIntent().getExtras().getString("tid"));
		// bundle.putInt("index", positionTemp);
		// intent.putExtras(bundle);
		// context.startActivity(intent);
		// }
		//
		// });
		//
		// String imageUrl = ((VideoModel) data.get(position)).getThumb();
		// imageView.setTag(imageUrl);
		//
		// new AsyncListViewImage().execute(imageView);
		//
		// lstPosition.add(position);
		// lstView.add(convertView);
		// } else {
		// convertView = lstView.get(lstPosition.indexOf(position));
		// }

		final ViewHolder holder;
		final int positionTemp = position;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.video_list_item, null);
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.video_listitem_imageView);
			holder.title = (TextView) convertView
					.findViewById(R.id.video_listitem_title);
			holder.titleLinearLayout = (LinearLayout) convertView
					.findViewById(R.id.video_listitem_titlelinearLayout);

			// holder.arrowimageView = (ImageView) convertView
			// .findViewById(R.id.video_listitem_imageView_arrow);

			// have problom
			// holder.commentLinearLayout = (LinearLayout) convertView
			// .findViewById(R.id.video_listitem_commentBG);

			holder.eye = (TextView) convertView
					.findViewById(R.id.video_listitem_eyecount);
			holder.good = (TextView) convertView
					.findViewById(R.id.video_listitem_goodcount);
			holder.comment = (TextView) convertView
					.findViewById(R.id.video_listitem_commentcount);
			holder.eyeer = (TextView) convertView
					.findViewById(R.id.video_listitem_eyecounter);
			holder.gooder = (TextView) convertView
					.findViewById(R.id.video_listitem_goodcounter);
			holder.commenter = (TextView) convertView
					.findViewById(R.id.video_listitem_commentcounter);

			// holder.eyeimageView = (ImageView) convertView
			// .findViewById(R.id.video_listitem_eye);
			// holder.goodimageView = (ImageView) convertView
			// .findViewById(R.id.video_listitem_good);
			// holder.commentimageView = (ImageView) convertView
			// .findViewById(R.id.video_listitem_comment);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// for data

		holder.title.setText(((VideoModel) data.get(position)).getTitle());

		holder.eye.setText(((VideoModel) data.get(position)).getPlayers_count()
				+ "");
		holder.good.setText(((VideoModel) data.get(position)).getLikes_count()
				+ "");
		holder.comment.setText(((VideoModel) data.get(position))
				.getComment_count() + "");

		// for ui

		// holder.titleLinearLayout
		// .setBackgroundResource(R.drawable.video_list_name_bg1);

		holder.title
				.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
		holder.eye.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
		holder.good
				.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
		holder.comment.setTextColor(Color.parseColor(uiConfig
				.getApp_text_color()));
		holder.eyeer
				.setTextColor(Color.parseColor(uiConfig.getApp_text_color()));
		holder.gooder.setTextColor(Color.parseColor(uiConfig
				.getApp_text_color()));
		holder.commenter.setTextColor(Color.parseColor(uiConfig
				.getApp_text_color()));
		if (uiConfig.getApp_icon_type().equals("2")) {
			// holder.eyeimageView.setImageResource(R.drawable.eye_white);
			// holder.goodimageView.setImageResource(R.drawable.good_white);
			// holder.commentimageView.setImageResource(R.drawable.comment_white);

			// holder.arrowimageView.setImageResource(R.drawable.arrow1);
		} else if (uiConfig.getApp_icon_type().equals("0")) {
			// holder.eyeimageView.setImageResource(R.drawable.eye_black);
			// holder.goodimageView.setImageResource(R.drawable.good_black);
			// holder.commentimageView.setImageResource(R.drawable.comment_black);
			// holder.arrowimageView.setImageResource(R.drawable.go_button);
		} else if (uiConfig.getApp_icon_type().equals("1")) {
			// holder.eyeimageView.setImageResource(R.drawable.eye_gray);
			// holder.goodimageView.setImageResource(R.drawable.good_gray);
			// holder.commentimageView.setImageResource(R.drawable.comment_gray);
			// holder.arrowimageView.setImageResource(R.drawable.go_button);

		}

		final String imageUrl = ((VideoModel) data.get(position)).getThumb();

		Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,
				holder.imageView, new ImageCallback() {

					public void imageLoaded(Drawable imageDrawable,
							ImageView imageView, String imageUrl) {
						imageView.setImageDrawable(imageDrawable);
					}
				});
		if (cachedImage == null) {
			holder.imageView.setImageResource(R.drawable.icon);
		} else {
			holder.imageView.setImageDrawable(cachedImage);
		}

		// holder.eyeimageView.setFocusable(true);
		// holder.eyeimageView.setOnClickListener(new OnClickListener() {
		//
		//
		// public void onClick(View v) {
		//
		// playVideo();
		// }
		// });

		holder.imageView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				VideoModel model = (VideoModel) data.get(positionTemp);
				String videoName = model.getTitle();

				Intent intent = new Intent(context, VideoDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("tabName", ((Activity) context).getIntent()
						.getExtras().getString("tabName"));
				bundle.putString("videoName", videoName);
				bundle.putString("tid", ((Activity) context).getIntent()
						.getExtras().getString("tid"));
				bundle.putInt("index", positionTemp);
				bundle.putString("type", VideoActivity.currenttype);
				intent.putExtras(bundle);

				context.startActivity(intent);
			}

		});
		// System.out.println("width:::::"
		// + holder.imageView.getLayoutParams().width);
		// Log.d("",DefaultActivity.g+
		// "===1====videoActivity======="+newsImageView.getVisibility());
		return convertView;
	}

	final class ViewHolder {
		TextView title, eye, good, comment, eyeer, gooder, commenter;
		ImageView imageView, arrowimageView, eyeimageView, goodimageView,
				commentimageView;
		LinearLayout titleLinearLayout, commentLinearLayout;
	}

	private void playVideo() {
		VideoDetailActivity videoDetail = new VideoDetailActivity();
		Intent intent = new Intent(context, VideoPlayerActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("url",
				((VideoModel) data.get(videoDetail.vindex)).getUrl());
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
}
