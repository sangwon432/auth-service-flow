package com.sangwon.authserviceflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Duration;
import java.time.Instant;

@RequiredArgsConstructor
public class JwtService {
    public final String issuser;

    private final Duration ttl;

    private final JwtEncoder jwtEncoder;

    public String generateToken(final String email) {
        final var issuedAt = Instant.now();

        final var claimsSet = JwtClaimsSet.builder()
                .subject(email)
                .issuer(issuser)
                .issuedAt(issuedAt)
                .expiresAt(issuedAt.plus(ttl))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
