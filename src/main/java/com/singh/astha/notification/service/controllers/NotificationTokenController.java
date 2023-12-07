package com.singh.astha.notification.service.controllers;

import com.singh.astha.notification.service.dtos.request.NotificationTokenRequestDto;
import com.singh.astha.notification.service.dtos.response.NotificationTokenResponseDto;
import com.singh.astha.notification.service.dtos.response.ResponseWrapper;
import com.singh.astha.notification.service.services.NotificationTokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification/token")
@SecurityRequirement(name = "bearerAuth")
public class NotificationTokenController {

    private final NotificationTokenService notificationTokenService;

    public NotificationTokenController(NotificationTokenService notificationTokenService) {
        this.notificationTokenService = notificationTokenService;
    }

    @PostMapping()
    public ResponseEntity<ResponseWrapper<NotificationTokenResponseDto>> saveNotificationToken(
            Authentication authentication,
            @Valid @RequestBody NotificationTokenRequestDto notificationTokenRequestDto
    ) {
        Long userId = Long.valueOf(authentication.getName());
        notificationTokenService.saveNotificationToken(notificationTokenRequestDto, userId);
        return ResponseEntity.ok().body(ResponseWrapper.success(null));
    }

}
