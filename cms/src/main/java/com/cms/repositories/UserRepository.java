package com.cms.repositories;

import java.util.Optional;

import com.cms.models.documents.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String>{
    Optional<User> findByMail(String mail);

}
