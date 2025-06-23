package com.tenco.blog.board;

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
    private String username;

    // CreationTimestamp : Brought by Hibernate
    // Entity 가 처음 저장할때 현재 시간을 자동으로 설정
    // PC -> DB(날짜 주입)
    // v1(SQL now()) -> v2(JPA)
    @CreationTimestamp
    private Timestamp createdAt; // created_at(auto-convert to SnakeCase)

    // Constructor
    public Board(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;
        // id, createdAt => JPA/Hibernate 자동으로 설정
    }

    // mustache 에서 표현할 시간 을 포맷기능(행위)을 스스로 만들자
    public String getTime() {
        return MyDateUtil.timestampFormat(createdAt);
    }
    
}
// DB first > Code first