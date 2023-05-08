package com.example.getreadyauction.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {

    @NotEmpty(message = "username은 필수 값입니다.")
    private String username;

    @NotEmpty(message = "password는 필수 값입니다.")
    private String password;
}//로그인할때 필요(?)