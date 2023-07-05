package com.example.getreadyauction.service;

import com.example.getreadyauction.dto.auction.AuctionAllResponseDto;
import com.example.getreadyauction.dto.auction.AuctionRequestDto;
import com.example.getreadyauction.dto.auction.AuctionResponseDto;
import com.example.getreadyauction.entity.*;
import com.example.getreadyauction.exception.CustomException;
import com.example.getreadyauction.repository.AuctionRepository;
import com.example.getreadyauction.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;
    private final ServiceUtil serviceUtil;

    @Transactional(readOnly = true)
    public ResponseEntity<List<AuctionResponseDto>> getAllAuctions(Pageable pageable) {
        List<Auction> auctionList = auctionRepository.findAllByOrderByCreatedAtDesc(pageable).getContent();
        List<AuctionResponseDto> auctionResponseDtoList = auctionList.stream().map(AuctionResponseDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(auctionResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<AuctionResponseDto>> getCategorizedAuctions(Pageable pageable, CategoryType category) {
        List<Auction> auctionList = auctionRepository.findAllByCategoryOrderByModifiedAtDesc(pageable, category).getContent();
        List<AuctionResponseDto> auctionResponseDtoList = auctionList.stream().map(AuctionResponseDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(auctionResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<AuctionResponseDto>> getSearchedAuction(Pageable pageable, String search) {
        List<Auction> auctionList = auctionRepository.findAllByTitleContainingOrderByModifiedAtDesc(pageable, search).getContent();
        List<AuctionResponseDto> auctionResponseDtoList = auctionList.stream().map(AuctionResponseDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(auctionResponseDtoList);
    }

    @Transactional
    public ResponseEntity<AuctionAllResponseDto> getDetailedAuctions(Long id) {
        Auction auction = serviceUtil.validateAuction(id);
        auction.setView(1);
        List<Bid> bidList = bidRepository.findByAuctionOrderByModifiedAtDesc(auction);
        return ResponseEntity.ok(new AuctionAllResponseDto(auction, bidList));
    }

    @Transactional
    public ResponseEntity<String> addAuction(AuctionRequestDto auctionRequestDto, Users users) {
        Auction auction = new Auction(auctionRequestDto, users);
        auctionRepository.save(auction);
        return ResponseEntity.ok("경매 물품 등록 완료");
    }

    @Transactional
    public ResponseEntity<String> editAuction(Long id, AuctionRequestDto auctionRequestDto, String username) {
        Auction auction = serviceUtil.validateAuction(id);

        if (username.equals(auction.getUser().getUsername()))
            auction.edit(auctionRequestDto);
        else throw new CustomException(ErrorCode.INVALID_AUTHORIZATION);

        return ResponseEntity.ok("경매 물품 수정 완료");
    }

    @Transactional
    public ResponseEntity<String> deleteAuction(Long id, String username) {
        Auction auction = serviceUtil.validateAuction(id);

        if (username.equals(auction.getUser().getUsername()))
            auctionRepository.deleteById(id);
        else throw new CustomException(ErrorCode.INVALID_AUTHORIZATION);

        return ResponseEntity.ok("경매 물품 삭제 완료");
    }
}

