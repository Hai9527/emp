package com.fmlditital.emp.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fmlditital.emp.async.AsyncImageLoader;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.model.BaseModel;

public abstract class AdapterViewAdapter extends BaseAdapter {

	// protected LayoutInflater mInflater;
	// protected List<BaseModel> data;
	//
	// protected List<Integer> lstPosition = new ArrayList<Integer>();
	// protected List<View> lstView = new ArrayList<View>();
	// protected UIConfig uiConfig = Confi.getInstance().getuIConfig();
	//
	// protected static String appStyle = Confi.getInstance().getAPP_Type();
	//
	// protected Context context = null;

	protected LayoutInflater mInflater;
	protected List<BaseModel> data;

	protected UIConfig uiConfig = Confi.getInstance().getuIConfig();

	protected static String appStyle = Confi.getInstance().getAPP_Type();

	protected AsyncImageLoader asyncImageLoader = null;

	protected Context context = null;

	public AdapterViewAdapter(Context context, List<BaseModel> data) {
		// mInflater = LayoutInflater.from(context);
		// this.data = data;
		// // asyncImageLoader = new AsyncImageLoader();
		// this.context = context;

		mInflater = LayoutInflater.from(context);
		this.data = data;
		asyncImageLoader = new AsyncImageLoader();
		this.context = context;
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

	
	public abstract View getView(int position, View convertView,
			ViewGroup parent);
}
