package com.singh.astha.notificationservice.dtos.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationTokenRequestDto {

    @NotBlank
    private String notificationToken;

    @NotNull
    private String deviceType;
}
