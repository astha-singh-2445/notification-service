package com.singh.astha.notificationservice.dtos.transformers;

import com.singh.astha.notificationservice.dtos.request.UserTokenRequestDto;
import com.singh.astha.notificationservice.model.Token;
import com.singh.astha.notificationservice.model.UserToken;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDtoTransformer {

    public UserToken convertUserTokenRequestDtoToUserToken(UserTokenRequestDto userTokenRequestDto) {
        Token token = new Token();
        token.setNotificationToken(userTokenRequestDto.getNotificationToken());
        token.setCreatedAt(Clock.systemDefaultZone().millis());
        List<Token> list = new ArrayList<>();
        list.add(token);
        return UserToken.builder()
                .userId(userTokenRequestDto.getUserId())
                .tokens(list)
                .build();
    }
}
