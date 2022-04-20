package com.singh.astha.notificationservice.service;

import com.singh.astha.notificationservice.dtos.request.UserTokenRequestDto;

public interface UserTokenService {

    void saveUserToken(UserTokenRequestDto userTokenDto, Long userId);
}
