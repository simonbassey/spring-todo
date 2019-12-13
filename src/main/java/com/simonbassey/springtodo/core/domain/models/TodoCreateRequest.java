package com.simonbassey.springtodo.core.domain.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TodoCreateRequest {
	
	@NotEmpty
	@NotNull
	private String title;
	private String detail;
	
	@NotEmpty
	@NotNull
	private String userId;
	public void setUserId(String uId) { this.userId = uId;}
	public String getUserId() { return this.userId;}
	
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
