package com.fmlditital.emp.model;

import android.graphics.Bitmap;

public class GalleryThumbModel extends BaseModel {
	private String id=null;
	private String pThumbUrl=null;
	private String pImageUrl=null;
	private String pName=null;
	private String description=null;
	private String title=null;
	private String isDownload=null;
	private String pId=null;
	private Bitmap bitmap=null;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpThumbUrl() {
		return pThumbUrl;
	}
	public void setpThumbUrl(String pThumbUrl) {
		this.pThumbUrl = pThumbUrl;
	}
	public String getpImageUrl() {
		return pImageUrl;
	}
	public void setpImageUrl(String pImageUrl) {
		this.pImageUrl = pImageUrl;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIsDownload() {
		return isDownload;
	}
	public void setIsDownload(String isDownload) {
		this.isDownload = isDownload;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

}
