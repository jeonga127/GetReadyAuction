package com.example.getreadyauction.service;

import com.example.getreadyauction.domain.scheduler.service.SchedulerService;
import com.example.getreadyauction.domain.user.dto.LoginRequestDto;
import com.example.getreadyauction.domain.user.dto.LoginResponseDto;
import com.example.getreadyauction.domain.user.dto.SignupRequestDto;
import com.example.getreadyauction.domain.user.service.UserService;
import com.example.getreadyauction.exception.ErrorCode;
import com.example.getreadyauction.domain.user.entity.Users;
import com.example.getreadyauction.exception.CustomException;
import com.example.getreadyauction.security.jwt.JwtUtil;
import com.example.getreadyauction.domain.user.repository.UsersRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@MockBean(SchedulerService.class)
@ExtendWith({MockitoExtension.class})
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @AfterEach
    void tearDown() {
        usersRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입")
    void signup() {
        //given
        SignupRequestDto testSignupRequestDto = new SignupRequestDto("testname1", "@testPassword1");

        //when
        when(passwordEncoder.encode("@testPassword1")).thenReturn("encodedPassword");
        when(usersRepository.existsByUsername("testname1")).thenReturn(false);
        ResponseEntity<String> testResult = userService.signup(testSignupRequestDto);

        //then
        assertThat(testResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(testResult.getBody()).isEqualTo("회원가입 성공");
    }

    @Test
    @DisplayName("회원가입 : 중복된 사용자가 존재")
    void signupWithExistedUser() {
        //given
        SignupRequestDto testSignupRequestDto = new SignupRequestDto("testname1", "@testPassword1");

        //when
        when(usersRepository.existsByUsername("testname1")).thenReturn(true);
        ResponseEntity<String> testResult = userService.signup(testSignupRequestDto);

        //then
        assertThat(testResult.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(testResult.getBody()).isEqualTo("중복된 사용자가 존재합니다.");
    }

    @Test
    @DisplayName("로그인")
    void login() {
        //given
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        LoginRequestDto testLoginRequestDto = new LoginRequestDto("testname1", "@testPassword1");
        Users testUser = new Users("testname1", "@testPassword1");

        when(usersRepository.findByUsername("testname1")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("@testPassword1", "@testPassword1")).thenReturn(true);
        when(jwtUtil.createToken("testname1")).thenReturn("testAuthorization");

        //when
        ResponseEntity<Object> testResult = userService.login(testLoginRequestDto, response);

        //then
        assertThat(testResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(((LoginResponseDto)testResult.getBody()))
                .usingRecursiveComparison()
                .isEqualTo(new LoginResponseDto(testUser.getUsername()));

        Mockito.verify(response).addHeader(JwtUtil.AUTHORIZATION_HEADER, "testAuthorization");
    }

    @Test
    @DisplayName("로그인 : 존재하지 않는 회원")
    void loginWithNotExistedUsers() {
        //given
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        LoginRequestDto testLoginRequestDto = new LoginRequestDto("testname1", "@WrongPassword1");
        when(usersRepository.findByUsername("testname1")).thenThrow(new CustomException(ErrorCode.NON_LOGIN));

        //when & then
        CustomException testResult = assertThrows(CustomException.class,
                () -> userService.login(testLoginRequestDto, response));
        assertThat(testResult.getErrorCode()).isEqualTo(ErrorCode.NON_LOGIN);
    }

    @Test
    @DisplayName("로그인 : 비밀번호가 일치하지 않을 때")
    void loginWithWrongPassword() {
        //given
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        LoginRequestDto testLoginRequestDto = new LoginRequestDto("testname1", "@WrongPassword1");
        Users testUser = new Users("testname1", "@testPassword1");

        when(usersRepository.findByUsername("testname1")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("@WrongPassword1", "@testPassword1")).thenReturn(false);

        //when
        ResponseEntity<Object> testResult = userService.login(testLoginRequestDto, response);

        //then
        assertThat(testResult.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(testResult.getBody()).isEqualTo("비밀번호가 일치하지 않습니다.");
    }
}