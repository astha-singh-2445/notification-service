package com.singh.astha.notification.service.service;

import com.singh.astha.notification.service.dtos.request.NotificationTokenRequestDto;

public interface NotificationTokenService {

    void saveNotificationToken(NotificationTokenRequestDto userTokenDto, Long userId);
}
