package com.tenco.blog.board;

import com.tenco.blog.user.User;
import com.tenco.blog.utils.MyDateUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


@Data
@Table(name = "board_tb")
@Entity
@Builder
@NoArgsConstructor // 기본 생성자 = JPA 에서 Entity 는 기본 생성자가 필요
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    // v2
    // private String username;
    // v3 : Board entity - User entity와 연관관계 성립

    // 다대일 N:1 relation setting
    // 여러개의 게시글에는 한명의 작성자를 가질수있어

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // FK column name
    private User user;

    @CreationTimestamp
    private Timestamp createdAt; // created_at(auto-convert to SnakeCase)

    // 게시글의 소유자를 직접 확인하는 기능을 만들어본다
    public boolean isOwner(Long checkUserId) {
        return this.user.getId().equals(checkUserId);
    }

    public String getTime() {
        return MyDateUtil.timestampFormat(createdAt);
    }
}
// DB first > Code first