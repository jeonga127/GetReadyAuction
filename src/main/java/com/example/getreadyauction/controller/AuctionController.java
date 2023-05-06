package com.example.getreadyauction.controller;

import com.example.getreadyauction.dto.*;
import com.example.getreadyauction.dto.auction.AuctionAllResponseDto;
import com.example.getreadyauction.dto.auction.AuctionCategoryDto;
import com.example.getreadyauction.dto.auction.AuctionResponseDto;
import com.example.getreadyauction.dto.auction.AuctionSearchDto;
import com.example.getreadyauction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("auction")
public class AuctionController {

    private final AuctionService auctionService;

    @GetMapping
    public ResponseDto<List<AuctionResponseDto>> getAllAuctions(Pageable pageable){
        return auctionService.getAllAuctions(pageable);
    }

    @GetMapping("/category")
    public ResponseDto<List<AuctionResponseDto>> getCategorizedAuctions(Pageable pageable, @RequestBody AuctionCategoryDto auctionCategoryDto){
        return auctionService.getCategorizedAuctions(pageable, auctionCategoryDto);
    }
    @GetMapping("/search")
    public ResponseDto<List<AuctionResponseDto>> getSearchedAuctions(Pageable pageable, @RequestBody AuctionSearchDto auctionSearchDto){
        return auctionService.getSearchedAuction(pageable, auctionSearchDto);
    }

    @GetMapping("/{id}")
    public ResponseDto<AuctionAllResponseDto> getDetailedAuctions(@PathVariable Long id){
        return auctionService.getDetailedAuctions(id);
    }

}
