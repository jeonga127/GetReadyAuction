package com.example.getreadyauction.service;

import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.dto.user.LoginRequestDto;
import com.example.getreadyauction.dto.user.LoginResponseDto;
import com.example.getreadyauction.dto.user.SignupRequestDto;
import com.example.getreadyauction.entity.ErrorCode;
import com.example.getreadyauction.entity.Users;
import com.example.getreadyauction.exception.CustomException;
import com.example.getreadyauction.jwt.JwtUtil;
import com.example.getreadyauction.repository.UsersRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 회원 중복 확인
        Optional<Users> found = usersRepository.findByUsername(username);
        if (found.isPresent()) {
            return ResponseDto.setBadRequest("중복된 사용자가 존재합니다.");
        }

        Users user = new Users(username, password);
        usersRepository.save(user);

        return ResponseDto.setSuccess("회원가입 성공");
    }

    @Transactional(readOnly = true)
    public ResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        Users user = usersRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NON_LOGIN)
        );
        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseDto.setBadRequest("비밀번호가 일치하지 않습니다");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
        return ResponseDto.setSuccess("로그인 성공", new LoginResponseDto(user.getUsername()));
    }
}