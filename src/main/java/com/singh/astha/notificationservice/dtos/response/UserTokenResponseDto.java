package com.singh.astha.notificationservice.dtos.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTokenResponseDto {

    private String id;
    private Long userId;
    private List<Object> userToken;
}
