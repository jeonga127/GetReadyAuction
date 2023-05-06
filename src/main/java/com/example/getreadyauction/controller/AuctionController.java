package com.example.getreadyauction.controller;

import com.example.getreadyauction.dto.AuctionCategoryDto;
import com.example.getreadyauction.dto.AuctionSearchDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/main") //실시간 전체 조회
    public List<ResponseDto> getNowAllAuction(){return auctionService.getNowAllAuction();}
}
