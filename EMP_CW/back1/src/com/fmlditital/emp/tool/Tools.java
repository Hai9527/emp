package com.fmlditital.emp.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;

public class Tools {

	public static String getMonthTime(String time) {
		String result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MMM");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

		Date date = null;

		try {
			date = format1.parse(time);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = sdf.format(date).toString();
		return result;
	}

	public static String getNumberTime(String time) {

		String result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("d");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

		Date date = null;

		try {
			date = format1.parse(time);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = sdf.format(date).toString();
		return result;
	}

	/**
	 * get Comment List Time
	 * 
	 * @param time
	 * @return
	 */
	public static String getCommentListTime(String time) {
		String result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");

		Date date = null;

		try {
			date = format1.parse(time);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = sdf.format(date).toString();
		return result;
	}

	public static String translateToTime(String time) {

		String result = null;

		int temp = Integer.parseInt(time.trim());

		int hour = (int) temp / 60 / 60;
		String hourString = String.valueOf(hour);

		int minute;
		if (hour != 0) {
			temp = (int) temp % 60;
			minute = (int) temp;
		} else {
			minute = temp / 60;
		}

		String minuteString = String.valueOf(minute);
		if (hour == 0)
			hourString = "00";

		result = hourString + ":" + minuteString;

		return result;
	}

	/**
	 * K单位转换M单位
	 * 
	 * @param size
	 * @return
	 */
	public static String translateKToM(int size) {
		String result = null;
		if (size >= 1024 * 1024)
			// result=String.format("%10.2f\n", size/(1024*1024))+"M";
			result = String.valueOf(size / (1024 * 1024)) + "M";
		else
			result = String.valueOf(size / 1024) + "K";
		return result;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * show Bad Network
	 * 
	 * @param context
	 */
	public static void showBadNetwork(final Context context) {
		UIConfig uiConfig = Confi.getInstance().getuIConfig();
		final AlertDialog alertDialog = new AlertDialog.Builder(context)
				.create();
		alertDialog.show();
		alertDialog.setContentView(R.layout.commentwifidialog);
		LinearLayout wifiBg = (LinearLayout) alertDialog
				.findViewById(R.id.wifidialog_linearlayout_bg);
		wifiBg.setBackgroundColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));
		wifiBg.getBackground().setAlpha(150);
		TextView txtTitle = (TextView) alertDialog
				.findViewById(R.id.wifidialog_textview_title);
		txtTitle.setText(R.string.check_network_message);
		txtTitle.setTextColor(Color.parseColor(uiConfig.getTopbar_text_color()));
		// 接收OKButton
		Button dialogBtnOk = (Button) alertDialog
				.findViewById(R.id.wifidialog_button_ok);
		// 修改OKButton字体颜色
		dialogBtnOk.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));
		// 修改OKButton颜色
		dialogBtnOk.setBackgroundColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));
		dialogBtnOk.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				((Activity) context).startActivity(new Intent(
						Settings.ACTION_WIRELESS_SETTINGS));
				alertDialog.cancel();
			}
		});

		// 接收CanelButton
		Button dialogBtnCanel = (Button) alertDialog
				.findViewById(R.id.wifidialog_button_cancel);
		// 修改CanelButton字体颜色
		dialogBtnCanel.setTextColor(Color.parseColor(uiConfig
				.getTopbar_text_color()));
		// 设置CanelButton颜色
		dialogBtnCanel.setBackgroundColor(Color.parseColor(uiConfig
				.getTopbar_background_color()));
		// CanelButton绑定监听器
		dialogBtnCanel.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				alertDialog.cancel();
			}
		});
	}

	/**
	 * get Android_ID
	 * 
	 * @param context
	 * @return
	 */
	public static String getAndroid_ID(Context context) {
		String result = android.provider.Settings.System.getString(
				context.getContentResolver(), "android_id");
		return result;
	}
}
