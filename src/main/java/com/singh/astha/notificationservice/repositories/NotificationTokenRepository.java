package com.singh.astha.notificationservice.repositories;

import com.singh.astha.notificationservice.model.NotificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NotificationTokenRepository extends MongoRepository<NotificationToken, String> {

    Optional<NotificationToken> findByUserId(Long userId);

    Optional<NotificationToken> findByUserIdAndTokens(Long userId, String token);
}
