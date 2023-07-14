package com.example.getreadyauction.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NON_LOGIN(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다"),
    EXPIRED_TOKEN (HttpStatus.BAD_REQUEST, "Expired JWT token, 만료된 JWT token 입니다."),
    INVALID_TOKEN (HttpStatus.BAD_REQUEST, "Invalid JWT signature, 유효하지 않는 JWT 서명 입니다."),
    UNSUPPORTED_TOKEN (HttpStatus.BAD_REQUEST, "Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다."),
    EMPTY_TOKEN (HttpStatus.BAD_REQUEST, "JWT claims is empty, 잘못된 JWT 토큰 입니다."),
    INVALID_AUTHORIZATION (HttpStatus.BAD_REQUEST, "본인만 삭제/수정할 수 있습니다."),
    NOSUCH_AUCTION (HttpStatus.BAD_REQUEST, "해당 경매가 존재하지 않습니다."),
    NONE_AUCTION (HttpStatus.BAD_REQUEST, "경매가 존재하지 않습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다.");

    private final HttpStatus status;
    private final String message;
}
