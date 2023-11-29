package com.singh.astha.notificationservice.service.impl;

import com.singh.astha.notificationservice.dtos.kafka.NotificationRequest;
import com.singh.astha.notificationservice.exceptions.ResponseException;
import com.singh.astha.notificationservice.model.NotificationTemplate;
import com.singh.astha.notificationservice.model.NotificationToken;
import com.singh.astha.notificationservice.repositories.NotificationTemplateRepository;
import com.singh.astha.notificationservice.repositories.NotificationTokenRepository;
import com.singh.astha.notificationservice.service.FCMService;
import com.singh.astha.notificationservice.service.NotificationService;
import com.singh.astha.notificationservice.utils.Constants;
import com.singh.astha.notificationservice.utils.ErrorMessages;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationTokenRepository notificationTokenRepository;

    private final NotificationTemplateRepository notificationTemplateRepository;

    private final FCMService fcmService;

    public NotificationServiceImpl(
            NotificationTokenRepository notificationTokenRepository,
            NotificationTemplateRepository notificationTemplateRepository,
            FCMService fcmService
    ) {
        this.notificationTokenRepository = notificationTokenRepository;
        this.notificationTemplateRepository = notificationTemplateRepository;
        this.fcmService = fcmService;
    }

    public void sendNotification(NotificationRequest notificationRequest) {

        Optional<NotificationToken> notificationTokenOptional = notificationTokenRepository.findByUserId(
                notificationRequest.getUserId()
        );
        if (notificationTokenOptional.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.USER_TOKEN_NOT_EXIST);
        }
        NotificationToken notificationToken = notificationTokenOptional.get();
        Optional<NotificationTemplate> notificationTemplateOptional = notificationTemplateRepository
                .findByTemplateId(notificationRequest.getTemplateId());
        if (notificationTemplateOptional.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.TEMPLATE_IS_NOT_PRESENT);
        }
        NotificationTemplate notificationTemplate = notificationTemplateOptional.get();
        notificationToken.getTokens().forEach(token -> {
            Map<String, Object> data = getNotificationBody(
                    notificationRequest,
                    token.getNotificationToken(),
                    notificationTemplate
            );
            fcmService.sendFcmMessage(data);
        });
    }

    private Map<String, Object> getNotificationBody(
            NotificationRequest notificationRequest,
            String token,
            NotificationTemplate notificationTemplate
    ) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> notification = new HashMap<>();
        String title = StringSubstitutor.replace(
                notificationTemplate.getTitle(),
                notificationRequest.getTitlePlaceholder(),
                "{",
                "}"
        );
        notification.put(Constants.TITLE, title);
        String body = StringSubstitutor.replace(
                notificationTemplate.getBody(),
                notificationRequest.getBodyPlaceHolders(),
                "{",
                "}"
        );
        notification.put(Constants.BODY, body);
        message.put(Constants.NOTIFICATION, notification);
        message.put(Constants.TOKEN, token);
        data.put(Constants.MESSAGE, message);
        return data;
    }

}
