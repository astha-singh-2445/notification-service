package com.singh.astha.notificationservice.service.impl;

import com.singh.astha.notificationservice.dtos.request.UserTokenRequestDto;
import com.singh.astha.notificationservice.dtos.response.UserTokenResponseDto;
import com.singh.astha.notificationservice.dtos.transformers.UserDtoTransformer;
import com.singh.astha.notificationservice.model.UserToken;
import com.singh.astha.notificationservice.repositories.UserTokenRepository;
import com.singh.astha.notificationservice.service.UserTokenService;
import org.springframework.stereotype.Service;

@Service
public class UserTokenServiceImpl implements UserTokenService {

    private final UserTokenRepository userTokenRepository;
    private final UserDtoTransformer userDtoTransformer;

    public UserTokenServiceImpl(UserTokenRepository userTokenRepository,
                                UserDtoTransformer userDtoTransformer) {
        this.userTokenRepository = userTokenRepository;
        this.userDtoTransformer = userDtoTransformer;
    }

    @Override
    public UserTokenResponseDto saveUserToken(UserTokenRequestDto userTokenRequestDto) {
        UserToken userToken = userDtoTransformer.convertUserTokenRequestDtoToUserToken(userTokenRequestDto);
        userToken = userTokenRepository.save(userToken);
        return userDtoTransformer.convertUserTokenToUserTokenResponseDto(userToken);
    }
}
