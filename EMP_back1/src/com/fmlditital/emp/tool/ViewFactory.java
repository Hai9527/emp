package com.fmlditital.emp.tool;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ListView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.adapter.ArticleAdapter;
import com.fmlditital.emp.adapter.CastGalleryAdapter;
import com.fmlditital.emp.adapter.CastGridAdapter;
import com.fmlditital.emp.adapter.CastListAdapter;
import com.fmlditital.emp.adapter.EventAdapter;
import com.fmlditital.emp.adapter.GalleryGalleryAdapter;
import com.fmlditital.emp.adapter.GalleryListViewAdapter;
import com.fmlditital.emp.adapter.MusicAdapter;
import com.fmlditital.emp.adapter.NotificationAdapter;
import com.fmlditital.emp.adapter.VideoAdapter;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.view.CastGalleryView;
import com.fmlditital.emp.view.CoverFlow;

public class ViewFactory {

	public static enum AdapterSelect {
		castkey, gallerykey, newskey, eventkey, musickey, notificationkey, videokey, downloadkey
	}

	private List<Map<String, String>> eq = null;

	// private View view = null;
	private static String appStyle = Confi.getInstance().getAPP_Type();

	public static AdapterView getAdapterView(Context context, AdapterSelect key) {

		AdapterView result = null;

		switch (key) {
		case castkey: {

			if (appStyle.equals(Global.APP_STYLE[0])) { // Music Template
				// Gallery gallery = new Gallery(context);
				Gallery gallery = new CastGalleryView(context);
				// gallery.setSelection(5);
				gallery.setSpacing(-5);
				result = gallery;
			} else if (appStyle.equals(Global.APP_STYLE[1])) { // Event Template
				result = getListView(context);
			} else if (appStyle.equals(Global.APP_STYLE[2])) { // Movie Template
				GridView mGrid = new GridView(context);
				mGrid.setNumColumns(3);
				mGrid.setHorizontalSpacing(2);
				mGrid.setVerticalSpacing(2);
				mGrid.setCacheColorHint(Color.TRANSPARENT);
				result = mGrid;
			}

		}
			break;
		case gallerykey: {
			if (appStyle.equals(Global.APP_STYLE[0])) {
				// Gallery gallery = new Gallery(context) ;
				// gallery.setSelection(5);
				// gallery.setSpacing(-7);
				// gallery.setUnselectedAlpha(0.5f);
				// result=gallery;

				// ListView listView = new ListSlidingView(context);
				// // ListView listView = new ListView(context);
				// listView.setCacheColorHint(0);
				// listView.setDivider(new ColorDrawable(Color.parseColor(Confi
				// .getInstance().getuIConfig().getTopbar_background_color())));
				// listView.setDividerHeight(-80);
				// listView.setFocusableInTouchMode(true);
				// result = listView;

				Gallery gallery = new CoverFlow(context);
				gallery.setSpacing(-20);
				gallery.setUnselectedAlpha(0.5f);
				result = gallery;
			} else if (appStyle.equals(Global.APP_STYLE[1])) {
				result = getListView(context);
			} else if (appStyle.equals(Global.APP_STYLE[2])) {
				Gallery gallery = new CoverFlow(context);
				gallery.setSpacing(-20);
				// gallery.setSpacing(-100);
				gallery.setUnselectedAlpha(0.5f);
				result = gallery;
			}
		}
			break;
		case newskey: {
			if (appStyle.equals(Global.APP_STYLE[0])) {
				result = getListView(context);
			} else if (appStyle.equals(Global.APP_STYLE[1])) {
				result = getListView(context);
			} else if (appStyle.equals(Global.APP_STYLE[2])) {

				result = getListView(context);
			}
		}
			break;

		case eventkey: {
			if (appStyle.equals(Global.APP_STYLE[0])) {
				result = getListView(context);
			} else if (appStyle.equals(Global.APP_STYLE[1])) {
				// list = getTemplateFromXML(1);
				result = getListView(context);
			} else if (appStyle.equals(Global.APP_STYLE[2])) {

				result = getListView(context);
			}
		}
			break;

		case musickey: {

			result = getListView(context);

		}
			break;
		case notificationkey: {

			result = getListView(context);

		}
			break;

		case videokey: {

			result = getListView(context);

		}
			break;
		case downloadkey: {

			result = getListView(context);

		}
			break;
		}

		return result;
	}

	public static BaseAdapter getDataAdapter(Context context,
			AdapterSelect key, List<BaseModel> data) {
		BaseAdapter result = null;

		switch (key) {
		case castkey: {

			if (appStyle.equals(Global.APP_STYLE[0])) {
				// // list = getTemplateFromXML(0);
				result = new CastGalleryAdapter(context, data);
			} else if (appStyle.equals(Global.APP_STYLE[1])) {
				result = new CastListAdapter(context, data);
				// result = new CastGalleryAdapter(context, data);
			} else if (appStyle.equals(Global.APP_STYLE[2])) {
				// result = new CastListAdapter(context, data);
				result = new CastGridAdapter(context, data);
			}
		}
			break;
		case gallerykey: {
			Log.d("galleryKey", "appStyle:::" + appStyle);
			if (appStyle.equals(Global.APP_STYLE[0])) {
				// result = new GalleryGalleryAdapter(context, data);
				result = new GalleryGalleryAdapter(context, data);
				// result = new ListSlidingAdapter(context, data);
			} else if (appStyle.equals(Global.APP_STYLE[1])) {
				result = new GalleryListViewAdapter(context, data);
			} else if (appStyle.equals(Global.APP_STYLE[2])) {
				// result = new GalleryGalleryAdapter(context, data);
				result = new GalleryGalleryAdapter(context, data);
			}

		}
			break;
		case newskey: {
			result = new ArticleAdapter(context, data);
			// result = new ArticleAdapter2(context, data);
			// result = new ArticleAdapter3(context, data);

		}
			break;

		case eventkey: {
			result = new EventAdapter(context, data);
		}
			break;
		case musickey: {
			result = new MusicAdapter(context, data);
		}
			break;
		case notificationkey: {
			result = new NotificationAdapter(context, data);
		}
			break;

		case videokey: {
			result = new VideoAdapter(context, data);
		}
			break;

		}

		return result;
	}

	private static ListView getListView(Context context) {
		ListView listView = new ListView(context);
		listView.setCacheColorHint(0);
		listView.setDivider(new ColorDrawable(Color.parseColor(Confi
				.getInstance().getuIConfig().getTopbar_background_color())));
		listView.setDividerHeight(1);
		listView.setFocusableInTouchMode(true);
		return listView;
	}
	// private static ShareListView getListView(Context context) {
	// ShareListView listView = new ShareListView(context);
	// listView.setCacheColorHint(0);
	// listView.setDivider(new ColorDrawable(Color.parseColor(Confi
	// .getInstance().getuIConfig().getTopbar_background_color())));
	// listView.setDividerHeight(1);
	// listView.setFocusableInTouchMode(true);
	// listView.setBackgroundResource(R.drawable.shape_bg_listview);
	// return listView;
	// }
}
