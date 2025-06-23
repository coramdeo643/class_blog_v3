package com.tenco.blog.User;

public class UserBuilderTest {
    // main test
    public static void main(String[] args) {
        Student student1 = Student.builder().id(100).username("Go").build(); // .build() = new Student
        // Lombok builder pattern >> .build() must be used!!!

        User user1 = User.builder().id(1).username("Hong").password("1234").email("abc@emiail");
        System.out.println(user1);

        User user2 = User.builder().id(1).username("Teemo");
        System.out.println(user2);

    }
}
