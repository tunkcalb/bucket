package com.bucket.store.dto.user;

import lombok.Data;

public class UserSignUpDto {

    @Data
    public static class Request {
        String username;
        String password;
        String name;
    }
}
