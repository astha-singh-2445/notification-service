package com.singh.astha.notificationservice.dtos.request;

import com.singh.astha.notificationservice.enums.DeviceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationTokenRequestDto {

    @NotBlank
    private String notificationToken;

    @NotNull
    private DeviceType deviceType;
}
