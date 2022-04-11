package com.singh.astha.notificationservice.controller;

import com.singh.astha.notificationservice.service.FCMService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class IngestionController {

    private final FCMService fcmService;

    public IngestionController(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    @KafkaListener(topics = {"notification_ingestion"})
    public void repeat(String value) throws IOException {
        fcmService.sendNotification("value");
        System.out.println(value);
    }

}
