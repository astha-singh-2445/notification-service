package com.singh.astha.notificationservice.service.impl;

import com.singh.astha.notificationservice.dtos.request.NotificationTemplateRequestDto;
import com.singh.astha.notificationservice.dtos.response.NotificationTemplateResponseDto;
import com.singh.astha.notificationservice.dtos.transformers.NotificationTemplateTransformer;
import com.singh.astha.notificationservice.exceptions.ResponseException;
import com.singh.astha.notificationservice.model.NotificationTemplate;
import com.singh.astha.notificationservice.repositories.NotificationTemplateRepository;
import com.singh.astha.notificationservice.service.NotificationTemplateService;
import com.singh.astha.notificationservice.utils.ErrorMessages;
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
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.TEMPLATE_ALREADY_EXISTS);
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
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.TEMPLATE_NOT_EXIST);
        }
        NotificationTemplate notificationTemplate = notificationTemplateOptional.get();
        return notificationTemplateTransformer.convertNotificationTemplateToNotificationTemplateResponseDto(
                notificationTemplate
        );
    }
}
