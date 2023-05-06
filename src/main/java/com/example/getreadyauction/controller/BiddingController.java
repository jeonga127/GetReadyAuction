package com.example.getreadyauction.controller;

import com.example.getreadyauction.dto.BiddingRequestDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.security.UserDetailsImpl;
import com.example.getreadyauction.service.BiddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BiddingController {

    private final BiddingService biddingService;

    @PostMapping("bid/add/{id}")
    public ResponseDto postBidding(@PathVariable Long id, @RequestBody BiddingRequestDto biddingRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return biddingService.postBidding(id, biddingRequestDto, userDetails.getUser());
    }
}
