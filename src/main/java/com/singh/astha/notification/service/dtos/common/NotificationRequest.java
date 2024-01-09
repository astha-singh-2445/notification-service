package com.singh.astha.notification.service.dtos.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Long userId;

    @NotBlank
    private String templateId;

    @NotNull
    private HashMap<String, String> titlePlaceholder;

    @NotNull
    private HashMap<String, String> bodyPlaceHolders;

}
