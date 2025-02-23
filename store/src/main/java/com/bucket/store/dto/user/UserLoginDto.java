package com.bucket.store.dto.user;

import lombok.Builder;
import lombok.Data;

public class UserLoginDto {

    @Data
    public static class Request {
        private String username;
        private String password;
    }

    @Data
    @Builder
    public static class Response {
        private String name;
    }
}
