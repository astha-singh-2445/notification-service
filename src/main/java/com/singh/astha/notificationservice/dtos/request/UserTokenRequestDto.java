package com.singh.astha.notificationservice.dtos.request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTokenRequestDto {

    @NotNull
    @Min(0)
    private Long userId;
    private String userToken;
}
