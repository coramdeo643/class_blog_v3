package com.tenco.blog.User;

/**
 * Builder Pattern 사용해서 User Class 설계해보자
 */
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;

    // Builder pattern design
    // 1. static method 활용해서 User 객체를 생성하는 method 만든다
    public static User builder() {
        return new User();
    }
    // 2. 각각의 멤버 method를 setting 하는 method를 만들어준다
    public User id(Integer id) {
        this.id = id;
        return this;
    }

    public User username(String username) {
        this.username = username;
        return this;
    }

    public User password(String password) {
        this.password = password;
        return this;
    }

    public User email(String email) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
} // end of User
