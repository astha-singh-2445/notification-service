package com.singh.astha.notificationservice.controller;

import com.singh.astha.notificationservice.dtos.request.NotificationTokenRequestDto;
import com.singh.astha.notificationservice.dtos.response.NotificationTokenResponseDto;
import com.singh.astha.notificationservice.dtos.response.ResponseWrapper;
import com.singh.astha.notificationservice.service.NotificationTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/notification/token")
public class NotificationTokenController {

    private final NotificationTokenService notificationTokenService;

    public NotificationTokenController(NotificationTokenService notificationTokenService) {
        this.notificationTokenService = notificationTokenService;
    }

    @PostMapping()
    public ResponseEntity<ResponseWrapper<NotificationTokenResponseDto>> saveNotificationToken(
            Authentication authentication,
            @Valid @RequestBody NotificationTokenRequestDto notificationTokenRequestDto) {
        Long userId = Long.valueOf(authentication.getName());
        notificationTokenService.saveNotificationToken(notificationTokenRequestDto, userId);
        return ResponseEntity.ok().body(ResponseWrapper.success(null));
    }

}
