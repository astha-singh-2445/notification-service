package com.singh.astha.notificationservice.controller;

import com.singh.astha.notificationservice.dtos.request.UserTokenRequestDto;
import com.singh.astha.notificationservice.dtos.response.UserTokenResponseDto;
import com.singh.astha.notificationservice.service.UserTokenService;
import com.singh.astha.notificationservice.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping(value = "/health")
    public ResponseEntity<String> getHealthApi() {
        return ResponseEntity.ok(Constants.OK);
    }

}

