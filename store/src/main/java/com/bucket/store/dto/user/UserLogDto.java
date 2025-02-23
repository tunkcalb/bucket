package com.bucket.store.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLogDto {
    @JsonProperty("access_timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime accessTimestamp;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("page")
    private String page;
}
