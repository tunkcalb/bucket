package com.bucket.store.model.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_log_id")
    private Long id;

    @Column
    private String page;

    @Column
    private LocalDateTime accessTimestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public UserLog (Long id, String page, LocalDateTime accessTimestamp, User user) {
        this.id = id;
        this.page = page;
        this.accessTimestamp = accessTimestamp;
        this.user = user;
    }
}
