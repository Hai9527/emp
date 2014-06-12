package com.fmlditital.emp.share.info;

public abstract class ShareInfo {
	protected String title="分享自安卓客户端 ";
	protected String context=null;
	
	public abstract void constructContext(String context);

	public String getContext() {
		return context;
	}

	 

}
