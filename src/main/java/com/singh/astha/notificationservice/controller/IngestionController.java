package com.singh.astha.notificationservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singh.astha.notificationservice.dtos.kafka.NotificationRequest;
import com.singh.astha.notificationservice.service.FCMService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class IngestionController {

    private final FCMService fcmService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public IngestionController(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    @KafkaListener(topics = {"notification_ingestion"})
    public void repeat(String value) throws IOException {
        NotificationRequest notificationRequest = objectMapper.readValue(value, NotificationRequest.class);
        fcmService.sendNotification(notificationRequest);
        System.out.println(value);
    }

}
