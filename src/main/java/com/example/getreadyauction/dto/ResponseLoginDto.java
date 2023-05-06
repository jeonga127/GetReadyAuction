package com.example.getreadyauction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ResponseLoginDto {
    private String msg;
    private HttpStatus code;
}
