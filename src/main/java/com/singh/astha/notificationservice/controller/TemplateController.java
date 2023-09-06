package com.singh.astha.notificationservice.controller;

import com.singh.astha.notificationservice.dtos.request.NotificationTemplateRequestDto;
import com.singh.astha.notificationservice.dtos.response.NotificationTemplateResponseDto;
import com.singh.astha.notificationservice.dtos.response.ResponseWrapper;
import com.singh.astha.notificationservice.service.NotificationTemplateService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/notification/template")
@SecurityRequirement(name = "bearerAuth")
public class TemplateController {

    private final NotificationTemplateService notificationTemplateService;

    public TemplateController(
            NotificationTemplateService notificationTemplateService) {
        this.notificationTemplateService = notificationTemplateService;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<NotificationTemplateResponseDto>> saveNotificationTemplate(
            @Valid @RequestBody NotificationTemplateRequestDto notificationTemplateRequestDto) {
        return ResponseEntity.ok()
                .body(ResponseWrapper.success(
                        notificationTemplateService.saveNotificationTemplate(notificationTemplateRequestDto)));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<NotificationTemplateResponseDto>> getNotificationTemplate(
            @RequestParam String templateId) {
        return ResponseEntity.ok()
                .body(ResponseWrapper.success(
                        notificationTemplateService.getNotificationTemplate(templateId)));
    }
}
