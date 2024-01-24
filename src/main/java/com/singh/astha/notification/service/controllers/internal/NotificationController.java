package com.singh.astha.notification.service.controllers.internal;

import com.singh.astha.notification.service.dtos.common.NotificationRequest;
import com.singh.astha.notification.service.dtos.response.wrapper.ResponseWrapper;
import com.singh.astha.notification.service.services.NotificationService;
import com.singh.astha.notification.service.utils.MessageConstants;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService
    ) {
        this.notificationService = notificationService;

    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<String>> sendNotification(
            @Valid @RequestBody NotificationRequest notificationRequest
    ) {
        notificationService.sendNotification(notificationRequest);
        return ResponseEntity.ok().body(ResponseWrapper.success(MessageConstants.SUCCESSFULLY_SENT_NOTIFICATION));
    }

    @PostMapping("/enqueue")
    public ResponseEntity<ResponseWrapper<String>> enqueueToKafka(
            @Valid @RequestBody NotificationRequest notificationRequest
    ) {
        notificationService.enqueueToKafka(notificationRequest);
        return ResponseEntity.ok()
                .body(ResponseWrapper.success(MessageConstants.SUCCESSFULLY_ENQUEUED_MESSAGE_IN_QUEUE));
    }
}
