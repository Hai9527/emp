package com.fmlditital.emp.dowmload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.fmlditital.emp.app.EMPApp;
import com.fmlditital.emp.async.AsyncDownload;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.manage.ImageSaveManage;
import com.fmlditital.emp.manage.ImageSaveManage.ImageSaveDir;
import com.fmlditital.emp.model.DownloadModel;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.model.NavigationModel;
import com.fmlditital.emp.tool.NetWorkState;

public class DownloadManager {

	String dowloadDir = Global.THE_APP_FILE_NAME + File.separator + "download";
	File fileDir = null;

	private static int dataCount = 0;

	// 下载数据
	private List<DownloadModel> downloadData = null;
	// 下载任务
	private Map<String, AsyncDownload> downloadTask = null;
	private AsyncDownload asyncTemp = null;

	private static DownloadManager instance = null;
	private Context context = EMPApp.getSingleton();

	// public static final int download_none = 0; //未完成
	public static final int download_ing = 1; // 下载中
	public static final int download_pause = 2; // 停止下载
	public static final int download_ed = 3; // 完成

	private DownloadManager() {
		super();
		// this.context = context;
		if (downloadData == null)
			downloadData = new ArrayList<DownloadModel>();
		if (downloadTask == null)
			downloadTask = new HashMap<String, AsyncDownload>();

		fileDir = new File(dowloadDir);
		if (!fileDir.exists())
			fileDir.mkdir();
	}

	public static DownloadManager getInstance() {
		if (instance == null)
			instance = new DownloadManager();
		// this.context=context;
		return instance;
	}

	public void startTask() {

	}

	/**
	 * 添加单个下载任务
	 * 
	 * @param model
	 */
	public void addTask(Context context, DownloadModel model) {
		if (NetWorkState.isNetworkAvailable(context)) {
			model.setState(download_ing);
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd-hh-mm-ss");
			String date = sDateFormat.format(new java.util.Date());
			model.setId(date.toString());
			// model.setPath(fileDir + File.separator + date.toString()
			// + getName(model.getUrl()));

			model.setPath(fileDir + File.separator + model.getTitle()
					+ getExtension(model.getUrl()));
			// model.setPath(fileDir + File.separator +
			// getName(model.getUrl()));
			// Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>"+model.get);

			// String name=model.getUrl().substring(model.getUrl().indexOf("/"),
			// model.getUrl().length());
			// model.setPath(fileDir + File.separator
			// +name);

			downloadData.add(model);

			AsyncDownload async = new AsyncDownload(context, model);
			async.execute();
			downloadTask.put(model.getId(), async);
		}

	}

	/**
	 * 继续单个下载任务
	 * 
	 * @param model
	 */
	public void goon(Context context, DownloadModel model) {
		for (DownloadModel item : downloadData) {
			if (model.getId().equals(item.getId())) {

				asyncTemp = (AsyncDownload) downloadTask.get(model.getId());
				if (asyncTemp != null) {
					asyncTemp.cancel(true);
					asyncTemp = null;
				}
				AsyncDownload async = new AsyncDownload(context, model);
				async.execute();
				downloadTask.put(model.getId(), async);
			}
		}
	}

	/**
	 * 停止单个下载任务
	 * 
	 * @param model
	 */
	public void pause(DownloadModel model) {
//		Log.v("",
//				">>>>>>>>>>>>>>>>>>>>>>>>>>model.getTitle()   "
//						+ model.getTitle());
//		Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>>> downloadData.size()    "
//				+ downloadData.size());

		for (int i = downloadData.size() - 1; i >= 0; i--) {
			DownloadModel item = downloadData.get(i);
			if (model.getId().equals(item.getId()))
				asyncTemp = (AsyncDownload) downloadTask.get(model.getId());
			if (asyncTemp != null) {
				asyncTemp.cancel(true);
				asyncTemp = null;
			}
		}

	}

	/**
	 * 删除单个下载任务
	 * 
	 * @param model
	 */
	public void delete(DownloadModel model) {
		for (int i = downloadData.size() - 1; i >= 0; i--) {
			DownloadModel item = downloadData.get(i);
			if (model.getId().equals(item.getId())) {
				asyncTemp = (AsyncDownload) downloadTask.get(model.getId());
				if (asyncTemp != null) {
					asyncTemp.cancel(true);
					asyncTemp = null;
				}
				downloadData.remove(i);
				downloadTask.remove(model.getId());
			}
		}
	}

	public void start() {
		getDataFormFile();
	}

	/**
	 * 保存下载数据 并停止所有的下载任务
	 */
	public void stopAll() {
		if (downloadData != null && downloadData.size() > 0) {
			for (int i = downloadData.size() - 1; i >= 0; i--) {
				DownloadModel item = downloadData.get(i);
				asyncTemp = (AsyncDownload) downloadTask.get(item.getId());
				if (asyncTemp != null) {
					asyncTemp.cancel(true);
					asyncTemp = null;
				}
			}
			if (downloadTask != null)
				downloadTask = null;
		}
		saveData(downloadData);
	}

	/**
	 * 保存下载列表
	 * 
	 * @param data
	 */
	private void saveData(List<DownloadModel> data) {
		dataCount = downloadData.size();
 
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			String foldername = Global.THE_APP_FILE_NAME + File.separator
					+ ".configuration";
			File folder = new File(foldername);
			if (folder == null || !folder.exists()) {
				folder.mkdir();
			}

			File targetFile = new File(foldername + File.separator + "down");
			if (targetFile.exists())
				targetFile.delete();
			FileOutputStream fs = null;
			ObjectOutputStream os = null;
			try {
				fs = new FileOutputStream(targetFile);

				os = new ObjectOutputStream(fs);
//				os.writeObject(Confi.getInstance().getAPP_Type());
				os.writeInt(dataCount);
				for (DownloadModel item : downloadData) {
					os.writeObject(item);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (fs != null) {
					try {
						fs.close();
						fs = null;
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				if (os != null) {
					try {
						os.close();
						os = null;
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}

	}

	/**
	 * 从文件中获取下载列表
	 * 
	 * @return
	 */
	public void getDataFormFile() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			downloadData.clear();

			String foldername = Global.THE_APP_FILE_NAME + File.separator
					+ ".configuration";
			File folder = new File(foldername);
			if (folder == null || !folder.exists()) {
				folder.mkdir();
			}
			 

			File targetFile = new File(foldername + File.separator
					+ "down");
			FileInputStream fos = null;
			ObjectInputStream ois = null;
			try {
				fos = new FileInputStream(targetFile);
				ois = new ObjectInputStream(fos);

				dataCount = ois.readInt();
				for (int i = 0; i < dataCount; i++) {
					DownloadModel down = (DownloadModel) ois.readObject();
					downloadData.add(down);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.close();
						fos = null;
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				if (ois != null) {
					try {
						ois.close();
						ois = null;
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}

		} 
	}

	private String getExtension(String url) {
		String result = null;
		// Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>url " + url);
		result = url.substring(url.lastIndexOf("."), url.length());

		return result;
	}

	public List<DownloadModel> getDownloadData() {
		return downloadData;
	}
}
