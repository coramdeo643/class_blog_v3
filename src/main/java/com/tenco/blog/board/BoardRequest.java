package com.tenco.blog.board;

import com.tenco.blog.user.User;
import lombok.Data;

/**
 * Client 에게 넘어온 Data를
 * Object로 변화해서 전달하는 DTO 역할 담당
 */
public class BoardRequest {

    // 게시글 수정용 DTO 설계
    @Data
    public static class UpdateDTO {
        private String title;
        private String content;
        // toEntity method 없어도 됨 dirty checking 활용
        // 유효성 검사
//        private String username;
        public void validate() {
            if(title == null  || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Insert the title");
            }
            if(content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("Insert the content");
            }
        }
    }

    // 게시글 저장 DTO
    @Data
    public static class SaveDTO {
        private String title;
        private String content;
        // (User) <-- toEntity() 호출 할 때 세션에서 가져와서 넣어준다
        public Board toEntity(User user){
            return Board.builder()
                    .title(this.title)
                    .user(user)
                    .content(this.content)
                    .build();
        }

        public void validate() {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Insert the title");
            }
            if (content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("Insert the content");
            }
        }
    }
}