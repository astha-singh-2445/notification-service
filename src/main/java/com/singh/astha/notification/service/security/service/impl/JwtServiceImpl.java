
package com.singh.astha.notification.service.security.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.singh.astha.notification.service.exceptions.ResponseException;
import com.singh.astha.notification.service.security.utils.Constants;
import com.singh.astha.notification.service.security.utils.ErrorMessages;
import com.singh.astha.notification.service.security.dtos.JwtPayload;
import com.singh.astha.notification.service.security.service.JwtService;
import com.singh.astha.notification.service.security.utils.AppProperties;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class JwtServiceImpl implements JwtService {

    private final JWTVerifier jwtVerifier;

    private final AppProperties appProperties;

    public JwtServiceImpl(AppProperties appProperties) {
        this.appProperties = appProperties;
        Algorithm rsaAlgorithm = getRSAAlgorithm();
        jwtVerifier = JWT.require(rsaAlgorithm).build();
    }

    @SneakyThrows
    private Algorithm getRSAAlgorithm() {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] publicKeyBytes = decoder.decode(appProperties.getPublicKey());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        return Algorithm.RSA512(rsaPublicKey, null);

    }

    @Override
    public JwtPayload verifyAndDecodeToken(String authHeader) {
        if (authHeader.startsWith(Constants.BEARER)) {
            try {
                String jwt = authHeader.substring(7);
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                return JwtPayload.builder()
                        .userId(decodedJWT.getClaim(Constants.USER_ID).asLong())
                        .roles(decodedJWT.getClaim(Constants.ROLES).asList(String.class))
                        .build();
            } catch (Exception e) {
                throw new ResponseException(HttpStatus.UNAUTHORIZED, ErrorMessages.INVALID_TOKEN);
            }
        }
        throw new ResponseException(
                HttpStatus.BAD_REQUEST,
                ErrorMessages.AUTHORIZATION_HEADER_MUST_NOT_BE_NULL_AND_MUST_START_BE_BEARER
        );
    }
}
