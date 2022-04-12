package com.singh.astha.notificationservice.repositories;

import com.singh.astha.notificationservice.model.UserToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserTokenRepository extends MongoRepository<UserToken, String> {

    UserToken findByUserId(Long userId);
}
