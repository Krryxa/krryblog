package com.krry.entity;

public class ResponseVal {
	
	private int code;
	private String message = "Request successful";
	private Object result;
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"code\":\"");
		builder.append(code);
		builder.append("\",\"message\":\"");
		builder.append(message);
		builder.append("\",\"result\":\"");
		builder.append(result);
		builder.append("\"}");
		return builder.toString();
	}
	
	
	
	
}
