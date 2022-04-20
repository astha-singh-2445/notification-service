package com.singh.astha.notificationservice.service.impl;

import com.singh.astha.notificationservice.dtos.kafka.NotificationRequest;
import com.singh.astha.notificationservice.exceptions.ResponseException;
import com.singh.astha.notificationservice.model.NotificationTemplate;
import com.singh.astha.notificationservice.model.UserToken;
import com.singh.astha.notificationservice.repositories.NotificationTemplateRepository;
import com.singh.astha.notificationservice.repositories.UserTokenRepository;
import com.singh.astha.notificationservice.service.FCMService;
import com.singh.astha.notificationservice.service.NotificationService;
import com.singh.astha.notificationservice.utils.Constants;
import com.singh.astha.notificationservice.utils.ErrorMessages;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NotificationServiceImpl implements NotificationService {

    private final UserTokenRepository userTokenRepository;
    private final NotificationTemplateRepository notificationTemplateRepository;
    private final FCMService fcmService;

    public NotificationServiceImpl(
            UserTokenRepository userTokenRepository,
            NotificationTemplateRepository notificationTemplateRepository,
            FCMService fcmService) {
        this.userTokenRepository = userTokenRepository;
        this.notificationTemplateRepository = notificationTemplateRepository;
        this.fcmService = fcmService;
    }

    public void sendNotification(NotificationRequest notificationRequest) {

        Optional<UserToken> userTokenOptional = userTokenRepository.findByUserId(notificationRequest.getUserId());
        if (userTokenOptional.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.USER_TOKEN_NOT_EXIST);
        }
        UserToken userToken = userTokenOptional.get();
        Optional<NotificationTemplate> notificationTemplateOptional =
                notificationTemplateRepository.findById(notificationRequest.getTemplateId());
        if (notificationTemplateOptional.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.TEMPLATE_IS_NOT_PRESENT);
        }
        NotificationTemplate notificationTemplate = notificationTemplateOptional.get();
        userToken.getTokens().forEach(token -> {
            Map<String, Object> data = getNotificationBody(notificationRequest.getPlaceHolder(),
                    token.getNotificationToken(),
                    notificationTemplate);
            fcmService.sendFcmMessage(data);
        });
    }

    private Map<String, Object> getNotificationBody(HashMap<String, String> notificationRequest, String token,
                                                    NotificationTemplate notificationTemplate) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> notification = new HashMap<>();
        notification.put(Constants.TITLE, notificationTemplate.getBody());
        String body = StringSubstitutor.replace(notificationTemplate.getBody(),
                notificationRequest, "{",
                "}");
        notification.put(Constants.BODY, body);
        message.put(Constants.NOTIFICATION, notification);
        message.put(Constants.TOKEN, token);
        data.put(Constants.MESSAGE, message);
        return data;
    }

}
