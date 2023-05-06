package com.example.getreadyauction.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String msg;
    private HttpStatus code;
}
