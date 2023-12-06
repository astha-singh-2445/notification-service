package com.singh.astha.notification.service.service;

import com.singh.astha.notification.service.dtos.kafka.NotificationRequest;

public interface NotificationService {

    void sendNotification(NotificationRequest notificationRequest);

}
