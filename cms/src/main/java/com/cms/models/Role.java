package com.cms.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority{
    UNASSIGNED,
    PRESIDENT,
    VICE_PRESIDENT,
    ACCOUNTANT,
    ADVISOR,
    DEPARTMENT,
    ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
