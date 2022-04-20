package com.singh.astha.notificationservice.dtos.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationTemplateResponseDto {

    private String id;
    private String template;
}
