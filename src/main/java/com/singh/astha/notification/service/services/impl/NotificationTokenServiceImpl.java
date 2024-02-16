package com.singh.astha.notification.service.services.impl;

import com.singh.astha.notification.service.dtos.request.NotificationTokenRequestDto;
import com.singh.astha.notification.service.dtos.response.wrapper.ErrorResponse;
import com.singh.astha.notification.service.dtos.transformers.NotificationTokenDtoTransformer;
import com.singh.astha.notification.service.enums.ErrorCode;
import com.singh.astha.notification.service.exceptions.ResponseException;
import com.singh.astha.notification.service.models.NotificationToken;
import com.singh.astha.notification.service.models.Token;
import com.singh.astha.notification.service.repositories.NotificationTokenRepository;
import com.singh.astha.notification.service.services.NotificationTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Optional;

@Service
public class NotificationTokenServiceImpl implements NotificationTokenService {

    private final NotificationTokenRepository notificationTokenRepository;

    private final NotificationTokenDtoTransformer notificationTokenDtoTransformer;

    public NotificationTokenServiceImpl(
            NotificationTokenRepository notificationTokenRepository,
            NotificationTokenDtoTransformer notificationTokenDtoTransformer
    ) {
        this.notificationTokenRepository = notificationTokenRepository;
        this.notificationTokenDtoTransformer = notificationTokenDtoTransformer;
    }

    @Override
    public void saveNotificationToken(NotificationTokenRequestDto notificationTokenRequestDto, Long userId) {
        Optional<NotificationToken> notificationTokenOptional = notificationTokenRepository.findByUserId(userId);
        NotificationToken notificationToken;
        if (notificationTokenOptional.isPresent()) {
            notificationToken = notificationTokenOptional.get();
            boolean isTokenExist = notificationToken.getTokens()
                    .stream()
                    .anyMatch(
                            res -> res.getNotificationToken().equals(notificationTokenRequestDto.getNotificationToken())
                    );

            if (isTokenExist) {
                throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorResponse.from(ErrorCode.TOKEN_ALREADY_EXISTS));
            }
            Token token = new Token();
            token.setCreatedAt(Clock.systemDefaultZone().millis());
            token.setNotificationToken(notificationTokenRequestDto.getNotificationToken());
            token.setDeviceType(notificationTokenRequestDto.getDeviceType());
            notificationToken.getTokens().add(token);
        } else {
            notificationToken = notificationTokenDtoTransformer.convertNotificationTokenRequestDtoToUserToken(
                    notificationTokenRequestDto,
                    userId
            );
        }
        notificationTokenRepository.save(notificationToken);
    }
}
