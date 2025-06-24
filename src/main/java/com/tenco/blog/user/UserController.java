package com.tenco.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor // DI
@Controller
public class UserController {

    private final UserRepository userRepository;

    /**
     * 회원가입 화면 요청
     * @return join-form.mustache
     */
    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    // 회원가입 액션 처리
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        System.out.println("=========== 회원가입요청 ============");
        System.out.println("username = " + joinDTO.getUsername());
        System.out.println("user email = " + joinDTO.getEmail());

        try {
            // 1. 입력 데이터 검증(유효성 검사)
            joinDTO.validate(); // defensive code
            // 2. 사용자명 중복 체크 username unique
            User existUser = userRepository.findByUsername(joinDTO.getUsername());
            if (existUser != null) {
                throw new IllegalArgumentException("Username already exists;" + joinDTO.getUsername());
            }
            // 3. 저장요청 : DTO 를 user obj로 변환
            User user = joinDTO.toEntity();
            // 4. user obj 영속화 처리
            userRepository.save(user);
            return "redirect:/login-form";

        } catch (Exception e) {
            // 검증 실패시 보통 에러메세지와 함께 다시 회원가입화면으로 전달
            return "user/join-form";
        }
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
