package com.singh.astha.notificationservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.singh.astha.notificationservice.dtos.kafka.NotificationRequest;
import com.singh.astha.notificationservice.service.NotificationService;
import com.singh.astha.notificationservice.utils.Constants;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private final NotificationService notificationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = { Constants.NOTIFICATION_INGESTION })
    public void repeat(String value) throws JsonProcessingException {
        NotificationRequest notificationRequest = objectMapper.readValue(value, NotificationRequest.class);
        notificationService.sendNotification(notificationRequest);
    }
}
