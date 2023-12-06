package com.singh.astha.notification.service.dtos.transformers;

import com.singh.astha.notification.service.dtos.request.NotificationTokenRequestDto;
import com.singh.astha.notification.service.model.NotificationToken;
import com.singh.astha.notification.service.model.Token;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationTokenDtoTransformer {

    public NotificationToken convertNotificationTokenRequestDtoToUserToken(
            NotificationTokenRequestDto notificationTokenRequestDto,
            Long userId
    ) {
        Token token = new Token();
        token.setNotificationToken(notificationTokenRequestDto.getNotificationToken());
        token.setCreatedAt(Clock.systemDefaultZone().millis());
        token.setDeviceType(notificationTokenRequestDto.getDeviceType());
        List<Token> list = new ArrayList<>();
        list.add(token);
        return NotificationToken.builder()
                .userId(userId)
                .tokens(list)
                .build();
    }
}
