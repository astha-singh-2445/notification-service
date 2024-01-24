package com.singh.astha.notification.service.services;

import com.singh.astha.notification.service.dtos.common.NotificationRequest;

public interface NotificationService {

    void sendNotification(NotificationRequest notificationRequest);

    void enqueueToKafka(NotificationRequest notificationRequest);
}
