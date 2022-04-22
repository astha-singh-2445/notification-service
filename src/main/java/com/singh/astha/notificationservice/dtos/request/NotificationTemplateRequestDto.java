package com.singh.astha.notificationservice.dtos.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

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
