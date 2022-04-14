package com.singh.astha.notificationservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.singh.astha.notificationservice.constants.Constant;
import com.singh.astha.notificationservice.dtos.kafka.NotificationRequest;
import com.singh.astha.notificationservice.service.FCMService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngestionController {

    private final FCMService fcmService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public IngestionController(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    @KafkaListener(topics = {Constant.NOTIFICATION_INGESTION})
    public void repeat(String value) throws JsonProcessingException {
        NotificationRequest notificationRequest = objectMapper.readValue(value, NotificationRequest.class);
        fcmService.sendNotification(notificationRequest);
    }

}