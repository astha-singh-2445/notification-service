package com.singh.astha.notification.service.services;

import com.singh.astha.notification.service.dtos.kafka.NotificationRequest;

public interface NotificationService {

    void sendNotification(NotificationRequest notificationRequest);

}
