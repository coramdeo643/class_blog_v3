package com.tenco.blog.board;

import com.tenco.blog.user.User;
import com.tenco.blog.utils.MyDateUtil;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor // 기본 생성자 = JPA 에서 Entity 는 기본 생성자가 필요
@Data
@Table(name = "board_tb")
@Entity
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

    // Constructor
//    public Board(String title, String content, String username) {
//        this.title = title;
//        this.content = content;
//        //this.username = username;
//    }
    public String getTime() {
        return MyDateUtil.timestampFormat(createdAt);
    }
}
// DB first > Code first