package com.example.getreadyauction.domain.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    @NotEmpty(message = "username은 필수 값입니다.")
    private String username;

    @NotEmpty(message = "password는 필수 값입니다.")
    private String password;
}