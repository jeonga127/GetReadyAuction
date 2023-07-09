package com.example.getreadyauction.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AmountDto {
    private int total;
    private int tax_free;
    private int vat;
    private int point;
    private int discount;
    private int green_deposit;
}
