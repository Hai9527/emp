package com.fmlditital.emp.base;

import android.os.Bundle;

import com.fmlditital.emp.app.EMPApp;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.view.TopBarView;

public class TopBarActivity extends DefaultActivity {

	protected EMPApp app = EMPApp.getSingleton();

	protected TopBarView topBarView = null;
	protected String title = null;

	protected String tid = null;
	protected String dataUrl = null;

	protected AsyncJson asyncJson = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		title = this.getIntent().getExtras().getString("tabName");
		tid = this.getIntent().getExtras().getString("tid");

		topBarView = new TopBarView(this, title);

		bodyLayout.removeAllViews();
		bodyLayout.invalidate();
		bodyLayout.addView(topBarView);

	}
}
