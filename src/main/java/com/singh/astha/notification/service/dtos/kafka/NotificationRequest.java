package com.singh.astha.notification.service.dtos.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    private Long userId;

    private String templateId;

    private HashMap<String, String> titlePlaceholder;

    private HashMap<String, String> bodyPlaceHolders;

}
