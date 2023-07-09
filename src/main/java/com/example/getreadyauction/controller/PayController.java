package com.example.getreadyauction.controller;

import com.example.getreadyauction.dto.pay.PayReadyDto;
import com.example.getreadyauction.dto.pay.PayRequestDto;
import com.example.getreadyauction.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pay")
@RequiredArgsConstructor
public class PayController {

    private final PayService payService;

    @PostMapping
    public ResponseEntity<PayReadyDto> purchaseAuction(@RequestBody PayRequestDto payRequestDto) {
        return payService.purchaseAuction(payRequestDto);
    }

    @GetMapping("/info")
    public ResponseEntity<Object> purchaseSuccess(@RequestParam("pg_token") String pg_token){
        return payService.getPayInfo(pg_token);
    }
}
