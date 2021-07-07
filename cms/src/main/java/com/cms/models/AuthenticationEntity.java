package com.cms.models;

import java.util.Collection;

import com.mongodb.lang.NonNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@Document(collection = "auth")
public class AuthenticationEntity implements Authentication {
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

    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Object getCredentials() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Object getDetails() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Object getPrincipal() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public boolean isAuthenticated() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        
    }
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    
    
}
