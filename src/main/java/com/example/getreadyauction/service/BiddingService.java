package com.example.getreadyauction.service;

import com.example.getreadyauction.dto.BiddingRequestDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.entity.Bidding;
import com.example.getreadyauction.entity.Users;
import com.example.getreadyauction.repository.AuctionRepository;
import com.example.getreadyauction.repository.BiddingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BiddingService {

    private final BiddingRepository biddingRepository;
    private final AuctionRepository auctionRepository;


    @Transactional
    public ResponseDto postBidding(Long id, BiddingRequestDto biddingRequestDto, Users users){
        Auction auction = auctionRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 물품입니다")  // null 처리
        );
        if (users.getUsername().equals(auction.getUser().getUsername())) { // 물품 등록자와 입찰자 검증
            throw new IllegalArgumentException("당신은 물품 원주인 입니다...");
        }
        Optional<Bidding> bidding = biddingRepository.findByIdAndUser(id, users);
        if (bidding.isEmpty()){
            biddingRepository.saveAndFlush(new Bidding(biddingRequestDto, users, auction));
        } else {
            Bidding editbid = new Bidding();
            editbid.Edit(biddingRequestDto);
            return ResponseDto.setSuccess("입찰가 변경!", null);
        }
        return ResponseDto.setSuccess("입찰 성공!", null);
    }
}
