package com.fmlditital.emp.share.info;

import com.fmlditital.emp.config.Confi;

public class ShareText extends ShareInfo {

	@Override
	public void constructContext(String context) {
		super.context=title+Confi.SnsAppName+" "+context;
	}

	 

}
