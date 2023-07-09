package com.example.getreadyauction.service;

import com.example.getreadyauction.dto.pay.PayApprovalDto;
import com.example.getreadyauction.dto.pay.PayReadyDto;
import com.example.getreadyauction.dto.pay.PayRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class PayService {

    @Value("${kakao.admin.key}")
    private String kakaoAdminKey;
    private PayReadyDto responseBody;

    public ResponseEntity<PayReadyDto> purchaseAuction(PayRequestDto payRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + kakaoAdminKey);
        headers.add("Accept", "application/json;charset=UTF-8");
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("cid", "TC0ONETIME");
        requestBody.add("partner_order_id", "GetReadyAuction");
        requestBody.add("partner_user_id", "GetReadyAuction");
        requestBody.add("item_name", payRequestDto.getItem());
        requestBody.add("quantity", "1");
        requestBody.add("total_amount", String.valueOf(payRequestDto.getPrice()));
        requestBody.add("tax_free_amount", String.valueOf((int) (payRequestDto.getPrice() * 0.9)));
        requestBody.add("approval_url", "http://localhost:8080/pay/info");
        requestBody.add("cancel_url", "http://localhost:8080/pay/info");
        requestBody.add("fail_url", "http://localhost:8080/pay/info");

        HttpEntity<MultiValueMap<String, String>> payRequest = new HttpEntity<>(requestBody, headers);
        RestTemplate rt = new RestTemplate();

        responseBody = rt.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                payRequest, PayReadyDto.class);

        return ResponseEntity.ok().body(responseBody);
    }

    public ResponseEntity<Object> getPayInfo(String pg_token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + kakaoAdminKey);
        headers.add("Accept", "application/json;charset=UTF-8");
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("cid", "TC0ONETIME");
        requestBody.add("tid", responseBody.getTid());
        requestBody.add("partner_order_id", "GetReadyAuction");
        requestBody.add("partner_user_id", "GetReadyAuction");
        requestBody.add("pg_token", pg_token);

        HttpEntity<MultiValueMap<String, String>> payRequest = new HttpEntity<>(requestBody, headers);
        RestTemplate rt = new RestTemplate();

        try {
            PayApprovalDto responseBody = rt.postForObject(
                    "https://kapi.kakao.com/v1/payment/approve",
                    payRequest, PayApprovalDto.class);

            return ResponseEntity.ok().body(responseBody);
        } catch (RestClientException exception) {
            return ResponseEntity.badRequest().body("결제 실패");
        }
    }
}
