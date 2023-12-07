package com.singh.astha.notification.service.services;

import java.util.Map;

public interface FCMService {

    void sendFcmMessage(Map<String, Object> data);
}
