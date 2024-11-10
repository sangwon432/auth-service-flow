package com.sangwon.authserviceflow.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangwon.authserviceflow.exception.ErrorType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import static com.sangwon.authserviceflow.exception.ErrorType.UNAUTHORIZED;
import static com.sangwon.authserviceflow.exception.ProblemDetailBuilder.forStatus;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class BearerTokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        final var status = HttpStatus.FORBIDDEN;

        log.info("{} : {}", status.getReasonPhrase(), authException.getMessage());

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // instead of null, have to customize the second arg later
        objectMapper.writeValue(response.getWriter(), forStatus(status).withErrorType(UNAUTHORIZED).build());

    }
}
