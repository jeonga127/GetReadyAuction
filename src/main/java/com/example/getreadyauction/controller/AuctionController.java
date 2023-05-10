package com.example.getreadyauction.controller;

import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.dto.auction.*;
import com.example.getreadyauction.security.UserDetailsImpl;
import com.example.getreadyauction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResponseDto<List<AuctionResponseDto>> getCategorizedAuctions(Pageable pageable, @RequestParam("category") String category){
        return auctionService.getCategorizedAuctions(pageable, category);
    }

    @GetMapping("/search")
    public ResponseDto<List<AuctionResponseDto>> getSearchedAuctions(Pageable pageable, @RequestParam("search") String search) {
        return auctionService.getSearchedAuction(pageable, search);
    }

    @GetMapping("/{id}")
    public ResponseDto<AuctionAllResponseDto> getDetailedAuctions(@PathVariable Long id){
        return auctionService.getDetailedAuctions(id);
    }

    @PostMapping("/add")
    public ResponseDto postAddAuction(@RequestBody AuctionRequestDto auctionAddRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return auctionService.postAddAuction(auctionAddRequestDto, userDetails.getUser());
    }

    @PutMapping("/edit/{editId}") //경매 수정
    public ResponseDto putEditAuction(@PathVariable("editId") Long editId, @RequestBody AuctionRequestDto auctionAddRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return auctionService.putEditAuction(editId, auctionAddRequestDto, userDetails.getUser());
    }

    @PutMapping("/up/{upId}") //경매 끌올
    public ResponseDto putUpAuction(@PathVariable("upId") Long upId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return auctionService.putUpAuction(upId, userDetails.getUser());
    }

    @DeleteMapping("/delete/{deleteId}")
    public ResponseDto delAuction(@PathVariable("deleteId") Long deleteId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return auctionService.delAuction(deleteId, userDetails.getUser());
    }
}
