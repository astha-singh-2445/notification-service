package com.singh.astha.notification.service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    private final AuthenticationExceptionHandler authenticationExceptionHandler;

    private final AccessDeniedExceptionHandler accessDeniedExceptionHandler;

    public SecurityConfig(
            JwtFilter jwtFilter,
            AuthenticationExceptionHandler authenticationExceptionHandler,
            AccessDeniedExceptionHandler accessDeniedExceptionHandler
    ) {
        this.jwtFilter = jwtFilter;
        this.authenticationExceptionHandler = authenticationExceptionHandler;
        this.accessDeniedExceptionHandler = accessDeniedExceptionHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/health", "/api-docs/**", "/swagger-ui/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        exception -> exception.authenticationEntryPoint(authenticationExceptionHandler)
                                .accessDeniedHandler(accessDeniedExceptionHandler)
                )
                .build();
    }
}
