package com.sangwon.authserviceflow.controller;

import com.sangwon.authserviceflow.dto.RegistrationRequestDto;
import com.sangwon.authserviceflow.dto.RegistrationResponseDto;
import com.sangwon.authserviceflow.mapper.UserSignupMapper;
import com.sangwon.authserviceflow.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserSignupMapper userSignupMapper;

    @PostMapping("/signup")
    public ResponseEntity<RegistrationResponseDto> signupUser(
            @Valid @RequestBody final RegistrationRequestDto registrationRequestDto
    ) {

        final var registeredUser = userService.signupUser(userSignupMapper.toEntity(registrationRequestDto));
        //email 전송 로직 추가 (다음에)
        return ResponseEntity.ok(userSignupMapper.toResponseDto(registeredUser));


    }
}
