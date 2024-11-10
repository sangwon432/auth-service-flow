package com.sangwon.authserviceflow.mapper;

import com.sangwon.authserviceflow.dto.RegistrationRequestDto;
import com.sangwon.authserviceflow.dto.RegistrationResponseDto;
import com.sangwon.authserviceflow.entity.User;
import org.springframework.stereotype.Component;


@Component
public class UserSignupMapper {
    public User toEntity(final RegistrationRequestDto registrationRequestDto) {
        final var user = new User();

        user.setEmail(registrationRequestDto.email());
        user.setUsername(registrationRequestDto.username());
        user.setPassword(registrationRequestDto.password());
        return user;
    }

    public RegistrationResponseDto toResponseDto(final User user) {
        return new RegistrationResponseDto(user.getUsername(), user.getEmail());
    }
}
