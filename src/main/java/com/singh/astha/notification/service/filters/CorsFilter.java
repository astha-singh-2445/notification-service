package com.singh.astha.notification.service.filters;

import com.singh.astha.notification.service.utils.FilterOrderingConstants;
import com.singh.astha.notification.service.utils.HeaderConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Locale;

@Component
@Order(FilterOrderingConstants.CORS_FILTER_ORDER)
public class CorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        response.setHeader(HeaderConstants.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.setHeader(HeaderConstants.ACCESS_CONTROL_ALLOW_HEADERS, "X-Requested-With, Authorization");
        response.setHeader(HeaderConstants.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader(HeaderConstants.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader(HeaderConstants.ORIGIN));
        response.setHeader(HeaderConstants.ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(HeaderConstants.CACHE_CONTROL, "no-store, max-age=0");
        response.setHeader(HeaderConstants.CONTENT_SECURITY_POLICY, "default-src 'none'");
        response.setHeader(HeaderConstants.CROSS_ORIGIN_EMBEDDER_POLICY, "require-corp");
        response.setHeader(HeaderConstants.CROSS_ORIGIN_OPENER_POLICY, "same-origin");
        response.setHeader(HeaderConstants.CROSS_ORIGIN_RESOURCE_POLICY, "same-origin");
        response.setHeader(HeaderConstants.PERMISSIONS_POLICY, HeaderConstants.PERMISSIONS_POLICY_VALUE);
        response.setHeader(HeaderConstants.PRAGMA, "no-cache");
        response.setHeader(HeaderConstants.REFERRER_POLICY, "no-referrer");
        response.setHeader(HeaderConstants.STRICT_TRANSPORT_SECURITY, "max-age=31536000 ; includeSubDomains");
        response.setHeader(HeaderConstants.X_CONTENT_TYPE_OPTIONS, "nosniff");
        response.setHeader(HeaderConstants.X_FRAME_OPTIONS, "DENY");
        response.setHeader(HeaderConstants.X_PERMITTED_CROSS_DOMAIN_POLICIES, "none");

        if (HttpMethod.OPTIONS.matches(request.getMethod().toUpperCase(Locale.getDefault()))) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
