package com.fmlditital.emp.interf;

import org.json.JSONObject;

public interface JsonInterface {
	
	public void callJoinSuccess(String url,JSONObject json) ;
	public void callJoinFailure(String url,String failMessage) ;
	public void callJoinMore(String url,JSONObject json) ;

}
