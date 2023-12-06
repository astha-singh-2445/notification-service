package com.singh.astha.notification.service.dtos.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationTokenResponseDto {

    private String id;

    private Long userId;

    private List<Object> userToken;
}
