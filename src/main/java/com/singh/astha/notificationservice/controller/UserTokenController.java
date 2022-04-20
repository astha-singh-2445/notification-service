package com.singh.astha.notificationservice.controller;

import com.singh.astha.notificationservice.dtos.request.UserTokenRequestDto;
import com.singh.astha.notificationservice.dtos.response.ResponseWrapper;
import com.singh.astha.notificationservice.dtos.response.UserTokenResponseDto;
import com.singh.astha.notificationservice.service.UserTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/token")
public class UserTokenController {

    private final UserTokenService userTokenService;

    public UserTokenController(UserTokenService userTokenService) {
        this.userTokenService = userTokenService;
    }

    @PostMapping()
    public ResponseEntity<ResponseWrapper<UserTokenResponseDto>> saveUserToken(Authentication authentication,
                                                                               @Valid @RequestBody UserTokenRequestDto userTokenDto) {
        Long userId = Long.valueOf(authentication.getName());
        userTokenService.saveUserToken(userTokenDto, userId);
        return ResponseEntity.ok().body(ResponseWrapper.success(null));
    }

}
