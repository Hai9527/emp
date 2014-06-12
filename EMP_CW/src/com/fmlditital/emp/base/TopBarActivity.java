package com.fmlditital.emp.base;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.fmlditital.emp.app.EMPApp;
import com.fmlditital.emp.async.AsyncJson;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.model.BaseModel;
import com.fmlditital.emp.model.CastModel;
import com.fmlditital.emp.tool.ViewFactory;
import com.fmlditital.emp.tool.ViewFactory.AdapterSelect;
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
