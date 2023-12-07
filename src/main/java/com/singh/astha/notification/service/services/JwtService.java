package com.singh.astha.notification.service.services;

import com.singh.astha.notification.service.dtos.internal.JwtPayload;

public interface JwtService {

    JwtPayload verifyAndDecodeToken(String authHeader);
}
