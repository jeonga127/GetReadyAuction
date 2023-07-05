package com.example.getreadyauction.controller;

import com.example.getreadyauction.dto.BidRequestDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.security.UserDetailsImpl;
import com.example.getreadyauction.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    @PostMapping("bid/add/{bidId}")
    public ResponseEntity<String> postBid(@PathVariable("bidId") Long bidId, @RequestBody BidRequestDto bidRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return bidService.postBid(bidId, bidRequestDto, userDetails.getUser());
    }
}
