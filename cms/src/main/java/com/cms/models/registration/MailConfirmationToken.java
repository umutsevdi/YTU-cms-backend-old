package com.cms.models.registration;

import java.time.LocalDateTime;

import com.mongodb.lang.NonNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "token")
public class MailConfirmationToken {
    @Id
    private String id;
    @NonNull
    @Indexed(unique = true)
    private String mail;
    private LocalDateTime expireDate;
    @NonNull
    private String token;
    public MailConfirmationToken() {
    }
    public MailConfirmationToken(String mail, LocalDateTime expireDate, String token) {
        this.mail = mail;
        this.expireDate = expireDate;
        this.token = token;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public LocalDateTime getExpireDate() {
        return expireDate;
    }
    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }


}
