package com.example.getreadyauction.service;

import com.example.getreadyauction.dto.BidRequestDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.entity.Bid;
import com.example.getreadyauction.entity.ErrorCode;
import com.example.getreadyauction.entity.Users;
import com.example.getreadyauction.exception.CustomException;
import com.example.getreadyauction.repository.AuctionRepository;
import com.example.getreadyauction.repository.BidRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    @Transactional
    public ResponseDto postBid(Long id, BidRequestDto bidRequestDto, Users users) {

        Auction auction = validateAuction(id); // 존재하는 경매인지 확인

        if (users.getUsername().equals(auction.getUser().getUsername())) // 물품 등록자와 입찰자 검증
            return ResponseDto.setBadRequest("당신은 물품 원 주인 입니다...", null);

        Optional<Bid> biddingFind = bidRepository.findByAuctionAndUser(auction, users);  // 물품에 자기의 입찰여부를 파악하기 위해 가져옴
        List<Bid> biddingExist = bidRepository.findByAuctionOrderByModifiedAtDesc(auction);  // 물품 자체의 입찰여부를 파악하기 위해, 물품에 등록된 최고 입찰가를 가져오기 위해

        if (biddingExist.isEmpty()) { // 물품에 입찰이 아무것도 없을때
            if (bidRequestDto.getPrice() >= auction.getCurrentPrice()) { // 최소입찰가격과 같거나 크다면 입찰 가능하도록
                Bid currentBid = new Bid(bidRequestDto, users, auction);
                List<Bid> newBidListBids = new ArrayList<>();
                newBidListBids.add(currentBid);

                auction.setCurrentPrice(bidRequestDto.getPrice()); // 입찰의 현재가격에 반영
                auction.setBidList(newBidListBids);

                bidRepository.save(currentBid); // 클라이언트의 첫 입찰이니 저장하도록
                return ResponseDto.setSuccess("입찰 성공!", null);
            } else
                return ResponseDto.setBadRequest("최소 입찰가 이상의 금액을 입력해주세요", null);
        }
        if (biddingFind.isEmpty() && (bidRequestDto.getPrice() > auction.getCurrentPrice())){ // 물품에 입찰이 없다는 로직이 통과되면 물품에 입찰이 무조건 있다는 것이고 클라이언트 입찰은 없을때 다음과 같은 로직 수행
            Bid currentBid = new Bid(bidRequestDto, users, auction);
            List<Bid> newBidListBids = auction.getBidList();
            newBidListBids.add(currentBid);

            auction.setCurrentPrice(bidRequestDto.getPrice());
            auction.setBidList(newBidListBids);

            bidRepository.save(currentBid); // 물품에 입찰은 있는데 클라이언트는 첫 입찰이므로 저장
            return ResponseDto.setSuccess("입찰 성공!", null);
        }
        if ((bidRequestDto.getPrice() > auction.getCurrentPrice())) { // 아니라면 클라이언트 입찰이 있다는 것이기에 요청값과 물품에 등록된 수정순으로 정렬된 입찰가격 중 0번째 가져옴
            Bid editbid = biddingFind.get(); // 찾은 값 가져와서
            editbid.edit(bidRequestDto);  // 수정값을 담아서 수정함

            List<Bid> newBidList = auction.getBidList();
            newBidList.add(editbid);

            auction.setBidList(newBidList);
            auction.setCurrentPrice(bidRequestDto.getPrice()); // 입찰의 현재가격에 반영

            return ResponseDto.setSuccess("입찰가 변경!", null);
        } else {
            return ResponseDto.setBadRequest("최고 입찰가 이상의 금액을 입력해주세요", null);
        }
    }

    /* 유효성 검사 */
    public Auction validateAuction(Long id){
        return auctionRepository.findById(id).orElseThrow( // 게시물이 존재하는지 여부 파악, 존재하면 담아줌
                () -> new CustomException(ErrorCode.NOSUCH_AUCTION)
        );
    }



}