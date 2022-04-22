package com.singh.astha.notificationservice.dtos.transformers;

import com.singh.astha.notificationservice.dtos.request.NotificationTokenRequestDto;
import com.singh.astha.notificationservice.model.NotificationToken;
import com.singh.astha.notificationservice.model.Token;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationTokenDtoTransformer {

    public NotificationToken convertNotificationTokenRequestDtoToUserToken(
            NotificationTokenRequestDto notificationTokenRequestDto,
            Long userId) {
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
