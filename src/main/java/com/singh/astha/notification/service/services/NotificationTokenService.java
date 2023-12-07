package com.singh.astha.notification.service.services;

import com.singh.astha.notification.service.dtos.request.NotificationTokenRequestDto;

public interface NotificationTokenService {

    void saveNotificationToken(NotificationTokenRequestDto userTokenDto, Long userId);
}
