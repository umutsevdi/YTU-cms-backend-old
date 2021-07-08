package com.cms.models.documents;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import com.mongodb.lang.NonNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "users")
public class User implements UserDetails {
    @Id
    private String id;
    private String name;
    @NonNull
    @Indexed(unique = true)
    private String mail;
    private String password;
    private long phone;
    private boolean mailConfirmed=false;
    private boolean enabled=true;
    private boolean registered=false;
    private Role role;
    @CreatedDate
    private LocalDate registrationDate;
    @Value("${cms.security.key}")
    private String customPropertyTest;

    public User() {
    }

    public User(String name, String mail, String password, long phone, Role role) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.phone = phone;
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

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    @Override
    public String getUsername() {
        return this.mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
        
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return mailConfirmed;
    }

    

    public String getCustomPropertyTest() {
        return customPropertyTest;
    }

    public void setCustomPropertyTest(String customPropertyTest) {
        this.customPropertyTest = customPropertyTest;
    }

    @Override
    public String toString() {
        return "User [enabled=" + enabled + ", id=" + id + ", mail=" + mail + ", mailConfirmed=" + mailConfirmed
                + ", name=" + name + ", password=" + password + ", phone=" + phone + ", registered=" + registered
                + ", registrationDate=" + registrationDate + ", role=" + role + "]";
    }

}
