package com.api.quiz.models;

import com.api.quiz.payload.request.SignupRequest;
import com.api.quiz.util.RoleObjectConverter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "mobile"),
			@UniqueConstraint(columnNames = "email") 
		})
public class User implements Serializable  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 50)
	@Email
	@Column( name = "email", unique = true )
	private String email;

	@NotBlank
	@Size(max = 120)
	@Column( name = "password", nullable = false )
	private String password;

	@Column( name = "mobile", nullable = false, unique = true)
	@Pattern(regexp="(^$|[0-9]{10})")
	private String mobile;

	@Column( name = "first_name", nullable = true )
	@Size(max = 20)
	private String firstName ;

	@Column( name = "last_name", nullable = true )
	@Size(max = 20)
	private String lastName ;

	@Convert(converter = RoleObjectConverter.class)
	private Set<Role> roles = new HashSet<>();

	@Column( name = "creation_date", nullable = false )
	private Date createdDate ;

	@Column( name = "last_modified_date", nullable = false )
	private Date modifiedDate ;

	public User() {

	}

	public User( String email, String password, String mobile, String firstName, String lastName) {
		this.email = email;
		this.password = password;
		this.mobile = mobile;

		this.firstName = firstName;
		this.lastName = lastName;

		this.createdDate = new Date();
		this.modifiedDate = new Date();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}