package com.cms.models;

import com.mongodb.lang.NonNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "auth")
public class AuthenticationEntity {
    @Id
    private String id;
    @NonNull
    @Indexed(unique = true)
    private final String  userId;
    @NonNull
    @Indexed(unique = true)
    private final String  token;
    private final Long  timestamp;
    public AuthenticationEntity(String userId, String token, Long timestamp) {
        this.userId = userId;
        this.token = token;
        this.timestamp = timestamp;
    }
    public String getUserId() {
        return userId;
    }
    public String getToken() {
        return token;
    }
    public Long getTimestamp() {
        return timestamp;
    }

    
    
}
