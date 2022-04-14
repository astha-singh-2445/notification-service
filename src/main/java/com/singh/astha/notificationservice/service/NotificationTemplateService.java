package com.singh.astha.notificationservice.service;

import com.singh.astha.notificationservice.dtos.request.NotificationTemplateRequestDto;
import com.singh.astha.notificationservice.dtos.response.NotificationTemplateResponseDto;

public interface NotificationTemplateService {

    public NotificationTemplateResponseDto saveNotificationTemplate(
            NotificationTemplateRequestDto notificationTemplateRequestDto);
}
