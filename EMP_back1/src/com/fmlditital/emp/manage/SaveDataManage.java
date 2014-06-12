package com.fmlditital.emp.manage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.fmlditital.emp.config.Confi;

public class SaveDataManage {

	private static SaveDataManage saveDataManage = null;

	public static final String JSON_DATA = "JSON_DATA";

	private SaveDataManage() {

	}

	// Context context;

	public static SaveDataManage getSaveDataManage() {
		if (saveDataManage == null)
			saveDataManage = new SaveDataManage();
		return saveDataManage;
	}

	public void saveJsonTable(Context context, String url, String path) {
		SharedPreferences sp = context.getSharedPreferences(JSON_DATA,
				Activity.MODE_PRIVATE);

		Editor editor = sp.edit();

		editor.putString(url, path);

		editor.commit();
	}

	public String getJsonDirec(Context context, String path) {
		SharedPreferences sp = context.getSharedPreferences(JSON_DATA,
				Activity.MODE_PRIVATE);
		return sp.getString(path, "null");
	}

	public void clearAllJsonTable(Context context) {
		SharedPreferences sp = context.getSharedPreferences(JSON_DATA,
				Activity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}

}
