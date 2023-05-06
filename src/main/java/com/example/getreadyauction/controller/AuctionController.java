package com.example.getreadyauction.controller;

import com.example.getreadyauction.dto.AuctionCategoryDto;
import com.example.getreadyauction.dto.AuctionSearchDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.service.AuctionService;
import lombok.RequiredArgsConstructor;
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
}
