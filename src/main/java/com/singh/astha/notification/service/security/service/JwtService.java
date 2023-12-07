package com.singh.astha.notification.service.security.service;

import com.singh.astha.notification.service.security.dtos.JwtPayload;

public interface JwtService {

    JwtPayload verifyAndDecodeToken(String authHeader);
}
