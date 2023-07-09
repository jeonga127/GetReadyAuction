package com.example.getreadyauction.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PayReadyDto {
    private String tid;
    private String next_redirect_pc_url;
    private Date created_at;
}
