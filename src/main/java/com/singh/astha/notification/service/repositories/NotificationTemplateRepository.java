package com.singh.astha.notification.service.repositories;

import com.singh.astha.notification.service.models.NotificationTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NotificationTemplateRepository extends MongoRepository<NotificationTemplate, String> {

    Optional<NotificationTemplate> findByTemplateId(String templateId);
}
