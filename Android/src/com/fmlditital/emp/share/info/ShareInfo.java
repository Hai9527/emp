package com.fmlditital.emp.share.info;

public abstract class ShareInfo {
	protected String title="Share from ";
	protected String context=null;
	
	public abstract void constructContext(String context);

	public String getContext() {
		return context;
	}

	 

}
