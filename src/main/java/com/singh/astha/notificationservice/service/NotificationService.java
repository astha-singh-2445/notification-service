package com.singh.astha.notificationservice.service;

import com.singh.astha.notificationservice.dtos.kafka.NotificationRequest;

public interface NotificationService {

    void sendNotification(NotificationRequest notificationRequest);

}
