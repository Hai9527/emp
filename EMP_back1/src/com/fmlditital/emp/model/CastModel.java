package com.fmlditital.emp.model;

public class CastModel extends BaseModel {
	private String id=null;
	private String title=null;
	private String icon=null;
	private String image=null;
	
	private String content=null;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getContent() {
		return content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
