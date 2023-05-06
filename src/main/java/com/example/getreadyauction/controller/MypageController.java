package com.example.getreadyauction.controller;

import com.example.getreadyauction.dto.AuctionResponseDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.security.UserDetailsImpl;
import com.example.getreadyauction.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("mypage")
public class MypageController {

    private final MyPageService myPageService;

    @GetMapping("/auction") //내 경매 전체 조회
    public ResponseDto<List<AuctionResponseDto>> myAuctions(Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return myPageService.myAuctions(pageable, userDetails.getUser());
    }

    @GetMapping("/bidding") //내 입찰 전체 조회
    public ResponseDto<List<AuctionResponseDto>> myBid(Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return myPageService.myBid(pageable, userDetails.getUser());
    }

}
