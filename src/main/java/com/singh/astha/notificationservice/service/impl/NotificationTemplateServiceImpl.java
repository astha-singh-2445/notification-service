package com.singh.astha.notificationservice.service.impl;

import com.singh.astha.notificationservice.dtos.request.NotificationTemplateRequestDto;
import com.singh.astha.notificationservice.dtos.response.NotificationTemplateResponseDto;
import com.singh.astha.notificationservice.dtos.transformers.NotificationTemplateTransformer;
import com.singh.astha.notificationservice.model.NotificationTemplate;
import com.singh.astha.notificationservice.repositories.NotificationTemplateRepository;
import com.singh.astha.notificationservice.service.NotificationTemplateService;
import org.springframework.stereotype.Service;

@Service
public class NotificationTemplateServiceImpl implements NotificationTemplateService {

    private final NotificationTemplateTransformer notificationTemplateTransformer;
    private final NotificationTemplateRepository notificationTemplateRepository;

    public NotificationTemplateServiceImpl(
            NotificationTemplateTransformer notificationTemplateTransformer,
            NotificationTemplateRepository notificationTemplateRepository) {
        this.notificationTemplateTransformer = notificationTemplateTransformer;
        this.notificationTemplateRepository = notificationTemplateRepository;
    }

    @Override
    public NotificationTemplateResponseDto saveNotificationTemplate(
            NotificationTemplateRequestDto notificationTemplateRequestDto) {
        NotificationTemplate notificationTemplate = notificationTemplateTransformer.convertNotificationTemplateRequestDtoToNotificationTemplate(
                notificationTemplateRequestDto);
        notificationTemplate = notificationTemplateRepository.save(notificationTemplate);
        return notificationTemplateTransformer.convertNotificationTemplateToNotificationTemplateResponseDto(
                notificationTemplate);
    }
}
