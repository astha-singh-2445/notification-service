package com.singh.astha.notification.service.dtos.transformers;

import com.singh.astha.notification.service.dtos.request.NotificationTemplateRequestDto;
import com.singh.astha.notification.service.dtos.response.NotificationTemplateResponseDto;
import com.singh.astha.notification.service.model.NotificationTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationTemplateTransformer {

    public NotificationTemplateResponseDto convertNotificationTemplateToNotificationTemplateResponseDto(
            NotificationTemplate notificationTemplate
    ) {
        return NotificationTemplateResponseDto.builder()
                .id(notificationTemplate.getId())
                .templateId(notificationTemplate.getTemplateId())
                .title(notificationTemplate.getTitle())
                .body(notificationTemplate.getBody())
                .imageLink(notificationTemplate.getImageLink())
                .build();
    }

    public NotificationTemplate convertNotificationTemplateRequestDtoToNotificationTemplate(
            NotificationTemplateRequestDto notificationTemplateRequestDto
    ) {
        return NotificationTemplate.builder()
                .templateId(notificationTemplateRequestDto.getTemplateId())
                .title(notificationTemplateRequestDto.getTitle())
                .body(notificationTemplateRequestDto.getBody())
                .imageLink(notificationTemplateRequestDto.getImageLink())
                .build();
    }

}
