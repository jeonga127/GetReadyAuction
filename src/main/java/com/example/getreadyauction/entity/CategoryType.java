package com.example.getreadyauction.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryType {

    FURNITURE("가구/인테리어"),
    FASSION("패션의류/잡화"),
    ELECTRONICS("전자제품"),
    SPORTS("스포츠/레저"),
    ETC("기타");

    private final String text;
}
