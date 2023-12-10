package com.singh.astha.notification.service.filters;

import com.singh.astha.notification.service.utils.Constants;
import com.singh.astha.notification.service.utils.FilterOrderingConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(FilterOrderingConstants.HTTP_LOGGING_FILTER_ORDER)
public class HttpLoggingFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(HttpLoggingFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            MDC.put(Constants.REQUEST_ID, (String) request.getAttribute(Constants.REQUEST_ID));
            filterChain.doFilter(request, response);
        } finally {
            log.info("Request to {}: {} - {}", request.getMethod(), formURI(request), response.getStatus());
            MDC.remove(Constants.REQUEST_ID);
        }
    }

    private String formURI(HttpServletRequest request) {
        String queryString = request.getQueryString();
        return request.getRequestURI() + (queryString == null ? "" : "?" + queryString);
    }
}
