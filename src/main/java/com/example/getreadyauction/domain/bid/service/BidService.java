package com.example.getreadyauction.domain.bid.service;

import com.example.getreadyauction.domain.bid.dto.BidRequestDto;
import com.example.getreadyauction.domain.auction.entity.Auction;
import com.example.getreadyauction.domain.bid.entity.Bid;
import com.example.getreadyauction.domain.user.entity.Users;
import com.example.getreadyauction.domain.auction.repository.AuctionRepository;
import com.example.getreadyauction.domain.bid.repository.BidRepository;
import com.example.getreadyauction.domain.util.ServiceUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final ServiceUtil serviceUtil;

    @Transactional
    public ResponseEntity<String> postBid(Long id, BidRequestDto bidRequestDto, Users users) {
        Auction auction = serviceUtil.validateAuction(id);

        if (users.getUsername().equals(auction.getUser().getUsername()))
            return ResponseEntity.badRequest().body("물품 주인은 입찰을 등록할 수 없습니다.");

        if (bidRequestDto.getPrice() < auction.getCurrentPrice())
            return ResponseEntity.badRequest().body("최소 입찰가 이상의 금액을 입력해주세요.");

        Optional<Bid> biddingFind = bidRepository.findByAuctionAndUser(auction, users);
        auction.setCurrentPrice(bidRequestDto.getPrice());

        if (biddingFind.isPresent()) {
            Bid existedBid = biddingFind.get();
            existedBid.edit(bidRequestDto);
            return ResponseEntity.ok("입찰가 변경");
        }

        Bid newBid = new Bid(bidRequestDto, users, auction);
        auction.setBidSize();

        bidRepository.save(newBid);
        auctionRepository.save(auction);
        return ResponseEntity.ok("입찰 성공");
    }
}