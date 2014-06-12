package com.fmlditital.emp.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.base.DefaultActivity;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.model.NavigationModel;

public class NavigationGridAdapter extends BaseAdapter {

	private UIConfig uiConfig = null;

	LayoutInflater inflater;
	private List<NavigationModel> data;
	private Context context = null;

	public NavigationGridAdapter(Context context, List<NavigationModel> data) {
		this.context = context;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.data = data;

		uiConfig = Confi.getInstance().getuIConfig();
	}

	
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.tabwidget, null);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView
					.findViewById(R.id.tabwidget_icon);
			holder.textView = (TextView) convertView
					.findViewById(R.id.tabwidget_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		convertView.setBackgroundColor(Color.parseColor(uiConfig
				.getNavbar_background_color()));

		// LinearLayout.LayoutParams convertViewParams = new LayoutParams(
		// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		// // convertViewParams.height = 120;
		// convertViewParams.height = DefaultActivity.getScreenWidth()/3;
		// // mGridParams.width=DefaultActivity.getScreenWidth()/3;
		// convertView.setLayoutParams(convertViewParams);

//		convertView.getBackground().setAlpha(180);

		holder.icon.setImageDrawable(data.get(position).getIcon());
		System.out.println("NavigationGridAdapter ::::::::::: "+holder.icon.getHeight());
		holder.textView.setTextColor(Color.parseColor(uiConfig
				.getNavbar_text_color()));
		holder.textView.setText(data.get(position).getTitle());
		return convertView;
	}

	final class ViewHolder {
		ImageView icon;
		TextView textView;
	}
}
