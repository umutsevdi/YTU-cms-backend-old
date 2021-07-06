package com.cms.models;

public class AuthenticationRequest {
    private String mail;
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

}
