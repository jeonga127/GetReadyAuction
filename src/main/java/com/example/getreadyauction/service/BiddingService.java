package com.example.getreadyauction.service;

import com.example.getreadyauction.dto.BiddingRequestDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.entity.Users;
import com.example.getreadyauction.repository.AuctionRepository;
import com.example.getreadyauction.repository.BiddingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BiddingService {

    private final BiddingRepository biddingRepository;
    private final AuctionRepository auctionRepository


    @Transactional
    public ResponseDto postBidding(Long id, BiddingRequestDto biddingRequestDto, Users users){

    }
}
