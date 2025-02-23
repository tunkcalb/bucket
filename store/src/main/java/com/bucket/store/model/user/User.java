package com.bucket.store.model.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @Setter
    @Column(name = "id")
    private String userId;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private String name;

    @Builder
    public User(Long id, String userId, String username, String password, String name) {
//        this.id = id;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
    }
}
