package com.fmlditital.emp.base;

import java.util.List;

import com.fmlditital.emp.model.BaseModel;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

public class AdapterViewActivity extends TopBarActivity {

	protected  List<  BaseModel > data = null;
	protected BaseAdapter adapter = null;
	protected AdapterView adapterView = null;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
}
