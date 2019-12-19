package com.simonbassey.springtodo.core.domain.models;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationResult {

	private boolean isSuccess;
	private List<String> errors;
	private String token;
	public boolean isSuccess() {
		return isSuccess;
	}
	public void isSuccess(boolean succeeded) {
		this.isSuccess = succeeded;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public AuthenticationResult() {
		// TODO Auto-generated constructor stub
	}
	
	public AuthenticationResult(boolean status, String token_) {
		isSuccess = status;
		this.token = token_;
	}
	public AuthenticationResult(boolean status, String token_, String[] errors_) {
		isSuccess = status;
		this.token = token_;
		errors = errors == null ? new ArrayList<String>() : errors;
		for (var e : errors_) {
			errors.add(e);
		}
	}
	
}
