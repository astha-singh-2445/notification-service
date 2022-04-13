package com.singh.astha.notificationservice.dtos.transformers;

import com.singh.astha.notificationservice.dtos.request.UserTokenRequestDto;
import com.singh.astha.notificationservice.dtos.response.UserTokenResponseDto;
import com.singh.astha.notificationservice.model.UserToken;
import org.springframework.stereotype.Component;

@Component
public class UserDtoTransformer {

    public UserTokenResponseDto convertUserTokenToUserTokenResponseDto(UserToken userToken) {
        return UserTokenResponseDto.builder()
                .userId(userToken.getUserId())
                .userToken(userToken.getUserToken())
                .build();
    }

    public UserToken convertUserTokenRequestDtoToUserToken(UserTokenRequestDto userTokenRequestDto) {
        return UserToken.builder()
                .userId(userTokenRequestDto.getUserId())
                .userToken(userTokenRequestDto.getUserToken())
                .build();
    }
}
