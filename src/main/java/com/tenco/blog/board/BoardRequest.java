package com.tenco.blog.board;

import com.tenco.blog.user.User;
import lombok.Data;

/**
 * Client 에게 넘어온 Data를
 * Object로 변화해서 전달하는 DTO 역할 담당
 */
public class BoardRequest {

    // 게시글 저장 DTO
    @Data
    public static class SaveDTO {
        private String title;
        private String content;
        // username 제거 : TODO 세션에서 가져올예정

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