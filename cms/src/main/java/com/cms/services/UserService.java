package com.cms.services;

import java.util.Optional;

import com.cms.models.documents.User;
import com.cms.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User loadUserByUsername(String mail) throws UsernameNotFoundException {
        return repository.findByMail(mail).orElseThrow(() -> new UsernameNotFoundException("UserNotFoundException"));
    }

    public User findById(String id) throws UsernameNotFoundException {
        return repository.findById(id).orElseThrow(() -> new UsernameNotFoundException("UserNotFoundException"));
    }

    public User findByMail(String mail) throws UsernameNotFoundException {
        return repository.findByMail(mail).orElseThrow(() -> new UsernameNotFoundException("UserNotFoundException"));
    }

    public User insertElement(User user) throws Exception {
        System.out.println(user);
        Optional<User> existingUser = repository.findByMail(user.getMail());
        if (existingUser.isPresent()) {
            throw new Exception("ExistingUserException");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return repository.insert(user);

    }

    public void editElement(User user) throws Exception {
        repository.findById(user.getId()).orElseThrow(() -> new Exception("UserNotFoundException"));
        repository.save(user);

    }

    public void deleteElement(String id) {
        try {
            repository.delete(repository.findById(id).get());
        } catch (Exception e) {
            System.out.println("User not found");
        }
    }
}
