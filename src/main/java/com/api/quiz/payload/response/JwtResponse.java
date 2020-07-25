package com.api.quiz.payload.response;

import com.api.quiz.models.User;
import com.api.quiz.security.services.UserDetailsImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String email;
	private String mobile;
	private List<String> roles;

	private Date creationDate;
	private Date lastModifiedDate;
	private String firstName;
	private String lastName;

	public JwtResponse(String accessToken, Long id, String email, String mobile, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.email = email;
		this.mobile = mobile;
		this.roles = roles;
	}

	public JwtResponse(String accessToken, User userObj ) {
		this.token = accessToken;
		this.id = userObj.getId();
		this.email = userObj.getEmail();
		this.mobile = userObj.getMobile();
		this.roles = userObj.getRoles().stream()
				.map( roleObj -> roleObj.getName().name() ).collect(Collectors.toList());
		this.creationDate = userObj.getCreatedDate();
		this.lastModifiedDate = userObj.getModifiedDate();
		this.firstName = userObj.getFirstName();
		this.lastName = userObj.getLastName();
	}

	public JwtResponse( String accessToken, UserDetailsImpl userDetails ) {
		this.token = accessToken;
		this.id = userDetails.getId();
		this.email = userDetails.getEmail();
		this.mobile = userDetails.getMobile();
		this.roles = userDetails.getAuthorities().stream()
				.map( simpleGrantedAuthority -> simpleGrantedAuthority.getAuthority() ).collect(Collectors.toList());
		this.creationDate = userDetails.getCreationDate();
		this.lastModifiedDate = userDetails.getLastModifiedDate();
		this.firstName = userDetails.getFirstName();
		this.lastName = userDetails.getLastName();
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}