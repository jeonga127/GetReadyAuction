package com.example.getreadyauction.service;

import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.entity.ErrorCode;
import com.example.getreadyauction.exception.CustomException;
import com.example.getreadyauction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceUtil {

    private final AuctionRepository auctionRepository;

    public Auction validateAuction(Long id){
        return auctionRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOSUCH_AUCTION)
        );
    }
}
