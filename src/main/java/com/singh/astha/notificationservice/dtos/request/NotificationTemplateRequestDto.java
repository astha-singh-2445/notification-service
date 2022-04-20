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
    private String template;
}
