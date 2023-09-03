package com.gwrteam.gwrteam.web.dto;

import javax.persistence.Column;
import java.util.Date;

public class UserRegistrationDto {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String custId;
    private Date createdTime;
    private boolean enabled;
    private String verificationCode;
    private String passwordCode;

    public UserRegistrationDto() {

    }

    public UserRegistrationDto(String username, String password, String email, String firstName, String lastName, String custId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.custId = custId;
        this.createdTime = createdTime;
        this.enabled = enabled;
        this.verificationCode = verificationCode;
        this.passwordCode = passwordCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}

