package com.example.getreadyauction.service;

import com.example.getreadyauction.dto.auction.AuctionRequestDto;
import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.entity.CategoryType;
import com.example.getreadyauction.entity.ErrorCode;
import com.example.getreadyauction.entity.Users;
import com.example.getreadyauction.exception.CustomException;
import com.example.getreadyauction.repository.AuctionRepository;
import com.example.getreadyauction.repository.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
class ServiceUtilTest {

    @Autowired
    private ServiceUtil serviceUtil;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UsersRepository usersRepository;

    @AfterEach
    void tearDown(){
        auctionRepository.deleteAllInBatch();
        usersRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("경매 물품 유무 검사")
    void validateAuction() {
        //given
        Users testUser1 = new Users("testName1", "testPW1");
        usersRepository.save(testUser1);

        AuctionRequestDto testAuctionRequestDto = AuctionRequestDto.builder()
                .title("test1의 쇼파").category(CategoryType.FURNITURE).content("쇼파").minPrice(13000).deadline("2000-01-01").build();

        Auction testAuction = Auction.builder().auctionRequestDto(testAuctionRequestDto).user(testUser1).build();
        auctionRepository.save(testAuction);

        //when
        Auction auctionResult = serviceUtil.validateAuction(testAuction.getId());

        //then
        assertThat(auctionResult.getTitle()).isEqualTo("test1의 쇼파");
        assertThat(auctionResult.getCategory()).isEqualTo(CategoryType.FURNITURE);
        assertThat(auctionResult.getContent()).isEqualTo("쇼파");
        assertThat(auctionResult.getMinPrice()).isEqualTo(13000);
        assertThat(auctionResult.getDeadline()).isEqualTo("2000-01-01");
    }

    @Test
    @DisplayName("경매 물품 유무 검사 : 존재하지 않는 경매 물품 요청")
    void validateNonExistedAuction() {
        //when & then
        CustomException testResult = assertThrows(CustomException.class,
                () -> serviceUtil.validateAuction(1L));
        assertThat(testResult.getErrorCode()).isEqualTo(ErrorCode.NOSUCH_AUCTION);
    }
}