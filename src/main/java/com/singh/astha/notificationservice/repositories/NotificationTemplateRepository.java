package com.singh.astha.notificationservice.repositories;

import com.singh.astha.notificationservice.model.NotificationTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NotificationTemplateRepository extends MongoRepository<NotificationTemplate, String> {

    Optional<NotificationTemplate> findByTemplateId(String templateId);
}
