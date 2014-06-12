package com.fmlditital.emp.tool;


public class SDCardState {

	public static boolean isSDCardAvailable() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

}
