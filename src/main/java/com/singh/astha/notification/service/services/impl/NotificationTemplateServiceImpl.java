package com.singh.astha.notification.service.services.impl;

import com.singh.astha.notification.service.dtos.request.NotificationTemplateRequestDto;
import com.singh.astha.notification.service.dtos.response.NotificationTemplateResponseDto;
import com.singh.astha.notification.service.dtos.response.wrapper.ErrorResponse;
import com.singh.astha.notification.service.dtos.transformers.NotificationTemplateTransformer;
import com.singh.astha.notification.service.enums.ErrorCode;
import com.singh.astha.notification.service.exceptions.ResponseException;
import com.singh.astha.notification.service.models.NotificationTemplate;
import com.singh.astha.notification.service.repositories.NotificationTemplateRepository;
import com.singh.astha.notification.service.services.NotificationTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationTemplateServiceImpl implements NotificationTemplateService {

    private final NotificationTemplateTransformer notificationTemplateTransformer;

    private final NotificationTemplateRepository notificationTemplateRepository;

    public NotificationTemplateServiceImpl(
            NotificationTemplateTransformer notificationTemplateTransformer,
            NotificationTemplateRepository notificationTemplateRepository
    ) {
        this.notificationTemplateTransformer = notificationTemplateTransformer;
        this.notificationTemplateRepository = notificationTemplateRepository;
    }

    @Override
    public NotificationTemplateResponseDto saveNotificationTemplate(
            NotificationTemplateRequestDto notificationTemplateRequestDto
    ) {
        Optional<NotificationTemplate> notificationTemplateOptional = notificationTemplateRepository.findByTemplateId(
                notificationTemplateRequestDto.getTemplateId()
        );
        if (notificationTemplateOptional.isPresent()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorResponse.from(ErrorCode.TEMPLATE_ALREADY_EXISTS));
        }
        NotificationTemplate notificationTemplate = notificationTemplateTransformer
                .convertNotificationTemplateRequestDtoToNotificationTemplate(
                        notificationTemplateRequestDto
                );

        notificationTemplate = notificationTemplateRepository.save(notificationTemplate);
        return notificationTemplateTransformer.convertNotificationTemplateToNotificationTemplateResponseDto(
                notificationTemplate
        );
    }

    @Override
    public NotificationTemplateResponseDto getNotificationTemplate(String templateId) {
        Optional<NotificationTemplate> notificationTemplateOptional = notificationTemplateRepository.findByTemplateId(
                templateId
        );
        if (notificationTemplateOptional.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorResponse.from(ErrorCode.TEMPLATE_NOT_EXIST));
        }
        NotificationTemplate notificationTemplate = notificationTemplateOptional.get();
        return notificationTemplateTransformer.convertNotificationTemplateToNotificationTemplateResponseDto(
                notificationTemplate
        );
    }
}
