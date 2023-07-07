package com.example.getreadyauction.controller;

import com.example.getreadyauction.dto.auction.AuctionResponseDto;
import com.example.getreadyauction.security.UserDetailsImpl;
import com.example.getreadyauction.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/auction")
    public ResponseEntity<List<AuctionResponseDto>> getAllMyAuctions(Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return myPageService.getAllMyAuctions(pageable, userDetails.getUser());
    }

    @GetMapping("/bidding")
    public ResponseEntity<List<AuctionResponseDto>> getAllMyBids(Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return myPageService.getAllMyBids(pageable, userDetails.getUser());
    }
}
