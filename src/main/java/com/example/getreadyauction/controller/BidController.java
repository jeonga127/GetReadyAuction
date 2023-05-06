package com.example.getreadyauction.controller;

import com.example.getreadyauction.dto.BidRequestDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.security.UserDetailsImpl;
import com.example.getreadyauction.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    @PostMapping("bid/add/{id}")
    public ResponseDto postBid(@PathVariable Long id, @RequestBody BidRequestDto bidRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return bidService.postBid(id, bidRequestDto, userDetails.getUser());
    }
}
