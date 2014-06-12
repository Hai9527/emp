package com.fmlditital.emp.adapter;

import java.util.List;
import java.util.Map;

import com.fmlditital.emp.R;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class EventDetailAdapter extends SimpleAdapter {
	Context myContext;
	private UIConfig uiConfig = Confi.getInstance().getuIConfig();

	public EventDetailAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		myContext = context;
		// TODO Auto-generated constructor stub
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(myContext).inflate(
				R.layout.listviewtotextview, parent, false);
		TextView txtOne = (TextView) convertView
				.findViewById(R.id.listview_textview_txtOne);
		txtOne.setText("dsfsdf");
		txtOne.setTextColor(Color.parseColor(uiConfig.getTopbar_text_color()));
		TextView txtTwo = (TextView) convertView
				.findViewById(R.id.listview_textview_txtTwo);
		txtTwo.setTextColor(Color.parseColor(uiConfig.getTopbar_text_color()));
		TextView txtThree = (TextView) convertView
				.findViewById(R.id.listview_textview_txtThree);
		txtThree.setTextColor(Color.parseColor(uiConfig.getTopbar_text_color()));
		TextView txtFour = (TextView) convertView
				.findViewById(R.id.listview_textview_txtFour);
		txtFour.setTextColor(Color.parseColor(uiConfig.getTopbar_text_color()));

		return convertView;
	}
}
