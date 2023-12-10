package com.singh.astha.notification.service.utils;

import org.springframework.boot.web.servlet.filter.OrderedFilter;

public final class FilterOrderingConstants {

    public static final int CORS_FILTER_ORDER = OrderedFilter.HIGHEST_PRECEDENCE;

    public static final int REQUEST_ATTRIBUTION_FILTER_ORDER = OrderedFilter.HIGHEST_PRECEDENCE + 1;

    public static final int HTTP_LOGGING_FILTER_ORDER = OrderedFilter.HIGHEST_PRECEDENCE + 2;

    private FilterOrderingConstants() {
    }
}
