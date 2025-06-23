package com.tenco.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller // IoC 대상 - 싱글톤 패턴으로 관리된다
public class BoardController {

    // Dependencies Injection
    private final BoardRepository boardRepository;

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
