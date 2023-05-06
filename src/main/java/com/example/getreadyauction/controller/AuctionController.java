package com.example.getreadyauction.controller;

import com.example.getreadyauction.dto.AuctionRequestDto;
import com.example.getreadyauction.dto.AuctionCategoryDto;
import com.example.getreadyauction.dto.AuctionSearchDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.security.UserDetailsImpl;
import com.example.getreadyauction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("auction")
public class AuctionController {

    private final AuctionService auctionService;

    @GetMapping
    public ResponseDto getAllAuctions(){
        return auctionService.getAllAuctions();
    }

    @GetMapping("/category")
    public ResponseDto getCategorizedAuctions(@RequestBody AuctionCategoryDto auctionCategoryDto){
        return auctionService.getCategorizedAuctions(auctionCategoryDto);
    }

    @GetMapping("/{id}")
    public ResponseDto getDetailedAuctions(@PathVariable Long id){
        return auctionService.getDetailedAuctions(id);
    }

    @GetMapping("/search")
    public ResponseDto getSearchedAuctions(@RequestBody AuctionSearchDto auctionSearchDto){
        return auctionService.getSearchedAuction(auctionSearchDto);
    }

    @PostMapping("/add")
    public ResponseDto postAddAuction(@RequestBody AuctionRequestDto auctionAddRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return auctionService.postAddAuction(auctionAddRequestDto, userDetails.getUser());
    }

    @PutMapping("/edit/{id}")
    public ResponseDto putEditAuction(@PathVariable Long id,@RequestBody AuctionRequestDto auctionAddRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return auctionService.putEditAuction(id, auctionAddRequestDto, userDetails.getUser());
    }

    @PutMapping("/up/{id}")
    public ResponseDto putUpAuction(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return auctionService.putUpAuction(id, userDetails.getUser());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto delAuction(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return auctionService.delAuction(id, userDetails.getUser());
    }
}
