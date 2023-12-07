package com.singh.astha.notification.service.repositories;

import com.singh.astha.notification.service.models.NotificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NotificationTokenRepository extends MongoRepository<NotificationToken, String> {

    Optional<NotificationToken> findByUserId(Long userId);

    Optional<NotificationToken> findByUserIdAndTokens(Long userId, String token);
}
