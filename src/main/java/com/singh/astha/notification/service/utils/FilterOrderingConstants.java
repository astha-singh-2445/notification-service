package com.singh.astha.notification.service.utils;

import org.springframework.boot.web.servlet.filter.OrderedFilter;

public final class FilterOrderingConstants {

    public static final Integer CORS_FILTER_ORDER = OrderedFilter.HIGHEST_PRECEDENCE;

    public static final Integer REQUEST_ATTRIBUTION_FILTER_ORDER = OrderedFilter.HIGHEST_PRECEDENCE + 1;

    public static final Integer HTTP_LOGGING_FILTER_ORDER = OrderedFilter.HIGHEST_PRECEDENCE + 2;
}
