package com.api.quiz.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;
 
public class SignupRequest {

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Pattern(regexp="(^$|[0-9]{10})")
    private String mobile;

    @NotBlank
    @Size(max = 20)
    private String firstName;

    @NotBlank
    @Size(max = 20)
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