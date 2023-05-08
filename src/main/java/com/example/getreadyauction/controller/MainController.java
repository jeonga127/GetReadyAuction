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

    @GetMapping("/main")  //하나로 합친 것
    public ResponseDto main(String orderBy, Pageable pageable){
        return mainService.main(orderBy, pageable);
    }

}
