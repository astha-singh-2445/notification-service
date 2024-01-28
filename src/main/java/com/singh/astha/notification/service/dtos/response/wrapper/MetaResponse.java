package com.singh.astha.notification.service.dtos.response.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.singh.astha.notification.service.enums.ResponseStatus;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MetaResponse(ResponseStatus status, Long timestamp) {

}
