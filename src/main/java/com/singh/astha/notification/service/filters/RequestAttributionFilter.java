package com.singh.astha.notification.service.filters;

import com.singh.astha.notification.service.utils.Constants;
import com.singh.astha.notification.service.utils.FilterOrderingConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(value = FilterOrderingConstants.REQUEST_ATTRIBUTION_FILTER_ORDER)
public class RequestAttributionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        request.setAttribute(Constants.REQUEST_ID, UUID.randomUUID().toString());
        request.setAttribute(Constants.REQUEST_ARRIVAL_TIME, System.currentTimeMillis());
        filterChain.doFilter(request, response);
    }
}
