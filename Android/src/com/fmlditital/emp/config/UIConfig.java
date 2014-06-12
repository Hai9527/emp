package com.fmlditital.emp.config;

import java.io.Serializable;

public class UIConfig implements Serializable {
	// app whole ui
	private String app_background_color = null;
	private String app_background_image = null;
	private String app_text_color = null;

	// navagation bar ui
	private String navbar_background_color = null;
//	private String navbar_background_image = null;
	private String navbar_text_color = null;

	// top bar ui
	private String topbar_background_color = null;
//	private String topbar_background_image = null;
	private String topbar_text_color = null;
	private String icon_type =  null;
	
	//opacity
	private int opacity=0;

	public String getApp_background_color() {
		return app_background_color;
	}

	public String getApp_icon_type() {
		return icon_type;
	}
	
	public void setApp_icon_type(String icon_type) {
		this.icon_type = icon_type;
	}
	
	public void setApp_background_color(String app_background_color) {
		this.app_background_color = addFlag(app_background_color);
	}

	public String getApp_background_image() {
		return app_background_image;
	}

	public void setApp_background_image(String app_background_image) {
		this.app_background_image = app_background_image;
	}

	public String getApp_text_color() {
		return app_text_color;
	}

	public void setApp_text_color(String app_text_color) {
		this.app_text_color = addFlag(app_text_color);
	}

	public String getNavbar_background_color() {
		return navbar_background_color;
	}

	public void setNavbar_background_color(String navbar_background_color) {
		this.navbar_background_color = addFlag(navbar_background_color);
	}

//	public String getNavbar_background_image() {
//		return navbar_background_image;
//	}
//
//	public void setNavbar_background_image(String navbar_background_image) {
//		this.navbar_background_image = navbar_background_image;
//	}

	public String getNavbar_text_color() {
		return navbar_text_color;
	}

	public void setNavbar_text_color(String navbar_text_color) {
		this.navbar_text_color = addFlag(navbar_text_color);
	}

	public String getTopbar_background_color() {
		return topbar_background_color;
	}

	public void setTopbar_background_color(String topbar_background_color) {
		this.topbar_background_color = addFlag(topbar_background_color);
	}

//	public String getTopbar_background_image() {
//		return topbar_background_image;
//	}
//
//	public void setTopbar_background_image(String topbar_background_image) {
//		this.topbar_background_image = topbar_background_image;
//	}

	public String getTopbar_text_color() {
		return topbar_text_color;
	}

	public void setTopbar_text_color(String topbar_text_color) {
		this.topbar_text_color = addFlag(topbar_text_color);
	}

	private String addFlag(String color) {
		return "#" + color;
	}

	public int getOpacity() {
		return opacity;
	}

	public void setOpacity(int opacity) {
		this.opacity = opacity;
	}
}
