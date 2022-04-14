package com.singh.astha.notificationservice.controller;

import com.singh.astha.notificationservice.dtos.request.NotificationTemplateRequestDto;
import com.singh.astha.notificationservice.dtos.response.NotificationTemplateResponseDto;
import com.singh.astha.notificationservice.service.NotificationTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/template")
public class TemplateController {

    private final NotificationTemplateService notificationTemplateService;

    public TemplateController(
            NotificationTemplateService notificationTemplateService) {
        this.notificationTemplateService = notificationTemplateService;
    }

    @PostMapping
    public ResponseEntity<NotificationTemplateResponseDto> saveTemplate(
            @Valid @RequestBody NotificationTemplateRequestDto notificationTemplateRequestDto) {
        return ResponseEntity.ok()
                .body(notificationTemplateService.saveNotificationTemplate(notificationTemplateRequestDto));
    }
}
