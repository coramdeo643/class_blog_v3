package com.tenco.blog.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserRequest {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;

        // JoinDTO를 User Obj 변환하는 메서드 추가
        // 계층간 데이터 변환을 위해 명확하게 분리
        public User toEntity() {
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .email(this.email)
                    .build();
        }

        // 회원가입시 유효성 검증 메서드
        public void validate() {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Insert the username");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Insert the password");
            }
            // 비밀번호가 4자~12자 사이 조건 설정?
            if (password.length() > 12 || password.length() < 4) {
                throw new IllegalArgumentException("Invalid password length");
            }
            // 간단한 이메일 형식 검증(정규화 표현식)
            if (!email.contains("@")) {
                throw new IllegalArgumentException("Invalid email format");
            }
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO { // DTO for Log-in
        private String username;
        private String password;

        // Validation
        public void validate() {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Insert the username");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Insert the password");
            }

        }
    }

    // user info update
    @Data
    public static class UpdateDTO {
        private String password;
        private String email;
        // username = unique, not able to update
        // toEntity = dirty checking
        public void validate() {
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Insert the password");
            }
            if (password.length() < 4 || password.length() > 100) {
                throw new IllegalArgumentException("Invalid password length(4~100)");
            }
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("Insert the email");
            }
        }
    }
}
