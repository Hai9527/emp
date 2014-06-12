package com.fmlditital.emp.model;

import java.io.Serializable;

import com.fmlditital.emp.adapter.AdapterViewAdapter;

public class DownloadModel   implements Serializable {
	
	private String id=null;
	private int currentPoit=0;
	private int totalSize=0;
	private String title=null;
	private String time=null;
	private String url=null;
	private String path=null;
	private int progress=0;
//	private String nane=null;
	private int state=0;
	public synchronized String getTitle() {
		return title;
	}
	public synchronized void setTitle(String title) {
		this.title = title;
	}
	public synchronized String getId() {
		return id;
	}
	public synchronized void setId(String id) {
		this.id = id;
	}
	public synchronized int getCurrentPoit() {
		return currentPoit;
	}
	public synchronized void setCurrentPoit(int currentPoit) {
		this.currentPoit = currentPoit;
	}
	public synchronized int getTotalSize() {
		return totalSize;
	}
	public synchronized void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public synchronized String getUrl() {
		return url;
	}
	public synchronized void setUrl(String url) {
		this.url = url;
	}
	public synchronized String getPath() {
		return path;
	}
	public synchronized void setPath(String path) {
		this.path = path;
	}
	public synchronized String getTime() {
		return time;
	}
	public synchronized void setTime(String time) {
		this.time = time;
	}
	public synchronized int getState() {
		return state;
	}
	public synchronized void setState(int state) {
		this.state = state;
	}
	public synchronized int getProgress() {
		return progress;
	}
	public synchronized void setProgress(int progress) {
		this.progress = progress;
	}
//	public String getSize() {
//		return size;
//	}
//	public void setSize(String size) {
//		this.size = size;
//	}
//	public String getNane() {
//		return nane;
//	}
//	public void setNane(String nane) {
//		this.nane = nane;
//	}
			

}
