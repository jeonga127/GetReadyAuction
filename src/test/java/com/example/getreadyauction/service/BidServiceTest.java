package com.example.getreadyauction.service;

import com.example.getreadyauction.dto.BidRequestDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.dto.auction.AuctionRequestDto;
import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.entity.Bid;
import com.example.getreadyauction.entity.CategoryType;
import com.example.getreadyauction.entity.Users;
import com.example.getreadyauction.repository.AuctionRepository;
import com.example.getreadyauction.repository.BidRepository;
import com.example.getreadyauction.repository.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class BidServiceTest {

    @Autowired
    private BidService bidService;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UsersRepository usersRepository;

    private Users testUser1;
    private Users testUser2;
    private Auction testAuction;

    @BeforeEach
    void setUp() {
        testUser1 = new Users("testName1", "testPW1");
        testUser2 = new Users("testName2", "testPW2");
        usersRepository.saveAll(List.of(testUser1, testUser2));

        AuctionRequestDto testAuctionRequestDto = AuctionRequestDto.builder()
                .title("test1의 쇼파").category(CategoryType.FURNITURE).content("쇼파").minPrice(13000).deadline("2000-01-01").build();

        testAuction = Auction.builder().auctionRequestDto(testAuctionRequestDto).user(testUser1).build();
        auctionRepository.save(testAuction);
    }

    @AfterEach
    void tearDown() {
        bidRepository.deleteAllInBatch();
        auctionRepository.deleteAllInBatch();
        usersRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("입찰 등록 테스트 : 유저가 첫 입찰을 등록")
    void postUsersFirstBid() {
        //given
        BidRequestDto testBidrequest = new BidRequestDto(15000);

        //when
        ResponseEntity<String> testResult = bidService.postBid(testAuction.getId(), testBidrequest, testUser2);

        //then
        assertThat(testResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(testResult.getBody()).isEqualTo("입찰 성공");
    }

    @Test
    @DisplayName("입찰 등록 테스트 : 유저가 입찰을 재등록")
    void postUsersAgainBid() {
        //given
        BidRequestDto testBidrequest1 = new BidRequestDto(15000);
        Bid originalBid = new Bid(testBidrequest1, testUser2, testAuction);
        bidRepository.save(originalBid);

        BidRequestDto testBidrequest2 = new BidRequestDto(17000);

        //when
        ResponseEntity<String> testResult = bidService.postBid(testAuction.getId(), testBidrequest2, testUser2);

        //then
        assertThat(testResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(testResult.getBody()).isEqualTo("입찰가 변경");
    }

    @Test
    @DisplayName("입찰 등록 테스트 : 자신이 등록한 경매 물품에 입찰을 등록")
    void postBidToMyAuction() {
        //given
        BidRequestDto testBidrequest = new BidRequestDto(15000);

        //when
        ResponseEntity<String> testResult = bidService.postBid(testAuction.getId(), testBidrequest, testUser1);

        //then
        assertThat(testResult.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(testResult.getBody()).isEqualTo("물품 주인은 입찰을 등록할 수 없습니다.");
    }

    @Test
    @DisplayName("입찰 등록 테스트 : 최소 입찰가보다 낮은 가격으로 입찰을 등록")
    void postBidWithLowPrice() {
        //given
        BidRequestDto testBidrequest = new BidRequestDto(10000);

        //when
        ResponseEntity<String> testResult = bidService.postBid(testAuction.getId(), testBidrequest, testUser2);

        //then
        assertThat(testResult.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(testResult.getBody()).isEqualTo("최소 입찰가 이상의 금액을 입력해주세요.");
    }
}