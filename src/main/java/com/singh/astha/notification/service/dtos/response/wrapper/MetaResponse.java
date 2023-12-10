package com.singh.astha.notification.service.dtos.response.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.singh.astha.notification.service.enums.ResponseStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetaResponse {
    private ResponseStatus status;

    private Long timestamp;
}
