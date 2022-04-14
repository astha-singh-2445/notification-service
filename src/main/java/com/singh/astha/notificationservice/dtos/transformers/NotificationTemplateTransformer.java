package com.singh.astha.notificationservice.dtos.transformers;

import com.singh.astha.notificationservice.dtos.request.NotificationTemplateRequestDto;
import com.singh.astha.notificationservice.dtos.response.NotificationTemplateResponseDto;
import com.singh.astha.notificationservice.model.NotificationTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationTemplateTransformer {

    public NotificationTemplateResponseDto convertNotificationTemplateToNotificationTemplateResponseDto(
            NotificationTemplate notificationTemplate) {
        return NotificationTemplateResponseDto.builder()
                .id(notificationTemplate.getId())
                .template(notificationTemplate.getTemplate())
                .build();
    }

    public NotificationTemplate convertNotificationTemplateRequestDtoToNotificationTemplate(
            NotificationTemplateRequestDto notificationTemplateRequestDto) {
        return NotificationTemplate.builder()
                .template(notificationTemplateRequestDto.getTemplate())
                .build();
    }

}