package com.douzone.dto;

public class JSONResult {
	private String result;	// "sueccess", "fail"
	private String message;	// result가 "fail" 이면 에러 내용, "success"이면 null
	private Object data;	// result가 "fail" 이면 null, "success"이면 객체

	// 기본 생성자 만들지 않음
	private JSONResult(String result, String message, Object data) {
		this.result = result;
		this.message = message;
		this.data = data;
	}
	
	public static JSONResult success(Object data) {
		return new JSONResult("success", null, data);
	}
	
	public static JSONResult fail(String message) {
		return new JSONResult("fail", message, null);
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
