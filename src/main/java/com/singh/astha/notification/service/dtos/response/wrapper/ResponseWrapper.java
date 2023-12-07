package com.singh.astha.notification.service.dtos.response.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.singh.astha.notification.service.enums.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper<T> {

    private T data;

    private PaginationResponse pagination;

    private List<ErrorResponse> errors;

    private MetaResponse meta;

    private ResponseWrapper(T data, PaginationResponse pagination, List<ErrorResponse> errors, MetaResponse meta) {
        this.data = data;
        this.pagination = pagination;
        this.errors = errors;
        this.meta = meta;
    }

    public static <T> ResponseWrapper<T> success(T data) {
        return success(data, null);
    }

    public static <T> ResponseWrapper<T> success(T data, PaginationResponse pagination) {
        MetaResponse metaResponse = MetaResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .timestamp(Instant.now().toEpochMilli())
                .build();
        return new ResponseWrapper<>(data, pagination, null, metaResponse);
    }

    public static <T> ResponseWrapper<T> failure(List<ErrorResponse> errorResponses) {
        MetaResponse metaResponse = MetaResponse.builder()
                .status(ResponseStatus.FAILURE)
                .timestamp(Instant.now().toEpochMilli())
                .build();
        return new ResponseWrapper<>(null, null, errorResponses, metaResponse);
    }

}
