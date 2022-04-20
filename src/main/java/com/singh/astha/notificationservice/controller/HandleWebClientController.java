package com.singh.astha.notificationservice.controller;

import com.singh.astha.notificationservice.dtos.kafka.NotificationRequest;
import com.singh.astha.notificationservice.exceptions.ResponseException;
import com.singh.astha.notificationservice.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class HandleWebClientController {

    private final NotificationService notificationService;

    public HandleWebClientController(
            NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping()
    public String handleWebclient() {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setUserId(13771888056598551L);
        notificationRequest.setTemplateId("62580ca3b352db798c4ec8ca");
        HashMap<String, String> values = new HashMap<>();
        values.put("medicine-name", "Paracetamol");
        notificationRequest.setPlaceHolder(values);
        try {
            notificationService.sendNotification(notificationRequest);
        } catch (Exception e) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Not able to send notification");
        }
        return "Success";
    }

}
