package com.singh.astha.notification.service.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationTemplateRequestDto {

    @NotBlank
    private String templateId;

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    private String imageLink;
}
