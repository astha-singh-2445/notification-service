package com.singh.astha.notification.service.services.impl;

import com.singh.astha.notification.service.dtos.common.NotificationRequest;
import com.singh.astha.notification.service.exceptions.ResponseException;
import com.singh.astha.notification.service.models.NotificationTemplate;
import com.singh.astha.notification.service.models.NotificationToken;
import com.singh.astha.notification.service.repositories.NotificationTemplateRepository;
import com.singh.astha.notification.service.repositories.NotificationTokenRepository;
import com.singh.astha.notification.service.services.FCMService;
import com.singh.astha.notification.service.services.NotificationService;
import com.singh.astha.notification.service.utils.Constants;
import com.singh.astha.notification.service.utils.ErrorMessages;
import com.singh.astha.notification.service.utils.JsonUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationTokenRepository notificationTokenRepository;

    private final NotificationTemplateRepository notificationTemplateRepository;

    private final FCMService fcmService;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final JsonUtils jsonUtils;

    public NotificationServiceImpl(
            NotificationTokenRepository notificationTokenRepository,
            NotificationTemplateRepository notificationTemplateRepository,
            FCMService fcmService,
            KafkaTemplate<String, String> kafkaTemplate,
            JsonUtils jsonUtils
    ) {
        this.notificationTokenRepository = notificationTokenRepository;
        this.notificationTemplateRepository = notificationTemplateRepository;
        this.fcmService = fcmService;
        this.kafkaTemplate = kafkaTemplate;
        this.jsonUtils = jsonUtils;
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

    @Override
    public void enqueueToKafka(NotificationRequest notificationRequest) {

        String data = jsonUtils.writeValueAsString(notificationRequest);
        String userId = String.valueOf(notificationRequest.getUserId());
        kafkaTemplate.send(Constants.NOTIFICATION_INGESTION, userId, data);
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
