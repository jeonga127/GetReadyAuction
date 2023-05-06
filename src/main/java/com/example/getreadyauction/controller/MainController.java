package com.example.getreadyauction.controller;


import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class MainController {

    private final MainService mainService;

    @GetMapping("/main/v")
    public ResponseDto mainVew(Pageable pageable){
        return mainService.mainView(pageable);
    }

    @GetMapping("/main/d")
    public ResponseDto mainDeadline(Pageable pageable){
        return mainService.mainDeadline(pageable);
    }

    @GetMapping("/main/c")
    public ResponseDto mainCount(Pageable pageable){
        return mainService.mainCount(pageable);
    }
}
