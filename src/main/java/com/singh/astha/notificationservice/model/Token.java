package com.singh.astha.notificationservice.model;

import com.singh.astha.notificationservice.enums.DeviceType;
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
