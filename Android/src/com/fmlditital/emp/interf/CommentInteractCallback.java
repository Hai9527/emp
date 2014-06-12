package com.fmlditital.emp.interf;

/**
 * this interface for comment interact call back
 */


public interface CommentInteractCallback {
	
	public void actionCallback() ;  //saw action 
	public void editCallback() ;// for Skip to comment list
	
	
//	public void addCallback(String tid,String data_id,String data_name); //add comment callback
	
	public void showDetailOrCommentList(String title);  // for comment detail or comment list
}
