package com.cms.repositories;

import java.util.Optional;

import com.cms.models.registration.MailConfirmationToken;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailConfirmationTokenRepository extends MongoRepository<MailConfirmationToken, String> {
    Optional<MailConfirmationToken> findByToken(String token);
}
