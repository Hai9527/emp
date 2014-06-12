package com.fmlditital.emp.dowmload;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fmlditital.emp.adapter.DownloadedAdapter;
import com.fmlditital.emp.adapter.DownloadingAdapter;
import com.fmlditital.emp.base.AdapterViewActivity;
import com.fmlditital.emp.model.DownloadModel;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.tool.NetWorkState;
import com.fmlditital.emp.tool.ViewFactory;
import com.fmlditital.emp.tool.ViewFactory.AdapterSelect;
import com.fmlditital.emp.view.TabView;

public class DownloadActivity extends AdapterViewActivity {

	private LinearLayout detailLinearLayout = null;

	// private DownloadReceiver mDownloadReceiver= null;

	private ListView downloadingListView = null, downloadedListView = null;
	private DownloadingAdapter adapterDowning = null;
	private DownloadedAdapter adapterDowned = null;
	private List<DownloadModel> downloadData = DownloadManager.getInstance()
			.getDownloadData();
	private List<DownloadModel> downloadingData = new ArrayList<DownloadModel>();
	private List<DownloadModel> downloadedData = new ArrayList<DownloadModel>();

	private String downloading = Global.DOWNLOADING,
			downloaded = Global.DOWNLOADED;
	private TabView touchView = null;

//	private Timer timer = new Timer();
	final Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
//			case 1: {
//				// Log.v("", ">>>>>>>>>>>>>>>>filterData(downloadData) ");
//				filterData(downloadData);
//				adapterDowning.notifyDataSetChanged();
//				adapterDowned.notifyDataSetChanged();
//			}
//				break;
			}
			super.handleMessage(msg);
		}

	};
	
	Runnable runSeekBarUpdate = new Runnable() {

		
		public void run() {
			filterData(downloadData);
			adapterDowning.notifyDataSetChanged();
			adapterDowned.notifyDataSetChanged();
			handler.postDelayed(this, 500);
		}

	};

//	private TimerTask task = new TimerTask() {
//
//		
//		public void run() {
//			Message message = new Message();
//			message.what = 1;
//			handler.sendMessage(message);
//
//		}
//	};

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		filterData(downloadData);

		detailLinearLayout = new LinearLayout(this);
		detailLinearLayout.setOrientation(LinearLayout.VERTICAL);

		touchView = new TabView(this);
		touchView.addTitle(downloading);
		touchView.addTitle(downloaded);
		touchView.display(downloading);
		touchView.addCommentCallback(this);
		bodyLayout.addView(touchView);

		downloadingListView = (ListView) ViewFactory.getAdapterView(this,
				AdapterSelect.downloadkey);
		downloadedListView = (ListView) ViewFactory.getAdapterView(this,
				AdapterSelect.downloadkey);

		adapterDowning = new DownloadingAdapter(DownloadActivity.this,
				downloadingData);
		adapterDowned = new DownloadedAdapter(DownloadActivity.this,
				downloadedData);

		detailLinearLayout.addView(downloadingListView);
		bodyLayout.addView(detailLinearLayout);

		downloadingListView.setAdapter(adapterDowning);
		downloadedListView.setAdapter(adapterDowned);

		downloadedListView.setOnItemClickListener(new OnItemClickListener() {

			
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				DownloadModel model = (DownloadModel) parent
						.getItemAtPosition(position);
				String path = model.getPath();
				openFile(new File(path));

			}

		});

		// timer.schedule(task, 0, 500);

		// mDownloadReceiver=new DownloadReceiver();
		//
		// IntentFilter filter = new IntentFilter();
		// filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		// registerReceiver(mDownloadReceiver, filter);
	}

	
	protected void onResume() {
		super.onResume();
		Log.v("", ">>>>>>>>>>>>>>>>onResume ");

		// timer.schedule(task, 0, 500);
		handler.postDelayed(runSeekBarUpdate, 1000);
//		adapterDowning.notifyDataSetChanged();
//		adapterDowned.notifyDataSetChanged();
	}

	
	protected void onPause() {
		super.onPause();
//		if (timer != null)
//			timer.cancel();
		if (runSeekBarUpdate != null)
			handler.removeCallbacks(runSeekBarUpdate);
	}

	
	protected void onDestroy() {
		super.onDestroy();
		// unregisterReceiver(mDownloadReceiver); //取消监听
	}

	private void filterData(List<DownloadModel> downloaddata) {
		downloadedData.clear();
		downloadingData.clear();
		for (DownloadModel model : downloaddata) {
			Log.v("", ">>>>>>>>>>>>>>>>model.getState() "+model.getState());
			
			if (model.getState() == DownloadManager.download_ing
					|| model.getState() == DownloadManager.download_pause)
				downloadingData.add(model);
		}
		for (DownloadModel model : downloaddata) {
			if (model.getState() == DownloadManager.download_ed) {
				downloadedData.add(model);
			}
		}
		 
//		adapterDowning.notifyDataSetChanged();
//		adapterDowned.notifyDataSetChanged();
	}

	
	public void showDetailOrCommentList(String title) {
		super.showDetailOrCommentList(title);
		if (title.equals(downloading)) {
			showBody(title);
		} else if (title.equals(downloaded)) {
			showBody(title);
		}
	}

	private void showBody(String flag) {
		if (flag.equals(downloading)) {
			detailLinearLayout.removeAllViews();
			detailLinearLayout.addView(downloadingListView);
		} else if (flag.equals(downloaded)) {
			detailLinearLayout.removeAllViews();
			detailLinearLayout.addView(downloadedListView);
		}
	}

	private void openFile(File file) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// 设置intent的Action属性
		intent.setAction(Intent.ACTION_VIEW);
		// 获取文件file的MIME类型
		String type = getMIMEType(file);
		// 设置intent的data和Type属性。
		intent.setDataAndType(/* uri */Uri.fromFile(file), type);
		// 跳转
		startActivity(intent);

	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * 
	 * @param file
	 */
	private String getMIMEType(File file) {

		String type = "*/*";
		String fName = file.getName();
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++) { // MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	private final String[][] MIME_MapTable = {
			// {后缀名， MIME类型}
			{ ".3gp", "video/3gpp" },
			{ ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" },
			{ ".avi", "video/x-msvideo" },
			{ ".bin", "application/octet-stream" },
			{ ".bmp", "image/bmp" },
			{ ".c", "text/plain" },
			{ ".class", "application/octet-stream" },
			{ ".conf", "text/plain" },
			{ ".cpp", "text/plain" },
			{ ".doc", "application/msword" },
			{ ".docx",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
			{ ".xls", "application/vnd.ms-excel" },
			{ ".xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
			{ ".exe", "application/octet-stream" },
			{ ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" },
			{ ".h", "text/plain" },
			{ ".htm", "text/html" },
			{ ".html", "text/html" },
			{ ".jar", "application/java-archive" },
			{ ".java", "text/plain" },
			{ ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" },
			{ ".js", "application/x-javascript" },
			{ ".log", "text/plain" },
			{ ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" },
			{ ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" },
			{ ".m4u", "video/vnd.mpegurl" },
			{ ".m4v", "video/x-m4v" },
			{ ".mov", "video/quicktime" },
			{ ".mp2", "audio/x-mpeg" },
			{ ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" },
			{ ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" },
			{ ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" },
			{ ".ogg", "audio/ogg" },
			{ ".pdf", "application/pdf" },
			{ ".png", "image/png" },
			{ ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pptx",
					"application/vnd.openxmlformats-officedocument.presentationml.presentation" },
			{ ".prop", "text/plain" }, { ".rc", "text/plain" },
			{ ".rmvb", "audio/x-pn-realaudio" }, { ".rtf", "application/rtf" },
			{ ".sh", "text/plain" }, { ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },
			{ ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
			{ ".wmv", "audio/x-ms-wmv" },
			{ ".wps", "application/vnd.ms-works" }, { ".xml", "text/plain" },
			{ ".z", "application/x-compress" },
			{ ".zip", "application/x-zip-compressed" }, { "", "*/*" } };

	public class DownloadReceiver extends BroadcastReceiver {

		// private List<DownloadModel> downloadData =
		// DownloadManager.getInstance()
		// .getDownloadData();

		
		public void onReceive(Context context, Intent intent) {

			if (!NetWorkState.isNetworkAvailable(context)) {
				Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无网络了");

				for (DownloadModel model : downloadData) {
					if (model.getState() == DownloadManager.download_ing)
						DownloadManager.getInstance().pause(model);
					Log.v("",
							">>>>>>>>>>>>>>>>model.getState()	"
									+ model.getState());
				}
			}
//			else {
//				Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>有网络了");
//				for (DownloadModel model : downloadData) {
//					if (model.getState() == DownloadManager.download_ing)
//						DownloadManager.getInstance().goon(context, model);
//				}
//				adapterDowning.notifyDataSetChanged();
//				adapterDowned.notifyDataSetChanged();
//			}
		}

	}
}
