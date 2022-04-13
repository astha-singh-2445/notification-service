package com.singh.astha.notificationservice.service;

import com.singh.astha.notificationservice.dtos.request.UserTokenRequestDto;
import com.singh.astha.notificationservice.dtos.response.UserTokenResponseDto;

public interface UserTokenService {

    public UserTokenResponseDto saveUserToken(UserTokenRequestDto userTokenDto);
}
