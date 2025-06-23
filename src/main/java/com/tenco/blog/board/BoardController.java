package com.tenco.blog.board;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller // IoC 대상 - 싱글톤 패턴으로 관리된다
public class BoardController {

    // 생성자 의존 주입 - DI 처리
    private final BoardPersistRepository br;

    // address : /board/{{board.id}}/delete
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id) {
        br.deleteById(id);
        return "redirect:/";
    }

    /**
     * address : http://localhost:8080/boarder/{id}/update-form
     * @param : id(board pk)
     * @return : update-form.mustache
     * @GetMapping
     */
    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        // select * from board-tb where id = 4;
        Board board = br.findById(id);
        // mustache file 조회된 data binding 처리
        request.setAttribute("board", board);
        return "board/update-form";
    }

    @PostMapping("/board/{id}/update-form")
    public String update(@PathVariable(name = "id") Long id, BoardRequest.UpdateDTO reqDTO) {
        System.out.println("정상 파싱 확인" + reqDTO.toString());
        // Transaction
        // 수정 -- select - 값을 확인 - data 수정 --> update
        // JPA persist context 활용
        // reqDTO.validate();
        br.update(id, reqDTO);
        // 수정 전략을 Dirty checking 활용
        // 장점 : update 쿼리 자동생성 / 변경 필드만 update / 영속성 컨텍스트 일관성 유지 / 1차 캐시 자동 갱신
        // 성공 시 리스트 화면으로 redirect
        return "redirect:/";
    }

    // 게시글 상세 보기(주소설계)
    // GET : http://localhost:8080/boarder/3
    @GetMapping("/board/{id}")
    public String detail(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        Board board = br.findById(id);
        request.setAttribute("board", board);
        // 1차 캐시 효과 - DB 접근하지않고 바로 영속성 context 에서 가져옴
        return "board/detail";
    }

    // 목록 화면 연결
    // 1. index.mustache 파일 변환 기능
    // 주소 = http://localhost:8080/, http://localhost:8080/index
    @GetMapping({"/", "/index"})
    public String boardList(HttpServletRequest request) {
        List<Board> boardList = br.findAll();
        request.setAttribute("boardList", boardList);
        return "index";
    }

    // 게시글 작성 화면 연결 처리
    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    // 게시글 작성 액션(수행) 처리
    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO reqDTO) {
        // HTTP 요청 본문 : title=값&content=값&username=값
        // form tag MIME type(application/x-www-form-urlencoded)

        // reqDTO = 사용자가 던진 데이터 상태가 있음
        // DTO > Board > DB
//        Board board = new Board(reqDTO.getTitle(), reqDTO.getContent(), reqDTO.getUsername());
        Board board = reqDTO.toEntity();
        br.save(board);

        // PGR
        return "redirect:/";
    }


}
