package com.singh.astha.notificationservice.service;

import java.util.Map;

public interface FCMService {

    void sendFcmMessage(Map<String, Object> data);
}
