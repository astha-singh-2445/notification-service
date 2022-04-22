package com.singh.astha.notificationservice.service;

import com.singh.astha.notificationservice.dtos.request.NotificationTokenRequestDto;

public interface NotificationTokenService {

    void saveNotificationToken(NotificationTokenRequestDto userTokenDto, Long userId);
}
