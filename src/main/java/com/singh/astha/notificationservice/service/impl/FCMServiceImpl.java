package com.singh.astha.notificationservice.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.singh.astha.notificationservice.constants.Constant;
import com.singh.astha.notificationservice.dtos.kafka.NotificationRequest;
import com.singh.astha.notificationservice.model.UserToken;
import com.singh.astha.notificationservice.repositories.UserTokenRepository;
import com.singh.astha.notificationservice.service.FCMService;
import com.singh.astha.notificationservice.utils.Constants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class FCMServiceImpl implements FCMService {

    private final GoogleCredentials googleCredentials;
    private final WebClient webClient;
    private final UserTokenRepository userTokenRepository;

    public FCMServiceImpl(GoogleCredentials googleCredentials,
                          WebClient webClient,
                          UserTokenRepository userTokenRepository) {
        this.googleCredentials = googleCredentials;
        this.webClient = webClient;
        this.userTokenRepository = userTokenRepository;
    }

    public String sendNotification(NotificationRequest notificationRequest) throws IOException {
        String accessToken = Constants.getAccessToken(googleCredentials);
        UserToken userToken = userTokenRepository.findByUserId(notificationRequest.getUserId());
        Map<String, Object> data = getnotificationBody(notificationRequest, userToken);

        webClient.post()
                .uri(Constant.GOOGLE_API, 1L)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, Constant.BEARER + accessToken)
                .body(BodyInserters.fromValue(data))
                .retrieve()
                .bodyToMono(String.class).block();

        return "Notification Successfully sent";
    }

    private Map<String, Object> getnotificationBody(NotificationRequest notificationRequest, UserToken userToken) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> notification = new HashMap<>();
        notification.put(Constant.TITLE, "Your Medicine is ended");
        notification.put(Constant.BODY,
                "You are running out of " + notificationRequest.getNotificationData() + ".Quickly grab " +
                        "from " +
                        "the nearest pharmacy.");
        message.put(Constant.NOTIFICATION, notification);
        message.put(Constant.TOKEN, userToken.getUserToken());
        data.put(Constant.MESSAGE, message);
        return data;
    }
}
