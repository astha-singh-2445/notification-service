package com.singh.astha.notification.service.dtos.request;

import com.singh.astha.notification.service.enums.DeviceType;
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
