package com.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.model.User;
@Repository
public interface UserRepository extends MongoRepository<User,Long> {

}
