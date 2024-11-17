package com.sangwon.authserviceflow.controller;

import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import com.sangwon.authserviceflow.dto.AuthenticationRequestDto;
import com.sangwon.authserviceflow.dto.AuthenticationResponseDto;
import com.sangwon.authserviceflow.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sangwon.authserviceflow.model.AuthTokens.REFRESH_TOKEN_COOKIE_NAME;
import static com.sangwon.authserviceflow.util.CookieUtil.addCookie;
import static com.sangwon.authserviceflow.util.CookieUtil.removeCookie;
import static org.springframework.http.HttpHeaders.SET_COOKIE;
//import static com.sangwon.authserviceflow.util.CookieUtil.addCookie;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @Valid @RequestBody final AuthenticationRequestDto authenticationRequestDto
    ) {
        final var authTokens = authService.authenticate(authenticationRequestDto.username(), authenticationRequestDto.password());
        return ResponseEntity.ok()
                .header(SET_COOKIE, addCookie(REFRESH_TOKEN_COOKIE_NAME, authTokens.refreshToken(), authTokens.refreshTokenTTL()).toString())
                .body(new AuthenticationResponseDto(authTokens.accessToken()));

    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponseDto> refreshToken(@CookieValue(REFRESH_TOKEN_COOKIE_NAME) final String refreshToken) {
        final var authTokens = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(new AuthenticationResponseDto(authTokens.accessToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> revokeToken(@CookieValue(REFRESH_TOKEN_COOKIE_NAME) final String refreshToken) {
        authService.revokeRefreshToken(refreshToken);

        return ResponseEntity.noContent()
                .header(SET_COOKIE, removeCookie(REFRESH_TOKEN_COOKIE_NAME).toString())
                .build();
    }
}
