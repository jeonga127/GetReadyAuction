package com.example.getreadyauction.security;

import com.example.getreadyauction.exception.ErrorCode;
import com.example.getreadyauction.domain.user.entity.Users;
import com.example.getreadyauction.exception.CustomException;
import com.example.getreadyauction.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws CustomException {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.NON_LOGIN));

        return new UserDetailsImpl(user, user.getUsername());
    }
}