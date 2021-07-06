package com.cms.models;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import com.mongodb.lang.NonNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "users")
public class User implements UserDetails, CredentialsContainer{
    @Id
    private String id;
    private String name;
    @NonNull
    @Indexed(unique = true)
    private String mail;
    private String password;
    private long phone;
    private boolean mailConfirmed;
    private boolean enabled;
    private Role role;
    @CreatedDate
    private LocalDate registrationDate;

    public User() {
    }

    public User(String name, String mail, String password, long phone, boolean mailConfirmed,
            boolean enabled, Role role) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.phone = phone;
        this.mailConfirmed = mailConfirmed;
        this.enabled = enabled;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public boolean isMailConfirmed() {
        return mailConfirmed;
    }

    public void setMailConfirmed(boolean mailConfirmed) {
        this.mailConfirmed = mailConfirmed;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return List.of(role);
    }

    @Override
    public String getUsername() {
        return this.mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return mailConfirmed;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public void eraseCredentials() {
        
    }

    

}
