package com.cms.repositories;

import java.util.Optional;

import com.cms.models.authentication.AuthenticationEntity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AuthenticationRepository extends MongoRepository<AuthenticationEntity,String>{
    Optional<AuthenticationEntity> findByToken(String token);
    Optional<AuthenticationEntity> findByUserId(String userId);

}
