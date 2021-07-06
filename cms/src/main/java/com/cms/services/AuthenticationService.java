package com.cms.services;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.cms.models.AuthenticationEntity;
import com.cms.models.User;
import com.cms.repositories.AuthenticationRepository;
import com.cms.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final String KEY = "muaxziwjaadidxurlhva";
    private AuthenticationRepository repository;
    private UserRepository userRepository;

    @Autowired
    public AuthenticationService(AuthenticationRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public String createToken(String mail, String password) throws Exception {
        User user = userRepository.findByMail(mail).get();

        if (user != null && user.getPassword().equals(getSHA(password))) {
            String token = getSHA(KEY + user.getId() + password + System.currentTimeMillis());
            repository.save(new AuthenticationEntity(user.getId(), token, System.currentTimeMillis()));
            return token;
        } else {
            throw new Exception("LoginException");
        }

    }

    public AuthenticationEntity getAuthentication(String token) throws Exception {
        if (isTokenExpired(token)) {
            throw new Exception("ExpiredTokenException");
        } else {
            return repository.findByToken(token).get();
        }
    }

    public boolean validateToken(String mail, String token) {
        AuthenticationEntity entity = repository.findByToken(token).get();
        User user = userRepository.findByMail(mail).get();
        if (entity != null && user != null)
            return entity.getUserId().equals(userRepository.findByMail(mail).get().getId()) && !isTokenExpired(token);
        else
            return false;

    }

    public boolean isTokenExpired(String token) {
        AuthenticationEntity entity = repository.findByToken(token).get();
        if (entity == null)
            return true;
        else if ((System.currentTimeMillis() - entity.getTimestamp()) > 86400000) {
            return false;
        } else {
            repository.delete(entity);
            return true;
        }
    }

    public static String getSHA(String input) { // NOT THREAD SAFE
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));

            BigInteger number = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(number.toString(16));

            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("getSHA = " + e);
            return input;
        }

    }

}
