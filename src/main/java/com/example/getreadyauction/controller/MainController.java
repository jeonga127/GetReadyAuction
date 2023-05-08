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

//    @GetMapping("/main")  //하나로 합친 것
//    public ResponseDto mainVew(Pageable pageable){
//        return mainService.mainView(pageable);
//    }

    @GetMapping("/main/v")  //조회수 기준 실시간 경매리스트
    public ResponseDto mainVew(Pageable pageable){
        return mainService.mainView(pageable);
    }

    @GetMapping("/main/d") //마감임박 기준 실시간 경매리스트
    public ResponseDto mainDeadline(Pageable pageable){
        return mainService.mainDeadline(pageable);
    }

    @GetMapping("/main/c") //마감임박 기준 실시간 경매리스트
    public ResponseDto mainCount(Pageable pageable){
        return mainService.mainCount(pageable);
    }
}
