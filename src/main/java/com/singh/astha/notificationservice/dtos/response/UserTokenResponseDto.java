package com.singh.astha.notificationservice.dtos.response;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTokenResponseDto {
    private Long userId;
    private String userToken;
}
