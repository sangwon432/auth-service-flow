package com.sangwon.authserviceflow.controller;

import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import com.sangwon.authserviceflow.dto.AuthenticationRequestDto;
import com.sangwon.authserviceflow.dto.AuthenticationResponseDto;
import com.sangwon.authserviceflow.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                .header(SET_COOKIE, null)
                .body(new AuthenticationResponseDto(authTokens.accessToken()));

    }
}
