package com.singh.astha.notificationservice.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.singh.astha.notificationservice.constants.Constant;
import com.singh.astha.notificationservice.dtos.kafka.NotificationRequest;
import com.singh.astha.notificationservice.model.NotificationTemplate;
import com.singh.astha.notificationservice.model.UserToken;
import com.singh.astha.notificationservice.repositories.NotificationTemplateRepository;
import com.singh.astha.notificationservice.repositories.UserTokenRepository;
import com.singh.astha.notificationservice.service.FCMService;
import com.singh.astha.notificationservice.utils.Constants;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
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
        String accessToken = Constants.getAccessToken(googleCredentials);
        UserToken userToken = userTokenRepository.findByUserId(notificationRequest.getUserId());
        Optional<NotificationTemplate> notificationTemplateOptional =
                notificationTemplateRepository.findById(notificationRequest.getTemplateId());
        if(!notificationTemplateOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Template is not present");
        }
        NotificationTemplate notificationTemplate = notificationTemplateOptional.get();
        Map<String, Object> data = getNotificationBody(notificationRequest, userToken,notificationTemplate);
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

        return "Notification Successfully sent";
    }

    private Map<String, Object> getNotificationBody(NotificationRequest notificationRequest, UserToken userToken,
                                                    NotificationTemplate notificationTemplate) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> notification = new HashMap<>();
        notification.put(Constant.TITLE, "Your Medicine is ended");
        String body = StringSubstitutor.replace(notificationTemplate.getTemplate(), notificationRequest.getPlaceHolder(), "{",
                "}");
        notification.put(Constant.BODY, body);
        message.put(Constant.NOTIFICATION, notification);
        message.put(Constant.TOKEN, userToken.getUserToken());
        data.put(Constant.MESSAGE, message);
        return data;
    }
}
