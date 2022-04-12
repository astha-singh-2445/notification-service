package com.singh.astha.notificationservice.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.singh.astha.notificationservice.dtos.kafka.NotificationRequest;
import com.singh.astha.notificationservice.model.UserToken;
import com.singh.astha.notificationservice.repositories.UserTokenRepository;
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
public class FCMService {

    private final GoogleCredentials googleCredentials;
    private final WebClient webClient;
    private final UserTokenRepository userTokenRepository;

    public FCMService(GoogleCredentials googleCredentials,
                      WebClient webClient,
                      UserTokenRepository userTokenRepository) {
        this.googleCredentials = googleCredentials;
        this.webClient = webClient;
        this.userTokenRepository = userTokenRepository;
    }

    public String sendNotification(NotificationRequest notificationRequest) throws IOException {
        String accessToken = Constants.getAccessToken(googleCredentials);
        UserToken userToken = userTokenRepository.findByUserId(notificationRequest.getUserId());

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> notification = new HashMap<>();
        notification.put("title", "Your Medicine is ended");
        notification.put("body", notificationRequest.getMedicineName()+"is ended");
        message.put("notification", notification);
        message.put("token", userToken.getUserToken());
        data.put("message", message);

        webClient.post()
                .uri("https://fcm.googleapis.com/v1/projects/medicine-b627f/messages:send", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .body(BodyInserters.fromValue(data))
                .retrieve()
                .bodyToMono(String.class).block();

        return "Notification Successfully sent";
    }
}
