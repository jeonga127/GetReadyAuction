package com.example.getreadyauction.exception;

import com.example.getreadyauction.dto.ErrorResponseDto;
import com.example.getreadyauction.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<ResponseDto>  signValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ResponseDto response = ResponseDto.setBadRequest("Valid Error : ", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseDto> exceptionHandler(final CustomException exception) {
        ErrorResponseDto response = new ErrorResponseDto(exception.getErrorCode());
        return new ResponseEntity<>(response, exception.getErrorCode().getStatus());
    }
}