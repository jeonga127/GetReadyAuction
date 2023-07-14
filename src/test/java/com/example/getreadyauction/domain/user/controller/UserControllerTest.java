package com.example.getreadyauction.domain.user.controller;

import com.example.getreadyauction.domain.scheduler.service.SchedulerService;
import com.example.getreadyauction.domain.user.dto.LoginRequestDto;
import com.example.getreadyauction.domain.user.dto.LoginResponseDto;
import com.example.getreadyauction.domain.user.dto.SignupRequestDto;
import com.example.getreadyauction.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@MockBean(SchedulerService.class)
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("회원가입 POST 요청")
    void signup() throws Exception{
        //given
        SignupRequestDto signupRequestDto = new SignupRequestDto("testname1", "@testPassword1");
        ResponseEntity<String> testResult = ResponseEntity.ok("회원가입 성공");
        when(userService.signup(Mockito.any(SignupRequestDto.class))).thenReturn(testResult);

        //when & then
        mockMvc.perform(post("/user/signup")
                        .content(objectMapper.writeValueAsString(signupRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("회원가입 성공"));
    }

    @Test
    @DisplayName("로그인 POST 요청")
    void login() throws Exception{
        //given
        LoginRequestDto loginRequestDto = new LoginRequestDto("testname1", "@testPassword1");
        ResponseEntity<Object> testResult =  ResponseEntity.ok(new LoginResponseDto("testname1"));
        when(userService.login(Mockito.any(LoginRequestDto.class), Mockito.any(HttpServletResponse.class))).thenReturn(testResult);

        //when & then
        mockMvc.perform(post("/user/login")
                        .content(objectMapper.writeValueAsString(loginRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testname1"));
    }
}