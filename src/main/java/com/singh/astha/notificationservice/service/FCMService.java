package com.singh.astha.notificationservice.service;

import com.singh.astha.notificationservice.dtos.kafka.NotificationRequest;

import java.io.IOException;

public interface FCMService {

    String sendNotification(NotificationRequest notificationRequest) throws IOException;
}
