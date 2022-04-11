package com.singh.astha.notificationservice.dtos.request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTokenRequestDto {
    @NotNull
    @Min(0)
    private Long userId;
    @NotBlank
    private String userToken;
}
