package com.example.getreadyauction.domain.bid.repository;

import com.example.getreadyauction.domain.auction.repository.AuctionRepository;
import com.example.getreadyauction.domain.bid.repository.BidRepository;
import com.example.getreadyauction.domain.bid.dto.BidRequestDto;
import com.example.getreadyauction.domain.auction.dto.AuctionRequestDto;
import com.example.getreadyauction.domain.auction.entity.Auction;
import com.example.getreadyauction.domain.bid.entity.Bid;
import com.example.getreadyauction.domain.auction.entity.CategoryType;
import com.example.getreadyauction.domain.scheduler.service.SchedulerService;
import com.example.getreadyauction.domain.user.repository.UsersRepository;
import com.example.getreadyauction.domain.user.entity.Users;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@MockBean(SchedulerService.class)
class BidRepositoryTest {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @AfterEach
    void tearDown() {
        bidRepository.deleteAllInBatch();
        auctionRepository.deleteAllInBatch();
        usersRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("경매 물품 & 사용자가 일치하는 입찰 내역 조회")
    void findByAuctionAndUser() {
        //given
        Users testUser = new Users("testname1", "@testPassword1");
        Users testOwner = new Users("testOwner", "@testOwnerPassword");

        usersRepository.save(testUser);
        usersRepository.save(testOwner);

        AuctionRequestDto testAuctionRequestDto = AuctionRequestDto.builder()
                .title("testAuction")
                .category(CategoryType.FURNITURE)
                .content("쇼파")
                .minPrice(13000)
                .deadline(LocalDateTime.parse("2000-01-01T00:00:00"))
                .build();

        Auction testAuction = Auction.builder().auctionRequestDto(testAuctionRequestDto).user(testOwner).build();
        auctionRepository.save(testAuction);

        BidRequestDto testBidRequestDto = new BidRequestDto(15000);
        Bid testBid = new Bid(testBidRequestDto, testUser, testAuction);
        bidRepository.save(testBid);

        //when
        Optional<Bid> resultBid = bidRepository.findByAuctionAndUser(testAuction, testUser);

        //then
        assertThat(resultBid).isPresent();
        assertThat(resultBid.get()).isEqualTo(testBid);
    }

    @Test
    @DisplayName("특정 경매 물품에 대한 입찰 조회 & 수정 날짜 순 정렬")
    void findByAuctionOrderByModifiedAtDesc() {
        //given
        Users testUser1 = new Users("testname1", "@testPassword1");
        Users testUser2 = new Users("testname2", "@testPassword2");
        Users testUser3 = new Users("testname3", "@testPassword3");
        Users testUser4 = new Users("testname4", "@testPassword4");
        Users testOwner = new Users("testOwner", "@testPassword5");

        usersRepository.saveAll(List.of(testUser1, testUser2, testUser3, testUser4, testOwner));

        AuctionRequestDto testAuctionRequestDto = AuctionRequestDto.builder()
                .title("testAuction")
                .category(CategoryType.FURNITURE)
                .content("쇼파")
                .minPrice(13000)
                .deadline(LocalDateTime.parse("2000-01-01T00:00:00"))
                .build();

        Auction testAuction = Auction.builder().auctionRequestDto(testAuctionRequestDto).user(testOwner).build();
        auctionRepository.save(testAuction);

        BidRequestDto testBidRequestDto = new BidRequestDto(15000);
        Bid testBid1 = new Bid(testBidRequestDto, testUser1, testAuction);
        Bid testBid2 = new Bid(testBidRequestDto, testUser2, testAuction);
        Bid testBid3 = new Bid(testBidRequestDto, testUser3, testAuction);
        Bid testBid4 = new Bid(testBidRequestDto, testUser4, testAuction);

        bidRepository.saveAll(List.of(testBid1, testBid2, testBid3, testBid4));

        BidRequestDto testBidUpdateDto = new BidRequestDto(17000);
        testBid2.edit(testBidUpdateDto);
        bidRepository.saveAndFlush(testBid2);

        List<Bid> expectedResult = new ArrayList<>(List.of(testBid1, testBid2, testBid3, testBid4));
        expectedResult.sort(Comparator.comparing(Bid::getModifiedAt).reversed());

        //when
        List<Bid> testResult = bidRepository.findByAuctionOrderByModifiedAtDesc(testAuction);

        //then
        assertThat(testResult)
                .hasSize(4)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("특정 유저에 대한 모든 입찰 내역 조회")
    void findAllByUserOrderByModifiedAtDesc() {
        //given
        Users testUser1 = new Users("testname1", "@testPassword1");
        Users testOwner = new Users("testOwner", "@testPassword5");
        usersRepository.saveAll(List.of(testUser1, testOwner));

        AuctionRequestDto testAuctionRequestDto = AuctionRequestDto.builder()
                .title("testAuction")
                .category(CategoryType.FURNITURE)
                .content("쇼파")
                .minPrice(13000)
                .deadline(LocalDateTime.parse("2000-01-01T00:00:00"))
                .build();

        Auction testAuction = Auction.builder().auctionRequestDto(testAuctionRequestDto).user(testOwner).build();
        auctionRepository.save(testAuction);

        //when

        //then
    }
}