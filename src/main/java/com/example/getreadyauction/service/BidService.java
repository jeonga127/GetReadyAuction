package com.example.getreadyauction.service;

import com.example.getreadyauction.dto.BidRequestDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.entity.Bid;
import com.example.getreadyauction.entity.Users;
import com.example.getreadyauction.repository.AuctionRepository;
import com.example.getreadyauction.repository.BidRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;


    @Transactional
    public ResponseDto postBid(Long id, BidRequestDto bidRequestDto, Users users){
        Auction auction = auctionRepository.findById(id).orElseThrow( // 게시물이 존재하는지 여부 파악, 존재하면 담아줌
                () -> new NullPointerException("존재하지 않는 물품입니다")
        );
        if (users.getUsername().equals(auction.getUser().getUsername())) { // 물품 등록자와 입찰자 검증
            throw new IllegalArgumentException("당신은 물품 원주인 입니다...");
        }

        Optional<Bid> biddingFind = bidRepository.findByAuctionAndUser(auction, users);  // 물품에 자기의 입찰여부를 파악하기 위해 가져옴
        Optional<Bid> biddingExist = bidRepository.findByAuction(auction);  // 물품 자체의 입찰여부를 파악하기 위해 가져옴

        if (biddingExist.isEmpty()) { // 물품에 입찰이 아무것도 없을때
            if (bidRequestDto.getPrice() >= auction.getMinPrice()) { // 최소입찰가격과 같거나 크다면 입찰 가능하도록
                bidRepository.saveAndFlush(new Bid(bidRequestDto, users, auction));
                return ResponseDto.setSuccess("입찰 성공!", null);
            } else {
                throw new IllegalArgumentException("최소 입찰가 이상의 금액을 입력해주세요");
            }
        }

        if (biddingExist.isPresent()) { // 물품에 입찰이 있는데 자기는 입찰을 하지 않은 경우, 물품에 등록된 기존 입찰 가격보다 큰 값을 적었을 때
            if (biddingFind.isEmpty() && bidRequestDto.getPrice() > biddingExist.get().getPrice()) {
                bidRepository.saveAndFlush(new Bid(bidRequestDto, users, auction));
                return ResponseDto.setSuccess("입찰 성공!", null);
            } else {
                throw new IllegalArgumentException("최고 입찰가 이상의 금액을 입력해주세요");
            }
        }

        if (biddingFind.isPresent() && bidRequestDto.getPrice() > biddingExist.get().getPrice()) { // 내가 입찰을 이미 했고 또 기존 입찰가격보다 더 큰 값을 적었을때
            Bid editbid = biddingFind.get(); // 찾은 값 가져와서
            editbid.Edit(bidRequestDto);  // 수정값을 담아서 수정함
            return ResponseDto.setSuccess("입찰가 변경!", null);
        } else {
            throw new IllegalArgumentException("최고 입찰가 이상의 금액을 입력해주세요");
        }
    }
}
