package com.bucket.store.model.user;

import com.bucket.store.type.UserType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private UserType type;

    @Builder
    public User (Long id,String username, String password, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.type = UserType.USER;
    }
}
