package com.singh.astha.notificationservice.controller;

import com.singh.astha.notificationservice.dtos.request.UserTokenRequestDto;
import com.singh.astha.notificationservice.dtos.response.UserTokenResponseDto;
import com.singh.astha.notificationservice.service.UserTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserTokenController {

    private final UserTokenService userTokenService;

    public UserTokenController(UserTokenService userTokenService) {
        this.userTokenService = userTokenService;
    }

    @PostMapping(value = "/user")
    public ResponseEntity<UserTokenResponseDto> getUserToken(@RequestBody UserTokenRequestDto userTokenDto) {
        return ResponseEntity.ok().body(userTokenService.saveUserToken(userTokenDto));
    }
}
