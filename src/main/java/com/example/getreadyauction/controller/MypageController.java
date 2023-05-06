package com.example.getreadyauction.controller;

import com.example.getreadyauction.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("mypage")
public class MypageController {

    private final MypageController mypageController;

    @GetMapping("/auction")
    public ResponseDto


}
