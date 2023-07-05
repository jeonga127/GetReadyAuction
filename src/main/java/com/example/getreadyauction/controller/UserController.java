package com.example.getreadyauction.controller;

import com.example.getreadyauction.dto.user.LoginRequestDto;
import com.example.getreadyauction.dto.user.SignupRequestDto;
import com.example.getreadyauction.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequestDto loginRequestDto, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            StringBuilder st = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                st.append(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(st.toString());
        }
        return userService.login(loginRequestDto, response);
    }
}