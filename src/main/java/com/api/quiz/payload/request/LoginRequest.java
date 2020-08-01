package com.api.quiz.payload.request;

import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
public class LoginRequest {
	@NotBlank
	private String emailOrMobile;

	@NotBlank
	private String password;

	public String getEmailOrMobile() {
		return emailOrMobile;
	}

	public void setEmailOrMobile(String emailOrMobile) {
		this.emailOrMobile = emailOrMobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}