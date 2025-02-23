package com.bucket.store.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ALREADY_IN_USE(409, "이미 데이터가 존재합니다."),
    INTERNAL_SERVER_ERROR(500, "서버 에러입니다"),

    // 회원 관련
    DUPLICATE_USERNAME(400, "중복된 아이디입니다."),
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(401, "잘못된 비밀번호입니다."),
    // 상품 관련
    BRAND_NOT_FOUND(404, "존재하지 않는 브랜드입니다."),
    OUT_OF_STOCK(409, "재고가 부족합니다."),
    ENCRYPTION_ERROR(500, "데이터 암호화에 실패했습니다."),
    DECRYPTION_ERROR(500, "데이터 복호화에 실패했습니다.");

    private final int status;
    private final String message;
}