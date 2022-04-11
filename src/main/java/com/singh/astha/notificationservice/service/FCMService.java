package com.singh.astha.notificationservice.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.singh.astha.notificationservice.utils.Constants;

import org.json.JSONObject;
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

    public FCMService(GoogleCredentials googleCredentials,
                      WebClient webClient) {
        this.googleCredentials = googleCredentials;
        this.webClient = webClient;
    }

    public String sendNotification(String value) throws IOException {
        String accessToken = Constants.getAccessToken(googleCredentials);

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> notification = new HashMap<>();
        notification.put("title","FCM Message springboot");
        notification.put("body","This is an astha");
        message.put("notification",notification);
        message.put("token","elxqIYmhTju1WWpYPtP_-3:APA91bGEtqU-EQHExSjztJpqka9KX_6BDYj0-XaqpKnlsHli1NE" +
                "-I0oLRo_PcQeJrd_9IvQnBpwE2D7KJyDa5xzZ0yooE7ebei43nkvTJ4c-uCOyBSgUrJvehOu-fccbkAolPTUzFO0k");
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
