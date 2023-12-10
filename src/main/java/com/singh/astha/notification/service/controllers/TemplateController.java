package com.singh.astha.notification.service.controllers;

import com.singh.astha.notification.service.dtos.request.NotificationTemplateRequestDto;
import com.singh.astha.notification.service.dtos.response.NotificationTemplateResponseDto;
import com.singh.astha.notification.service.dtos.response.wrapper.ResponseWrapper;
import com.singh.astha.notification.service.services.NotificationTemplateService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification/template")
@SecurityRequirement(name = "bearerAuth")
public class TemplateController {

    private final NotificationTemplateService notificationTemplateService;

    public TemplateController(
            NotificationTemplateService notificationTemplateService
    ) {
        this.notificationTemplateService = notificationTemplateService;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<NotificationTemplateResponseDto>> saveNotificationTemplate(
            @Valid @RequestBody NotificationTemplateRequestDto notificationTemplateRequestDto
    ) {
        return ResponseEntity.ok()
                .body(
                        ResponseWrapper.success(
                                notificationTemplateService.saveNotificationTemplate(notificationTemplateRequestDto)
                        )
                );
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<NotificationTemplateResponseDto>> getNotificationTemplate(
            @RequestParam String templateId
    ) {
        return ResponseEntity.ok()
                .body(
                        ResponseWrapper.success(
                                notificationTemplateService.getNotificationTemplate(templateId)
                        )
                );
    }
}
