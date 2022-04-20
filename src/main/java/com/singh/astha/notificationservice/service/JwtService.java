package com.singh.astha.notificationservice.service;

import com.singh.astha.notificationservice.dtos.internal.JwtPayload;

public interface JwtService {

    JwtPayload verifyAndDecodeToken(String authHeader);
}
