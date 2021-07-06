package com.cms.services;

import java.util.Optional;

import com.cms.models.User;
import com.cms.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository repository;
    
    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public User findById(String id){
        return repository.findById(id).get();
    }

    public User findByMail(String mail){
        return repository.findUserByMail(mail).get();
    }

    public void insertElement(User user){
        repository.insert(user);
    }
    public void editElement(String id,Optional<String> password,){
        User user = repository.findById(id).get();
        
    }

    public void deleteElement(String id){
        try{
            repository.delete(repository.findById(id).get());
        }catch(Exception e){
            System.out.println("User not found");
        }
    }
}
