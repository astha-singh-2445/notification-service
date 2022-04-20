package com.singh.astha.notificationservice.service.impl;

import com.singh.astha.notificationservice.dtos.request.UserTokenRequestDto;
import com.singh.astha.notificationservice.dtos.response.UserTokenResponseDto;
import com.singh.astha.notificationservice.dtos.transformers.UserDtoTransformer;
import com.singh.astha.notificationservice.exceptions.ResponseException;
import com.singh.astha.notificationservice.model.Token;
import com.singh.astha.notificationservice.model.UserToken;
import com.singh.astha.notificationservice.repositories.UserTokenRepository;
import com.singh.astha.notificationservice.service.UserTokenService;
import com.singh.astha.notificationservice.utils.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<UserToken> userTokenOptional = userTokenRepository.findByUserId(userTokenRequestDto.getUserId());
        UserToken userToken=new UserToken();
        if (userTokenOptional.isPresent()) {
            userToken = userTokenOptional.get();
            Token token =new Token();
            token.setCreatedAt(Clock.systemDefaultZone().millis());
            token.setUserToken(userTokenRequestDto.getUserToken());
            userToken.getUserToken().add(token);
        }
        else {
            userToken = userDtoTransformer.convertUserTokenRequestDtoToUserToken(userTokenRequestDto);
        }
        userToken = userTokenRepository.save(userToken);
        return userDtoTransformer.convertUserTokenToUserTokenResponseDto(userToken);
    }
}
