package com.singh.astha.notificationservice.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.singh.astha.notificationservice.dtos.kafka.NotificationRequest;
import com.singh.astha.notificationservice.exceptions.ResponseException;
import com.singh.astha.notificationservice.model.NotificationTemplate;
import com.singh.astha.notificationservice.model.Token;
import com.singh.astha.notificationservice.model.UserToken;
import com.singh.astha.notificationservice.repositories.NotificationTemplateRepository;
import com.singh.astha.notificationservice.repositories.UserTokenRepository;
import com.singh.astha.notificationservice.service.FCMService;
import com.singh.astha.notificationservice.utils.Constant;
import com.singh.astha.notificationservice.utils.ErrorMessage;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class FCMServiceImpl implements FCMService {

    private final GoogleCredentials googleCredentials;
    private final WebClient webClient;
    private final UserTokenRepository userTokenRepository;
    private final NotificationTemplateRepository notificationTemplateRepository;

    public FCMServiceImpl(GoogleCredentials googleCredentials,
                          WebClient webClient,
                          UserTokenRepository userTokenRepository,
                          NotificationTemplateRepository notificationTemplateRepository) {
        this.googleCredentials = googleCredentials;
        this.webClient = webClient;
        this.userTokenRepository = userTokenRepository;
        this.notificationTemplateRepository = notificationTemplateRepository;
    }

    public String sendNotification(NotificationRequest notificationRequest) {
        String accessToken = Constant.getAccessToken(googleCredentials);
        Optional<UserToken> userTokenOptional = userTokenRepository.findByUserId(notificationRequest.getUserId());
        if (userTokenOptional.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessage.USER_TOKEN_NOT_EXIST);
        }
        UserToken userToken = userTokenOptional.get();
        Optional<NotificationTemplate> notificationTemplateOptional =
                notificationTemplateRepository.findById(notificationRequest.getTemplateId());
        if (notificationTemplateOptional.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessage.TEMPLATE_IS_NOT_PRESENT);
        }
        NotificationTemplate notificationTemplate = notificationTemplateOptional.get();
        userToken.getUserToken().forEach(token ->{
            Map<String, Object> data = getNotificationBody(notificationRequest, token, notificationTemplate);
            webClient.post()
                    .uri(Constant.GOOGLE_API, 1L)
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, Constant.BEARER + accessToken)
                    .body(BodyInserters.fromValue(data))
                    .exchangeToMono(res -> {
                        if (res.statusCode().isError()) {
                            return res.createException().flatMap(Mono::error);
                        }
                        return res.bodyToMono(String.class);
                    }).block();
        });


        return Constant.NOTIFICATION_SUCCESSFULLY_SENT;
    }

    private Map<String, Object> getNotificationBody(NotificationRequest notificationRequest, Token userToken,
                                                    NotificationTemplate notificationTemplate) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> notification = new HashMap<>();
        notification.put(Constant.TITLE, "Medicine Reminder");
        String body = StringSubstitutor.replace(notificationTemplate.getTemplate(),
                notificationRequest.getPlaceHolder(), "{",
                "}");
        notification.put(Constant.BODY, body);
        message.put(Constant.NOTIFICATION, notification);
        message.put(Constant.TOKEN, userToken.getUserToken());
        data.put(Constant.MESSAGE, message);
        return data;
    }
}
