package com.cms.services;

import com.cms.models.User;
import com.cms.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository uRepository;
    
    @Autowired
    public UserService(UserRepository uRepository) {
        this.uRepository = uRepository;
    }


    public void insertElement(User user){
        uRepository.insert(user);
    }
}
