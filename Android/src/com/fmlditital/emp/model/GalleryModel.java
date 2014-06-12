package com.fmlditital.emp.model;

public class GalleryModel extends BaseModel{
	private String gId=null;
	private String gName=null;
	private String gThumbUrl=null;
	private String count;
	private String isDownload=null;
	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getgId() {
		return gId;
	}
	public void setgId(String gId) {
		this.gId = gId;
	}
	public String getgName() {
		return gName;
	}
	public void setgName(String gName) {
		this.gName = gName;
	}
	public String getgThumbUrl() {
		return gThumbUrl;
	}
	public void setgThumbUrl(String gThumbUrl) {
		this.gThumbUrl = gThumbUrl;
	}
	public String getIsDownload() {
		return isDownload;
	}
	public void setIsDownload(String isDownload) {
		this.isDownload = isDownload;
	}

	

}
