package com.fmlditital.emp.adapter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.GalleryThumbModel;

public class GalleryThumbAdapter extends BaseAdapter{

//	private ArrayList<GalleryThumbModel> mItems = new ArrayList<GalleryThumbModel>();
	private List<BaseModel> mItems =null;

	Context mContext;
	LayoutInflater inflater;

	public GalleryThumbAdapter(Context context,List<BaseModel> data) {
		this.mContext = context;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mItems=data;
		 Log.d("", "GalleryThumbAdapter :::: getView :::::"+mItems.size());
	}

	public void addItem(GalleryThumbModel model) {
		mItems.add(model);
	}

	
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mItems.get(position);
	}

	
	public long getItemId(int position) {
		return position;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Log.d("", "GalleryThumbAdapter :::::::: getView ");
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.gallerythumb_item, null);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView
					.findViewById(R.id.gallerythumb_item_Image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
	    Log.d("", "GalleryThumbAdapter :::: getView "+position+":::::"+((GalleryThumbModel)mItems.get(position)).getpThumbUrl());
//		holder.icon.setImageBitmap(((GalleryThumbModel)mItems.get(position)).getBitmap());
		new AsyncLoadImage().execute(new Object[] { holder.icon,
				((GalleryThumbModel)mItems.get(position)).getpThumbUrl() });
//		new AsyncLoadImage().execute(new Object[] { holder.icon,
//				mItems.get(position).getpThumbUrl() });

		return convertView;
	}
	
	static class ViewHolder {
		ImageView icon;
	}

	class AsyncLoadImage extends AsyncTask<Object, Object, Void> {
		
		protected Void doInBackground(Object... params) {

			try {
				ImageView imageView = (ImageView) params[0];
				String url = (String) params[1];
				Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>ImageView: url " + url);
				Bitmap bitmap = getBitmapByUrl(url);
				publishProgress(new Object[] { imageView, bitmap });
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onProgressUpdate(Object... progress) {
			ImageView imageView = (ImageView) progress[0];
			imageView.setImageBitmap((Bitmap) progress[1]);
		}
	}

	static public Bitmap getBitmapByUrl(String urlString)
			throws MalformedURLException, IOException {
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(25000);
		connection.setReadTimeout(90000);
		Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
		return bitmap;
	}

}
