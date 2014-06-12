package com.fmlditital.emp.async;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.fmlditital.emp.interf.JsonInterface;
import com.fmlditital.emp.manage.SaveDataManage;
import com.fmlditital.emp.model.Global;
import com.fmlditital.emp.tool.NetWorkState;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

public class AsyncMoareJson extends AsyncTask<String, Integer, JSONObject> {
	private JsonInterface interFace = null;
	private String urlPath = null;
	private Context context = null;
	
	private ProgressDialog progressDialog = null;


	public void addInterFace(JsonInterface interFace) {
		this.interFace = interFace;
	}

	public AsyncMoareJson(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(context, "", "loading...", true,true);
	}

	@Override
	protected JSONObject doInBackground(String... params) {

		JSONObject jsonObject = null;
		URL url = null;
		HttpURLConnection httpURLConnection = null;
		BufferedReader br = null;
		InputStream inputStream = null;
		try {

			urlPath = params[0];
			url = new URL(urlPath);
			StringBuilder builder = new StringBuilder();

			if (NetWorkState.isNetworkAvailable(context)) {
				httpURLConnection = (HttpURLConnection) url.openConnection();
				inputStream = httpURLConnection.getInputStream();
				br = new BufferedReader(new InputStreamReader(inputStream));
				String line;
				while ((line = br.readLine()) != null) {
					builder.append(line);

				}
				jsonObject = new JSONObject(builder.toString());

				inputStream.close();
				br.close();
				httpURLConnection.disconnect();

				saveJsonToSD(urlPath, builder.toString());
			} else {
				if (getJsonFormSD(urlPath) != null)
					jsonObject = new JSONObject(getJsonFormSD(urlPath));
			}

		} catch (MalformedURLException e) {
			// e.printStackTrace();
			interFace.callJoinFailure(params[0], e.getMessage());
		} catch (IOException e) {
			interFace.callJoinFailure(params[0], e.getMessage());
		} catch (JSONException e) {
			interFace.callJoinFailure(params[0], e.getMessage());
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return jsonObject;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		progressDialog.dismiss();
		if (result != null)
			interFace.callJoinMore(urlPath, result);
		else
			interFace.callJoinFailure(urlPath, "no data");
		super.onPostExecute(result);
	}

	/**
	 * save Json To SD
	 * 
	 * @param path
	 * @param stringToWrite
	 */
	private void saveJsonToSD(String url, String stringToWrite) {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {

			String fileName = null;

			fileName = SaveDataManage.getSaveDataManage().getJsonDirec(context,
					url);

			if (fileName.equals("null") || fileName == null) {
				fileName = produceJsonName();
				SaveDataManage.getSaveDataManage().saveJsonTable(context, url,
						fileName);
			}

			File fileDir = new File(Global.THE_APP_FILE_NAME + File.separator
					+ "cache");
			if (!fileDir.exists())
				fileDir.mkdir();

			File f = new File(fileDir + File.separator + fileName);
			if (f.exists())
				f.delete();
			OutputStreamWriter osw = null;

			try {
				if (!f.exists()) {
					f.createNewFile();
					osw = new OutputStreamWriter(new FileOutputStream(f),
							"utf-8");
					osw.write(stringToWrite);
					osw.close();
				} else {
					osw = new OutputStreamWriter(new FileOutputStream(f, true),
							"utf-8");
					osw.write(stringToWrite);
					osw.flush();
					osw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * get Json Form SD
	 * 
	 * @param path
	 * @return
	 */
	private String getJsonFormSD(String path) {

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			String jsonName = null;

			jsonName = SaveDataManage.getSaveDataManage().getJsonDirec(context,
					path);

			if (jsonName.equals("null") || jsonName == null)
				return null;

			String foldername = Global.THE_APP_FILE_NAME + File.separator
					+ "cache";
			File folder = new File(foldername);
			if (folder == null || !folder.exists()) {
				folder.mkdir();
			}
			File targetFile = new File(foldername + File.separator + jsonName);
			StringBuilder sb = new StringBuilder();
			try {
				if (!targetFile.exists()) {
					targetFile.createNewFile();
					return "No File error ";
				} else {
					InputStream in = new BufferedInputStream(
							new FileInputStream(targetFile));
					BufferedReader br = new BufferedReader(
							new InputStreamReader(in, "UTF-8"));
					String tmp;
					while ((tmp = br.readLine()) != null) {
						sb.append(tmp);
					}
					br.close();

					in.close();
					return sb.toString();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

		}

		return null;
	}

	private String produceJsonName() {
		StringBuffer sb = new StringBuffer();

		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		String date = sDateFormat.format(new java.util.Date());
		sb.append(date);
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random1 = new Random();

		for (int i = 0; i < 5; i++) {
			int number = random1.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

}
