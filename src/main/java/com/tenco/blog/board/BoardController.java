package com.tenco.blog.board;

import com.tenco.blog.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller // IoC 대상 - 싱글톤 패턴으로 관리된다
public class BoardController {
    // Dependencies Injection
    private final BoardRepository boardRepository;

    // new post request

    /**
     * Addess : http:// 8080/
     *
     * @param session
     * @return
     */
    @GetMapping("/board/save-form")
    public String saveForm(HttpSession session) {
        // 권한 체크 > 로그인 된 사용자만 이동
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            // 로그인 안한 경우 다시 로그인 페이지로 리다이렉트 처리
            return "redirect:/login-form";
        }
        return "board/save-form";
    }
    // http://localhost:8080/board/save
    // Post save action
    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO reqDTO, HttpSession session) {

        // 권한 체크(무조건)
        try {
            User sessionUser = (User) session.getAttribute("sessionUser");
            if (sessionUser == null) {
                // 로그인 안한 경우 다시 로그인 페이지로 리다이렉트 처리
                return "redirect:/login-form";
            }
            //2. 유효성
            reqDTO.validate();
            //3. 저장
//        Board board = reqDTO.toEntity(sessionUser);
            boardRepository.save(reqDTO.toEntity(sessionUser));
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            return "board/save-form";
        }
    }

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        // 1. Post List find
        List<Board> boardList = boardRepository.findByAll();
        // 2. Board entity 에는 User entity와 연관관계 중 연관관계 호출 확인
//        boardList.get(0).getUser().getUsername();
        // 3. 뷰에 데이터 전달
        request.setAttribute("boardList", boardList);
        return "index";
    }

    // Address :
    @GetMapping("/board/{id}")
    public String detail(@PathVariable(name = "id") Long id,
                         HttpServletRequest request) {
        Board board = boardRepository.findById(id);
        request.setAttribute("board", board);
        return "board/detail";
    }


}
