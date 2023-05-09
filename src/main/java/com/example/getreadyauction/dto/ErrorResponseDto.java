package com.example.getreadyauction.dto;

import com.example.getreadyauction.entity.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ErrorResponseDto {
    private int status;
    private String error;
    private String msg;

    public ErrorResponseDto(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.error = errorCode.getStatus().name();
        this.msg = errorCode.getMessage();
    }
}
