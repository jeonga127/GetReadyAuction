package com.example.getreadyauction.domain.user.service;

import com.example.getreadyauction.domain.user.dto.LoginRequestDto;
import com.example.getreadyauction.domain.user.dto.LoginResponseDto;
import com.example.getreadyauction.domain.user.dto.SignupRequestDto;
import com.example.getreadyauction.exception.ErrorCode;
import com.example.getreadyauction.domain.user.entity.Users;
import com.example.getreadyauction.exception.CustomException;
import com.example.getreadyauction.security.jwt.JwtUtil;
import com.example.getreadyauction.domain.user.repository.UsersRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseEntity<String> signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();

        if(usersRepository.existsByUsername(username))
            return ResponseEntity.badRequest().body("중복된 사용자가 존재합니다.");

        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        Users user = new Users(username, password);
        usersRepository.save(user);

        return ResponseEntity.ok("회원가입 성공");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.NON_LOGIN));

        if (!passwordEncoder.matches(password, user.getPassword()))
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
        return ResponseEntity.ok(new LoginResponseDto(user.getUsername()));
    }
}