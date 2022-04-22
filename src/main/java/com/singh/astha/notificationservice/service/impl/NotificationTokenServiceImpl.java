package com.singh.astha.notificationservice.service.impl;

import com.singh.astha.notificationservice.dtos.request.NotificationTokenRequestDto;
import com.singh.astha.notificationservice.dtos.transformers.NotificationTokenDtoTransformer;
import com.singh.astha.notificationservice.exceptions.ResponseException;
import com.singh.astha.notificationservice.model.NotificationToken;
import com.singh.astha.notificationservice.model.Token;
import com.singh.astha.notificationservice.repositories.NotificationTokenRepository;
import com.singh.astha.notificationservice.service.NotificationTokenService;
import com.singh.astha.notificationservice.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationTokenServiceImpl implements NotificationTokenService {

    private final NotificationTokenRepository notificationTokenRepository;
    private final NotificationTokenDtoTransformer notificationTokenDtoTransformer;

    public NotificationTokenServiceImpl(NotificationTokenRepository notificationTokenRepository,
                                        NotificationTokenDtoTransformer notificationTokenDtoTransformer) {
        this.notificationTokenRepository = notificationTokenRepository;
        this.notificationTokenDtoTransformer = notificationTokenDtoTransformer;
    }

    @Override
    public void saveNotificationToken(NotificationTokenRequestDto notificationTokenRequestDto, Long userId) {
        Optional<NotificationToken> notificationTokenOptional = notificationTokenRepository.findByUserId(userId);
        NotificationToken notificationToken;
        if (notificationTokenOptional.isPresent()) {
            notificationToken = notificationTokenOptional.get();
            List<Token> savedToken = notificationToken.getTokens().stream()
                    .filter(res -> res.getNotificationToken()
                            .equals(notificationTokenRequestDto.getNotificationToken()))
                    .collect(
                            Collectors.toList());
            if (!savedToken.isEmpty()) {
                throw new ResponseException(HttpStatus.BAD_REQUEST, Constants.TOKEN_ALREADY_EXISTS);
            }
            Token token = new Token();
            token.setCreatedAt(Clock.systemDefaultZone().millis());
            token.setNotificationToken(notificationTokenRequestDto.getNotificationToken());
            token.setDeviceType(notificationTokenRequestDto.getDeviceType());
            notificationToken.getTokens().add(token);
        } else {
            notificationToken = notificationTokenDtoTransformer.convertNotificationTokenRequestDtoToUserToken(
                    notificationTokenRequestDto, userId);
        }
        notificationTokenRepository.save(notificationToken);
    }
}
