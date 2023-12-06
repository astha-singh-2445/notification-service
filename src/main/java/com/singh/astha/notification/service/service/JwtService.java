package com.singh.astha.notification.service.service;

import com.singh.astha.notification.service.dtos.internal.JwtPayload;

public interface JwtService {

    JwtPayload verifyAndDecodeToken(String authHeader);
}
