package edu.ait.realestateportal.rest.dto;

public class ErrorDTO {
	private int statusCode;
	private String message;
	public ErrorDTO(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
