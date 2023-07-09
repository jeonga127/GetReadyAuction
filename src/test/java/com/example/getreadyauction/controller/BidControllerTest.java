package com.example.getreadyauction.controller;

import com.example.getreadyauction.dto.BidRequestDto;
import com.example.getreadyauction.dto.auction.AuctionRequestDto;
import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.entity.Bid;
import com.example.getreadyauction.entity.CategoryType;
import com.example.getreadyauction.entity.Users;
import com.example.getreadyauction.security.UserDetailsImpl;
import com.example.getreadyauction.service.AuctionService;
import com.example.getreadyauction.service.BidService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class BidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BidService bidService;

    @Test
    @DisplayName("입찰 등록 POST 요청 : 인증된 사용자")
    void postBid() throws Exception {
        //given
        Users testUser = new Users("testname1", "@testPassword1");
        UserDetailsImpl userDetails = new UserDetailsImpl(testUser, testUser.getUsername());
        BidRequestDto bidRequestDto = new BidRequestDto(25000);

        ResponseEntity<String> testResult = ResponseEntity.ok("입찰 성공");
        when(bidService.postBid(Mockito.anyLong(), Mockito.any(BidRequestDto.class), Mockito.any(Users.class))).thenReturn(testResult);

        //when & then
        mockMvc.perform(post("/bid/add/{bidId}", 1L)
                        .content(objectMapper.writeValueAsString(bidRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(userDetails)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("입찰 성공"));
    }

    @Test
    @DisplayName("입찰 등록 POST 요청 : 인증되지 않은 사용자")
    void postBidWithNoAuthorization() throws Exception {
        //given
        BidRequestDto bidRequestDto = new BidRequestDto(25000);

        //when & then
        mockMvc.perform(post("/bid/{bidId}", 1L)
                        .content(objectMapper.writeValueAsString(bidRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}