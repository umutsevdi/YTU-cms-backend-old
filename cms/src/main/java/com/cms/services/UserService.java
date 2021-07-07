package com.cms.services;

import java.util.Optional;

import com.cms.models.User;
import com.cms.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    // @Autowired
    // private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User loadUserByUsername(String mail) throws UsernameNotFoundException {
        return repository.findByMail(mail).orElseThrow(() -> new UsernameNotFoundException("UserNotFoundException"));
    }

    public User findById(String id) {
        return repository.findById(id).get();
    }

    public User findByMail(String mail) {
        return repository.findByMail(mail).get();
    }

    public User insertElement(User user) {
        System.out.println(user);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        return repository.insert(user);
    }

    public void editElement(String id, Optional<String> password) {
        // User user = repository.findById(id).get();

    }

    public void deleteElement(String id) {
        try {
            repository.delete(repository.findById(id).get());
        } catch (Exception e) {
            System.out.println("User not found");
        }
    }
}
