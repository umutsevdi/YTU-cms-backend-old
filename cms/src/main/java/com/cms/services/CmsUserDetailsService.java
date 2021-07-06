package com.cms.services;

import com.cms.models.Role;
import com.cms.models.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class CmsUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
       
        return new User("name", "mail", "password", 0L, true, true, Role.ADVISOR);
    }

}
