package com.example.getreadyauction.controller;

import com.example.getreadyauction.dto.LoginRequestDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.dto.ResponseLoginDto;
import com.example.getreadyauction.dto.SignupRequestDto;
import com.example.getreadyauction.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor//클래스에 선언되어 있는 final 또는 @NonNull 어노테이션이 붙은 필드에 대한 생성자를 자동으로 생성(?)
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")//회원가입 페이지 보여주기(?)
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }

    @GetMapping("/login")//로그인 페이지 보여주기(?)
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }



    @PostMapping("/signup")//회원가입
    public ResponseLoginDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder st = new StringBuilder();
            for(FieldError fieldError: bindingResult.getFieldErrors()) {
                st.append(fieldError.getDefaultMessage());
            }
            return new ResponseLoginDto(st.toString(), HttpStatus.BAD_REQUEST);
        }
        return userService.signup(signupRequestDto);
    }



    @ResponseBody
    @PostMapping("/login")//로그인
    public ResponseLoginDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);

    }

}