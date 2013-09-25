package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessage{
	
	@JsonProperty("status-code")
	private int statusCode;
	private String message;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}