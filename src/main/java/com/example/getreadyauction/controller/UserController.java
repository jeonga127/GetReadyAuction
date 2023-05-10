package com.example.getreadyauction.controller;

import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.dto.user.LoginRequestDto;
import com.example.getreadyauction.dto.user.SignupRequestDto;
import com.example.getreadyauction.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")//회원가입
    public ResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder st = new StringBuilder();
            for(FieldError fieldError: bindingResult.getFieldErrors()) {
                st.append(fieldError.getDefaultMessage());
            }
            return ResponseDto.setBadRequest(st.toString());
        }
        return userService.signup(signupRequestDto);
    }

    @PostMapping("/login")//로그인
    public ResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            StringBuilder st = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                st.append(fieldError.getDefaultMessage());
            }
            return ResponseDto.setBadRequest(st.toString());
        }
        return userService.login(loginRequestDto, response);
    }
}