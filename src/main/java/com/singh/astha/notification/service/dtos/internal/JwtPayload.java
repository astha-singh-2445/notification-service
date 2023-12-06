package com.singh.astha.notification.service.dtos.internal;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtPayload {

    private Long userId;

    private List<String> roles;
}
