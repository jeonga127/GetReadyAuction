package com.example.getreadyauction.exception;

import com.example.getreadyauction.domain.scheduler.service.SchedulerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@MockBean(SchedulerService.class)
class GlobalExceptionHandlerTest {

    @Test
    @DisplayName("회원가입/로그인 형식 예외 처리")
    void signValidException() {
        // given
        BindingResult bindingResult = Mockito.mock(BindingResult.class);

        FieldError fieldError = new FieldError("fieldName", "fieldValue", "errorMessage");
        Map<String, String> errors = new HashMap<>();
        errors.put(fieldError.getField(), fieldError.getDefaultMessage());

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException((MethodParameter) null, bindingResult);
        Mockito.when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        // when
        ResponseEntity<String> response = handler.signValidException(exception);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo(errors.toString());
    }

    @Test
    @DisplayName("Custom 예외 처리")
    void exceptionHandler() {
        // given
        CustomException exception = new CustomException(ErrorCode.METHOD_NOT_ALLOWED);
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        // when
        ResponseEntity<String> response = handler.exceptionHandler(exception);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo(exception.getMessage());
    }
}