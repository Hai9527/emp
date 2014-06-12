package com.fmlditital.emp.async;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.fmlditital.emp.dowmload.DownloadManager;
import com.fmlditital.emp.model.DownloadModel;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.music.MusicDetailActivity;
import com.fmlditital.emp.tool.NetWorkState;

public class AsyncDownload extends AsyncTask<Void, Integer, String> {
	private boolean finished = true;
	private DownloadModel model = null;
	private Context context = null;
	
//	private boolean isNetworkConnect=true;

	public AsyncDownload(Context context, DownloadModel model) {
		this.model = model;
		this.context = context;
	}

	@Override
	protected void onCancelled() {
		model.setState(DownloadManager.download_pause);
		finished = false;
		super.onCancelled();
	}

	@Override
	protected String doInBackground(Void... params) {

		URL url = null;
		HttpURLConnection httpURLConnection = null;
		InputStream inputStream = null;
		RandomAccessFile outputStream = null;
		int length = 0;
		int startPosition = model.getCurrentPoit();
		
//		while(isNetworkConnect) {
//			 if (NetWorkState.isNetworkAvailable(context)) {
			model.setState(DownloadManager.download_ing);
			try {
				url = new URL(model.getUrl());
				httpURLConnection = (HttpURLConnection) url.openConnection();

				httpURLConnection.setRequestProperty("RANGE", "bytes="
						+ startPosition + "-");
				httpURLConnection.setAllowUserInteraction(true);

				// length = httpURLConnection.getContentLength();
				// int startPosition = model.getCurrentPoit();

				if (model.getTotalSize() == 0) {
					length = httpURLConnection.getContentLength();
					model.setTotalSize(length);
				} else {
					length = model.getTotalSize();
				}

				inputStream = httpURLConnection.getInputStream();

				outputStream = new RandomAccessFile(model.getPath(), "rw");
				outputStream.seek(startPosition);
				
				byte[] buf = new byte[1024 * 10];
				int read = 0;
				int curSize = startPosition;
				while (finished) {
					// while ((read = inputStream.read(buf)) != -1) {
					 
					read = inputStream.read(buf);
					if (read == -1) {
						finished = false;
						break;
					}

					outputStream.write(buf, 0, read);
					curSize = curSize + read;

					model.setCurrentPoit(curSize);
					// update progressbar
					publishProgress((int) (curSize * 100.0f / length));
					if (curSize == length) {
						break;
					}
			
					// Thread.sleep(10);
				}
				inputStream.close();
				outputStream.close();
				httpURLConnection.disconnect();
			} catch (MalformedURLException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
				
			}
			// catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			finally {
				finished = false;
				if (inputStream != null) {
					try {
						inputStream.close();
						if (outputStream != null) {
							outputStream.close();
						}
						if (httpURLConnection != null) {
							httpURLConnection.disconnect();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
//			 }else {
//				 isNetworkConnect=true;
//			 model.setState(DownloadManager.download_pause);
//			 }
//		}
		
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// Log.v("", ">>>>>>>>>>>>>>>>>>>>>>>>onProgressUpdate(): " +
		// values[0]);
		model.setProgress(values[0]);
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String result) {
		model.setState(DownloadManager.download_ed);
		super.onPostExecute(result);
	}

}