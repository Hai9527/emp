package com.fmlditital.emp.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.model.DownloadModel;
import com.fmlditital.emp.tool.Tools;

public class DownloadedAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<DownloadModel> data;

	private Context context = null;

	private UIConfig uiConfig = Confi.getInstance().getuIConfig();

	public DownloadedAdapter(Context context, List<DownloadModel> data) {
		mInflater = LayoutInflater.from(context);
		this.data = data;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return (DownloadModel) data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.downloaded_iist_item, null);

			holder = new ViewHolder();
			holder.title = (TextView) convertView
					.findViewById(R.id.downloaded_iist_item_title);
			holder.title.setTextColor(Color.parseColor(uiConfig
					.getTopbar_text_color()));
			holder.size = (TextView) convertView
					.findViewById(R.id.downloaded_iist_item_size);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		//
		// // Bind the data efficiently with the holder.
		// holder.title.setTextColor(context.getResources().getColor(
		// listitemTextColor));
		// holder.time.setTextColor(context.getResources().getColor(
		// listitemTextColor));
		// holder.size.setTextColor(context.getResources().getColor(
		// listitemTextColor));

		int totalSize = data.get(position).getTotalSize();
		// Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>>totalSize "+totalSize);

		holder.title.setText(data.get(position).getTitle());
		holder.size.setText(Tools.translateKToM(totalSize));

		return convertView;
	}

	final class ViewHolder {
		TextView title, size;
	}

}
