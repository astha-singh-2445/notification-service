package com.singh.astha.notificationservice.service.impl;

import com.singh.astha.notificationservice.dtos.request.UserTokenRequestDto;
import com.singh.astha.notificationservice.dtos.transformers.UserDtoTransformer;
import com.singh.astha.notificationservice.exceptions.ResponseException;
import com.singh.astha.notificationservice.model.Token;
import com.singh.astha.notificationservice.model.UserToken;
import com.singh.astha.notificationservice.repositories.UserTokenRepository;
import com.singh.astha.notificationservice.service.UserTokenService;
import com.singh.astha.notificationservice.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Clock;
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
    public void saveUserToken(UserTokenRequestDto userTokenRequestDto, Long userId) {
        Optional<UserToken> userTokenOptional = userTokenRepository.findByUserId(userId);
        UserToken userToken;
        if (userTokenOptional.isPresent()) {
            userToken = userTokenOptional.get();
            Optional<UserToken> optionalUserToken = userTokenRepository.findByNotificationToken(
                    userTokenRequestDto.getNotificationToken());
            if (optionalUserToken.isPresent()) {
                throw new ResponseException(HttpStatus.BAD_REQUEST, Constants.TOKEN_ALREADY_EXISTS);
            }
            Token token = new Token();
            token.setCreatedAt(Clock.systemDefaultZone().millis());
            token.setNotificationToken(userTokenRequestDto.getNotificationToken());
            userToken.getTokens().add(token);
        } else {
            userToken = userDtoTransformer.convertUserTokenRequestDtoToUserToken(userTokenRequestDto);
        }
        userTokenRepository.save(userToken);
    }
}
