package com.singh.astha.notification.service.controllers;

import com.singh.astha.notification.service.dtos.common.NotificationRequest;
import com.singh.astha.notification.service.dtos.response.wrapper.ResponseWrapper;
import com.singh.astha.notification.service.services.NotificationService;
import com.singh.astha.notification.service.utils.Constants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/notifications")
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<String>> sendNotification(
            @Valid @RequestBody NotificationRequest notificationRequest
    ) {
        notificationService.sendNotification(notificationRequest);
        return ResponseEntity.ok().body(ResponseWrapper.success(Constants.SUCCESSFULLY_SEND_NOTIFICATION));
    }
}
