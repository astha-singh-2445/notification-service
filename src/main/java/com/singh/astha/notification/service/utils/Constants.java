package com.singh.astha.notification.service.utils;

import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;

public final class Constants {

    public static final String ERROR_CODE_PREFIX = "NS-E-";

    public static final String NOTIFICATION_INGESTION = "notification_ingestion";

    public static final String GOOGLE_API = "https://fcm.googleapis.com/v1/projects/medicine-b627f/messages:send";

    public static final String BEARER = "Bearer ";

    public static final String FIREBASE_PRIVATE_KEY_JSON = "firebase.json";

    public static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";

    public static final String FIXED = "fixed";

    public static final String TITLE = "title";

    public static final String BODY = "body";

    public static final String NOTIFICATION = "notification";

    public static final String TOKEN = "token";

    public static final String MESSAGE = "message";

    public static final String OK = "OK";

    public static final String TOKEN_ALREADY_EXISTS = "Token already exists";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String APPLICATION_JSON = "application/json";

    public static final String KEY_VALUE_ERROR_FORMAT = "%s: %s";

    public static final String REQUEST_ID = "requestId";

    public static final String REQUEST_ARRIVAL_TIME = "requestArrivalTime";

    private Constants() {
    }

    public static String getAccessToken(GoogleCredentials googleCredentials) {
        try {
            googleCredentials.refreshIfExpired();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googleCredentials.getAccessToken().getTokenValue();
    }

}
