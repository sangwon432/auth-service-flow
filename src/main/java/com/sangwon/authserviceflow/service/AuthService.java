package com.sangwon.authserviceflow.service;

import com.sangwon.authserviceflow.entity.User;
import com.sangwon.authserviceflow.model.AuthTokens;
import com.sangwon.authserviceflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthTokens authenticate(final String username, final String password) {
        final var authToken = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        System.out.println("HERE");
        System.out.println(authToken);
        final var authentication = authenticationManager.authenticate(authToken);

        final var user = userRepository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with username [%s] not found".formatted(username)));

        return authenticate(user);
    }

    public AuthTokens authenticate(final User user) {
        final var accessToken = jwtService.generateToken(user.getUsername());
//
//        final var refreshTokenEntity = new RefreshToken();
//        refreshTokenEntity.setUser(user);
//        refreshTokenEntity.setExpiresAt(Instant.now().plus(refreshTokenTtl));
//        refreshTokenRepository.save(refreshTokenEntity);

//        return new AuthTokens(accessToken, refreshTokenEntity.getId().toString(), between(Instant.now(), refreshTokenEntity.getExpiresAt()));
        return new AuthTokens(accessToken);
    }
}
