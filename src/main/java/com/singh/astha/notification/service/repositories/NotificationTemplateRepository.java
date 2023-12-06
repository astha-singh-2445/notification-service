package com.singh.astha.notification.service.repositories;

import com.singh.astha.notification.service.model.NotificationTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NotificationTemplateRepository extends MongoRepository<NotificationTemplate, String> {

    Optional<NotificationTemplate> findByTemplateId(String templateId);
}
