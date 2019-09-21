package com.evacodekitchen.realestateportalserver.rest.dto;

public class ErrorDTO {
	
	private String message;
	private Integer code;

	public ErrorDTO() {
	}

	public ErrorDTO(String message, Integer code) {
		this.message = message;
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
