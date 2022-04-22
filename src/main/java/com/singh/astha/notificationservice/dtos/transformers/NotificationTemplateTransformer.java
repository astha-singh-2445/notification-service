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
                .templateId(notificationTemplate.getTemplateId())
                .title(notificationTemplate.getTitle())
                .body(notificationTemplate.getBody())
                .imageLink(notificationTemplate.getImageLink())
                .build();
    }

    public NotificationTemplate convertNotificationTemplateRequestDtoToNotificationTemplate(
            NotificationTemplateRequestDto notificationTemplateRequestDto) {
        return NotificationTemplate.builder()
                .templateId(notificationTemplateRequestDto.getTemplateId())
                .title(notificationTemplateRequestDto.getTitle())
                .body(notificationTemplateRequestDto.getBody())
                .imageLink(notificationTemplateRequestDto.getImageLink())
                .build();
    }

}
