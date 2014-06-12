package com.fmlditital.emp.model;

import android.graphics.Bitmap;

public class AriticleModel extends BaseModel {
	private String id = null;
	private String title = null;
	private String summary = null;
	private String time = null;
	private String thumbUrl = null;
	private String content = null;
	private String comment_count = null;
	private String players_count = null;
	private String likes_count = null;
	private Bitmap thumbBitmap=null;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getComment_count() {
		return comment_count;
	}

	public void setComment_count(String comment_count) {
		this.comment_count = comment_count;
	}

	public String getPlayers_count() {
		return players_count;
	}

	public void setPlayers_count(String players_count) {
		this.players_count = players_count;
	}

	public String getLikes_count() {
		return likes_count;
	}

	public void setLikes_count(String likes_count) {
		this.likes_count = likes_count;
	}

	public Bitmap getThumbBitmap() {
		return thumbBitmap;
	}

	public void setThumbBitmap(Bitmap thumbBitmap) {
		this.thumbBitmap = thumbBitmap;
	}

}
