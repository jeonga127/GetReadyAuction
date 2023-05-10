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
}//Spring Framework에 의해 자동으로 사용(?)//컨트롤러에서 발생하는 예외를 인터셉트하여 적절히 처리(?)

//예를 들어, 사용자가 입력한 데이터의 유효성 검사에 실패하면 MethodArgumentNotValidException이 발생함
//이 경우 signValidException 메서드가 이 예외를 인터셉트하고, 어떤 필드에서 문제가 발생했는지 사용자에게 알려주는 응답을 생성

//signValidException 메서드는 MethodArgumentNotValidException 예외를 처리함. 이 예외는 Spring에서 제공하는 데이터 바인딩 및 유효성 검사 기능을 사용할 때, 바인딩 오류나 유효성 검사 실패 시 발생함.
//이 메서드는 발생한 예외 정보를 받아서, 어떤 필드에서 유효성 검사 오류가 발생했는지와 그 이유를 클라이언트에게 반환

//exceptionHandler 메서드는 CustomException 예외를 처리함. 이 예외는 사용자가 직접 정의한 예외로, 예외가 발생하면 이 메서드가 호출되어 처리함.
//메서드는 발생한 예외의 정보를 받아서, 그 예외의 에러 코드에 맞는 응답을 클라이언트에게 반환




