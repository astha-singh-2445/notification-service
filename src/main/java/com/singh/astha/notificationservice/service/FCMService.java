package com.singh.astha.notificationservice.service;

import com.singh.astha.notificationservice.dtos.kafka.NotificationRequest;

public interface FCMService {

    String sendNotification(NotificationRequest notificationRequest);
}
