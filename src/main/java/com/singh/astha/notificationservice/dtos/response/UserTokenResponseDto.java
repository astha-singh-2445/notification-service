package com.singh.astha.notificationservice.dtos.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTokenResponseDto {

    private Long userId;
    private String userToken;
}
