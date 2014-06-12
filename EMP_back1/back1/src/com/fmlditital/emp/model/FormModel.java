package com.fmlditital.emp.model;

public class FormModel extends BaseModel{
	private String fvid = null;
	private String fv_name = null;
	private String fv_title = null;
	private String fv_type = null;
	private String fv_required = null;
	private String prompting_message = null;
	private String vis_hits = null; 
	private String fv_value = null; 
	
	private String fv_text = null;
	
	public String getFv_text() {
		return fv_text;
	}

	public void setFv_text(String fv_text) {
		this.fv_text = fv_text;
	}

	public String getFv_value() {
		return fv_value;
	}

	public void setFv_value(String fv_value) {
		this.fv_value = fv_value;
	}

	public String getFvid() {
		return fvid;
	}

	public void setFvid(String fvid) {
		this.fvid = fvid;
	}

	public String getFv_name() {
		return fv_name;
	}

	public void setFv_name(String fv_name) {
		this.fv_name = fv_name;
	}

	public String getFv_title() {
		return fv_title;
	}

	public void setFv_title(String fv_title) {
		this.fv_title = fv_title;
	}

	public String getFv_type() {
		return fv_type;
	}

	public void setFv_type(String fv_type) {
		this.fv_type = fv_type;
	}

	public String getFv_required() {
		return fv_required;
	}

	public void setFv_required(String fv_required) {
		this.fv_required = fv_required;
	}

	public String getPrompting_message() {
		return prompting_message;
	}

	public void setPrompting_message(String prompting_message) {
		this.prompting_message = prompting_message;
	}

	public String getVis_hits() {
		return vis_hits;
	}

	public void setVis_hits(String vis_hits) {
		this.vis_hits = vis_hits;
	}
}
