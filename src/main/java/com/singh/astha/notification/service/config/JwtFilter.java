package com.singh.astha.notification.service.config;

import com.singh.astha.notification.service.dtos.internal.JwtPayload;
import com.singh.astha.notification.service.services.JwtService;
import com.singh.astha.notification.service.utils.Constants;
import com.singh.astha.notification.service.utils.MessageConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader(Constants.AUTHORIZATION);
            if (authorizationHeader != null) {

                JwtPayload jwtPayload = jwtService.verifyAndDecodeToken(authorizationHeader);
                User user = new User(
                        jwtPayload.getUserId().toString(), UUID.randomUUID().toString(),
                        jwtPayload.getRoles()
                                .stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList()
                );
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } catch (Exception exception) {
            // Ignore this exception
            logger.error(MessageConstants.UNABLE_TO_SET_USER_PASSWORD_AUTHENTICATION_TOKEN, exception);
        }
        filterChain.doFilter(request, response);
    }

}
