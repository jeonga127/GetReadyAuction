package com.example.getreadyauction.controller;

import com.example.getreadyauction.dto.auction.AuctionAllResponseDto;
import com.example.getreadyauction.dto.auction.AuctionRequestDto;
import com.example.getreadyauction.dto.auction.AuctionResponseDto;
import com.example.getreadyauction.entity.CategoryType;
import com.example.getreadyauction.security.UserDetailsImpl;
import com.example.getreadyauction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("auction")
public class AuctionController {

    private final AuctionService auctionService;

    @GetMapping
    public ResponseEntity<List<AuctionResponseDto>> getAllAuctions(Pageable pageable){
        return auctionService.getAllAuctions(pageable);
    }

    @GetMapping("/category")
    public ResponseEntity<List<AuctionResponseDto>> getCategorizedAuctions(Pageable pageable, @RequestParam("category") CategoryType category){
        return auctionService.getCategorizedAuctions(pageable, category);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AuctionResponseDto>> getSearchedAuctions(Pageable pageable, @RequestParam("search") String search) {
        return auctionService.getSearchedAuction(pageable, search);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuctionAllResponseDto> getDetailedAuctions(@PathVariable Long id){
        return auctionService.getDetailedAuctions(id);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addAuction(@RequestBody AuctionRequestDto auctionAddRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return auctionService.addAuction(auctionAddRequestDto, userDetails.getUser());
    }

    @PutMapping("/edit/{editId}")
    public ResponseEntity<String> editAuction(@PathVariable("editId") Long editId, @RequestBody AuctionRequestDto auctionAddRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return auctionService.editAuction(editId, auctionAddRequestDto, userDetails.getUser().getUsername());
    }

    @DeleteMapping("/delete/{deleteId}")
    public ResponseEntity<String> deleteAuction(@PathVariable("deleteId") Long deleteId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return auctionService.deleteAuction(deleteId, userDetails.getUser().getUsername());
    }
}
