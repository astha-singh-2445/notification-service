package com.singh.astha.notification.service.service;

import java.util.Map;

public interface FCMService {

    void sendFcmMessage(Map<String, Object> data);
}
