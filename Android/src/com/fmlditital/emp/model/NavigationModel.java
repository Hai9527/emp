package com.fmlditital.emp.model;

import java.io.Serializable;

import android.graphics.drawable.Drawable;


public class NavigationModel implements Serializable {
	private int id = 0;
	private String tab_id = null;
	private String title = null;
	private  transient Drawable icon = null;
	private String iconPath=null;
	private String tab_type=null;
	
	private String function = null;

	public NavigationModel() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getTab_id() {
		return tab_id;
	}

	public void setTab_id(String tab_id) {
		this.tab_id = tab_id;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getTab_type() {
		return tab_type;
	}

	public void setTab_type(String tab_type) {
		this.tab_type = tab_type;
	}
}
 
 
