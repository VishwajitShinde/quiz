package com.api.quiz.payload.request;

import com.api.quiz.models.Password;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@ToString
public class SignupRequest {

    @NotBlank
    @Size(max = 40)
    @Email ( message = "Email is invalid ")
    private String email;

    private Set<String> role;
    
    @NotBlank
    @Password (message = "Password is invalid : Chars - min(8), max(20), uppercase - min(1), number - min(1), special char - min(1)")
    private String password;

    @NotBlank
    @Pattern(regexp="(^$|[0-9]{10})", message = "Mobile number is invalid")
    private String mobile;

    @NotBlank
    @Size( max = 20, message = "first name exceeds character limit : max 20 char")
    private String firstName;

    @NotBlank
    @Size(max = 20, message = "first name exceeds character limit : max 20 char" )
    private String lastName;

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
    
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
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
}