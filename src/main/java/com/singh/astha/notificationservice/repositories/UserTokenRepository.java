package com.singh.astha.notificationservice.repositories;

import com.singh.astha.notificationservice.model.UserToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserTokenRepository extends MongoRepository<UserToken, String> {

    Optional<UserToken> findByUserId(Long userId);

    Optional<UserToken> findByNotificationToken(String notificationToken);
}
