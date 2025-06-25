package com.tenco.blog.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor // DI
@Controller
public class UserController {

    private final UserRepository ur;
    private final HttpSession hs;

    // update 화면 요청
    @GetMapping("/user/update-form")
    public String updateForm(HttpServletRequest request, HttpSession hs) {
        // 1. login check
        User sUser = (User) hs.getAttribute("sessionUser");
        if(sUser == null) {
            return "redirect:/login-form";
        }
        request.setAttribute("user", sUser);
        return "user/update-form";
    }

    // update action
    @PostMapping("/user/update")
    public String update(
            UserRequest.UpdateDTO reqDTO, HttpSession hs, HttpServletRequest request) {
        // 1. login check
        User sUser = (User) hs.getAttribute("sessionUser");
        if(sUser == null) {
            return "redirect:/login-form";
        }
        // 2. validation
        reqDTO.validate();
        // 권한체크 필요없음 = 세션에서 정보를 가져오기때문
        User updateUser = ur.updateById(sUser.getId(), reqDTO);
        // session sync.
        hs.setAttribute("sessionUser", updateUser);

        // 다시 회원정보 보기 화면으로 redirect
        return "redirect:/user/update-form";
        // return "redirect:/user/update-form?error=error_message";
    }

    /**
     * 회원가입 화면 요청
     *
     * @return join-form.mustache
     */
    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    // 회원가입 액션 처리
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO, HttpServletRequest request) {
        System.out.println("=========== 회원가입요청 ============");
        System.out.println("username = " + joinDTO.getUsername());
        System.out.println("user email = " + joinDTO.getEmail());

        try {
            // 1. 입력 데이터 검증(유효성 검사)
            joinDTO.validate(); // defensive code
            // 2. 사용자명 중복 체크 username unique
            User existUser = ur.findByUsername(joinDTO.getUsername());
            if (existUser != null) {
                throw new IllegalArgumentException("Username already exists;" + joinDTO.getUsername());
            }
            // 3. 저장요청 : DTO 를 user obj로 변환
            User user = joinDTO.toEntity();
            // 4. user obj 영속화 처리
            ur.save(user);
            return "redirect:/login-form";

        } catch (Exception e) {
            // 검증 실패시 보통 에러메세지와 함께 다시 회원가입화면으로 전달\
            request.setAttribute("errorMessage", "Wrong request");
            return "user/join-form";
        }
    }

    /**
     * Login 화면 요청
     *
     * @return login-form.mustache
     */
    @GetMapping("/login-form")
    public String loginForm() {
        // 반환값이 뷰(파일)이름이 됨(뷰리졸버가 실제파일 경로를 찾아감)
        return "user/login-form";
    }

    // Login action : 자원의 요청은 GET,
    // but login request is exceptional = 보안상 이유
    // 1. 입력 데이터 검증
    // 2. 사용자명과 비밀번호를 DB에 접근해서 조회
    // 3. 로그인 성공과 실패에 따른 동작 설정 처리
    // 4. 로그인 성공, 서버측 메모리인 세션 메모리에 사용자 정보를 저장
    // 5. 메인화면으로 redirect:/
    // 6. 로그인 실패, 오류메세지 .
    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO, HttpServletRequest request) {
        System.out.println("== Log in ==");
        System.out.println("USERNAME = " + loginDTO.getUsername());

        try {
            // 1. validation
            loginDTO.validate();
            // 2. DB
            User user = ur.findByUsernameAndPassword(
                    loginDTO.getUsername(), loginDTO.getPassword());
            // 3. if login failed,
            if (user == null) {
                // login fail : no user
                throw new IllegalArgumentException("Invalid username or password");
            }
            // 4. login success
            hs.setAttribute("sessionUser", user);
            // 5. redirect to main page
            return "redirect:/";

        } catch (Exception e) {
            // 필요하다면 Error message 생성
            return "user/login-form";
        }
    }

    // log-out
    @GetMapping("/logout")
    public String logout() {
        hs.invalidate();
        return "redirect:/";
    }
}
