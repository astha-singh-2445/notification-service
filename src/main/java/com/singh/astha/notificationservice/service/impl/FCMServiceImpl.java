package com.singh.astha.notificationservice.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.singh.astha.notificationservice.service.FCMService;
import com.singh.astha.notificationservice.utils.Constants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class FCMServiceImpl implements FCMService {

    private final GoogleCredentials googleCredentials;
    private final WebClient webClient;


    public FCMServiceImpl(GoogleCredentials googleCredentials,
                          WebClient webClient) {
        this.googleCredentials = googleCredentials;
        this.webClient = webClient;
    }

    public void sendFcmMessage(Map<String, Object> data) {
        String accessToken = Constants.getAccessToken(googleCredentials);
        webClient.post()
                .uri(Constants.GOOGLE_API)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, Constants.BEARER + accessToken)
                .body(BodyInserters.fromValue(data))
                .exchangeToMono(res -> {
                    if (res.statusCode().isError()) {
                        return res.createException().flatMap(Mono::error);
                    }
                    return res.bodyToMono(String.class);
                }).block();
    }


}
