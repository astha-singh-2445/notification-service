package com.singh.astha.notification.service.model;

import com.singh.astha.notification.service.enums.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    private Long createdAt;

    private String notificationToken;

    private DeviceType deviceType;

}
