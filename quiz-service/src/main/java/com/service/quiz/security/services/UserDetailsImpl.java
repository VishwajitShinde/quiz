package com.service.quiz.security.services;

import com.service.quiz.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String email;

	private String mobile;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	private String firstName;

	private String lastName;

	private Date creationDate;

	private Date lastModifiedDate;

	public UserDetailsImpl(Long id, String email, String password, String mobile,
			Collection<? extends GrantedAuthority> authorities, String firstName,
						   String lastName, Date creationDate, Date lastModifiedDate) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.mobile = mobile;
		this.authorities = authorities;
		this.firstName = firstName;
		this.lastName = lastName;
		this.creationDate = creationDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId(),
				user.getEmail(),
				user.getPassword(),
				user.getMobile(),
				authorities,
				user.getFirstName(),
				user.getLastName(),
				user.getCreatedDate(),
				user.getModifiedDate()
				);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getMobile() {
		return mobile;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}