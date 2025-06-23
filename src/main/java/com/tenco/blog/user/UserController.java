package com.tenco.blog.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    // 회원가입 화면 요청
    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    // Login 화면 요청
    @GetMapping("/login-form")
    public String loginForm() {
        // 반환값이 뷰(파일)이름이 됨(뷰리졸버가 실제파일 경로를 찾아감)
        return "user/login-form";
    }

    // update 화면 요청

    @GetMapping("/user/update-form")
    public String updateForm() {
        return "user/update-form";
    }

    @GetMapping("/logout")
    public String logout() {
        // "redirect : " 스프링에서 접두사를 사용하면 다른 URL 로 리다이렉트됨
        // 즉 리다이렉트 한다는것은 뷰를 렌더링하지않고 브라우저가 재요청
        return "redirect:/";
    }

}
