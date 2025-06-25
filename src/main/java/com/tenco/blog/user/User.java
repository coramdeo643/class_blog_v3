package com.tenco.blog.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "user_tb")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Username 중복 방지 unique true setting
    @Column(unique = true)
    //@Column(length = 20, nullable = false) 제약사항 설정 가능!!!
    private String username;

    private String password;
    private String email;

    // Entity persist call 시 자동으로 pc 현재 시간을 setting
    @CreationTimestamp
    private Timestamp createdAt;

    // Constructor
    // 객체 생성시 가독성과 안전성 향상
    @Builder
    public User(Long id, String username, String password, String email, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }
}
