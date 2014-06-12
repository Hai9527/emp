package com.fmlditital.emp.share.info;

import com.fmlditital.emp.config.Confi;

public class SharePicture extends ShareInfo {

	private String imagePath=null;
	
	@Override
	public void constructContext(String context) {
		super.context=title+Confi.SnsAppName+context;
		
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	 

}
