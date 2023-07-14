package com.example.getreadyauction.service;

import com.example.getreadyauction.domain.bid.dto.BidRequestDto;
import com.example.getreadyauction.domain.auction.dto.AuctionRequestDto;
import com.example.getreadyauction.domain.auction.dto.AuctionResponseDto;
import com.example.getreadyauction.domain.auction.entity.Auction;
import com.example.getreadyauction.domain.bid.entity.Bid;
import com.example.getreadyauction.domain.auction.entity.CategoryType;
import com.example.getreadyauction.domain.mypage.service.MyPageService;
import com.example.getreadyauction.domain.scheduler.service.SchedulerService;
import com.example.getreadyauction.domain.user.entity.Users;
import com.example.getreadyauction.domain.auction.repository.AuctionRepository;
import com.example.getreadyauction.domain.bid.repository.BidRepository;
import com.example.getreadyauction.domain.user.repository.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@MockBean(SchedulerService.class)
class MyPageServiceTest {

    @Autowired
    private MyPageService myPageService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;

    private Users testUser1;
    private Users testUser2;

    private Auction testAuction1;
    private Auction testAuction2;

    @BeforeEach
    void setUp() {
        testUser1 = new Users("testname1", "@testPassword1");
        testUser2 = new Users("testname2", "@testPassword2");
        usersRepository.saveAll(List.of(testUser1, testUser2));

        AuctionRequestDto testAuctionRequestDto1 = AuctionRequestDto.builder()
                .title("test1의 쇼파").category(CategoryType.FURNITURE).content("쇼파").minPrice(13000).deadline(LocalDateTime.parse("2000-01-01T00:00:01")).build();
        AuctionRequestDto testAuctionRequestDto2 = AuctionRequestDto.builder()
                .title("test1의 셔츠").category(CategoryType.FASSION).content("셔츠").minPrice(5000).deadline(LocalDateTime.parse("2000-01-02T00:00:01")).build();

        testAuction1 = Auction.builder().auctionRequestDto(testAuctionRequestDto1).user(testUser1).build();
        testAuction2 = Auction.builder().auctionRequestDto(testAuctionRequestDto2).user(testUser1).build();
        auctionRepository.saveAll(List.of(testAuction1, testAuction2));

        BidRequestDto testBidRequestDto1 = new BidRequestDto(15000);
        Bid testBid1 = new Bid(testBidRequestDto1, testUser2, testAuction1);
        Bid testBid2 = new Bid(testBidRequestDto1, testUser2, testAuction2);
        bidRepository.saveAll(List.of(testBid1, testBid2));
    }

    @AfterEach
    void tearDown() {
        bidRepository.deleteAllInBatch();
        auctionRepository.deleteAllInBatch();
        usersRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("유저가 등록한 모든 경매 물품 조회")
    void getAllMyAuctions() {
        //given
        Pageable pageable = PageRequest.of(0, 5);

        List<Auction> auctionsList = new ArrayList<>(List.of(testAuction1, testAuction2));
        auctionsList.sort(Comparator.comparing(Auction::getModifiedAt).reversed());
        List<AuctionResponseDto> expectedResult = auctionsList.stream().map(AuctionResponseDto::new).collect(Collectors.toList());

        //when
        ResponseEntity<List<AuctionResponseDto>> testResult = myPageService.getAllMyAuctions(pageable, testUser1);

        //then
        assertThat(testResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(testResult.getBody())
                .hasSize(2)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("유저가 입찰한 모든 경매 물품 조회")
    void getAllMyBids() {
        //given
        Pageable pageable = PageRequest.of(0, 5);

        List<Auction> auctionsList = new ArrayList<>(List.of(testAuction1, testAuction2));
        auctionsList.sort(Comparator.comparing(Auction::getModifiedAt).reversed());
        List<AuctionResponseDto> expectedResult = auctionsList.stream().map(AuctionResponseDto::new).collect(Collectors.toList());

        //when
        ResponseEntity<List<AuctionResponseDto>> testResult = myPageService.getAllMyBids(pageable, testUser2);

        //then
        assertThat(testResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(testResult.getBody())
                .hasSize(2)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }
}