package com.fmlditital.emp.base;

import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.fmlditital.emp.app.EMPApp;
import com.fmlditital.emp.model.BaseModel;

public abstract class DetailBaseActivity extends DefaultActivity {

//	boolean showTopbar = false;

	protected String tid = null,aid=null, data_id = null, data_name = null;

	protected String title = null;

	protected int index = 0;
	protected EMPApp app = EMPApp.getSingleton();

//	protected ViewFlipper mFlipper;
//	protected GestureDetector mGestureDetector;
//	protected LinearLayout[] linearLayouts = null;
	protected List<BaseModel> data = null;
//	protected boolean isShowcontrol = false;

//	protected int downX, upX;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		index = this.getIntent().getExtras().getInt("index");
		tid = this.getIntent().getExtras().getString("tid");
//        aid=this.
	}

//	protected abstract void nextPage();
//
//	protected abstract void prePage();

//	protected void showControl(boolean isShow) {
//		if (isShow == true) {
//			touchBarLayout.setVisibility(View.VISIBLE);
//			touchBarLayout.requestFocus();
//		} else
//			touchBarLayout.setVisibility(View.GONE);
//	}
}
