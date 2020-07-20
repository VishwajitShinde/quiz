package com.api.quiz.payload.response;

public class MessageResponse<T> {
	private String message;
	private T response;

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public MessageResponse(String message, T response) {
		this.message = message;
		this.response = response;
	}

	public MessageResponse(String message) {
	    this.message = message;
	  }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}