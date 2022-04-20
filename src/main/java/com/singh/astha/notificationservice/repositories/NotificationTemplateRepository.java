package com.singh.astha.notificationservice.repositories;

import com.singh.astha.notificationservice.model.NotificationTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationTemplateRepository extends MongoRepository<NotificationTemplate, String> {


}
