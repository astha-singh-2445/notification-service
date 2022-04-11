package com.singh.astha.notificationservice.utils;

import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;

public class Constants {

    public static final String OK = "OK";

    public static String getAccessToken(GoogleCredentials googleCredentials) throws IOException {
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

}
