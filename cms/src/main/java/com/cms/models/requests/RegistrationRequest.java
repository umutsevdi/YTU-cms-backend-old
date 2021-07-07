package com.cms.models.requests;

public class RegistrationRequest {
    private String mail;
    private String password;
    private String name;
    public RegistrationRequest() {
    }
    public RegistrationRequest(String mail, String password, String name) {
        this.mail = mail;
        this.password = password;
        this.name = name;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    
    
}
