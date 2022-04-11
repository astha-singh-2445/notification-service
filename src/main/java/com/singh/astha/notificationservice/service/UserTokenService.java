package com.singh.astha.notificationservice.service;

import com.singh.astha.notificationservice.dtos.request.UserTokenRequestDto;
import com.singh.astha.notificationservice.dtos.response.UserTokenResponseDto;
import com.singh.astha.notificationservice.model.UserToken;
import com.singh.astha.notificationservice.repositories.UserTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class UserTokenService {
    private final UserTokenRepository userTokenRepository;

    public UserTokenService(UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    public UserTokenResponseDto saveUserToken(UserTokenRequestDto userTokenDto) {
        UserToken userToken = UserToken.builder()
                .userId(userTokenDto.getUserId())
                .userToken(userTokenDto.getUserToken())
                .build();
        userToken = userTokenRepository.save(userToken);
        return UserTokenResponseDto.builder()
                .userId(userToken.getUserId())
                .userToken(userToken.getUserToken())
                .build();
    }
}
