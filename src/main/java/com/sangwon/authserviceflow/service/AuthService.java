package com.sangwon.authserviceflow.service;

import com.sangwon.authserviceflow.entity.RefreshToken;
import com.sangwon.authserviceflow.entity.User;
import com.sangwon.authserviceflow.model.AuthTokens;
import com.sangwon.authserviceflow.repository.RefreshTokenRepository;
import com.sangwon.authserviceflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
//import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.time.Duration.between;

//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import static java.time.Duration.between;
//
//import java.time.Duration;
//import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${jwt.refresh-token-ttl}")
    private final Duration refreshTokenTTL;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthTokens authenticate(final String username, final String password) {
        final var authToken = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        final var authentication = authenticationManager.authenticate(authToken);

        final var user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with username [%s] not found".formatted(username)));

        return authenticate(user);
    }

    public AuthTokens authenticate(final User user) {
        final var accessToken = jwtService.generateToken(user.getUsername());

        final var refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setUser(user);
        refreshTokenEntity.setExpiresAt(Instant.now().plus(refreshTokenTTL));
        refreshTokenRepository.save(refreshTokenEntity);

        return new AuthTokens(accessToken, refreshTokenEntity.getId().toString(), between(Instant.now(), refreshTokenEntity.getExpiresAt()));
    }

    public AuthTokens refreshToken(final String refreshToken) {
        final var refreshTokenEntity = refreshTokenRepository.findByIdAndExpiresAtAfter(validateRefreshTokenFormat(refreshToken), Instant.now())
                .orElseThrow(() -> new BadCredentialsException("Invalid or expired refresh token"));

        final var newAccessToken = jwtService.generateToken(refreshTokenEntity.getUser().getUsername());
        return new AuthTokens(newAccessToken, refreshToken, between(Instant.now(), refreshTokenEntity.getExpiresAt()));

    }

    private UUID validateRefreshTokenFormat(final String refreshToken) {
        try {
            return UUID.fromString(refreshToken);

        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Invalid or expired refresh token");
        }
    }

    public void revokeRefreshToken(String refreshToken) {
        refreshTokenRepository.deleteById(validateRefreshTokenFormat(refreshToken));
    }
}
