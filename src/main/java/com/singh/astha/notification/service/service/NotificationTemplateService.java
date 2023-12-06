package com.singh.astha.notification.service.service;

import com.singh.astha.notification.service.dtos.request.NotificationTemplateRequestDto;
import com.singh.astha.notification.service.dtos.response.NotificationTemplateResponseDto;

public interface NotificationTemplateService {

    NotificationTemplateResponseDto saveNotificationTemplate(
            NotificationTemplateRequestDto notificationTemplateRequestDto
    );

    NotificationTemplateResponseDto getNotificationTemplate(String templateId);
}
