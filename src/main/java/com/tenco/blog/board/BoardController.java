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
import java.util.SimpleTimeZone;

@RequiredArgsConstructor
@Controller // IoC 대상 - 싱글톤 패턴으로 관리된다
public class BoardController {
    // Dependencies Injection
    private final BoardRepository boardRepository;

    // 게시글 수정하기 화면 요청
    // /board/{{board.id}}/update-form
    // 1. 인증검사(로그인)
    // 2. 수정할 게시글 존재여부확인
    // 3. 권한 체크
    // 4. 수정 폼에 기존 데이터 뷰 바인딩
    @GetMapping("/board/{id}/update-form")
    public String updateForm(
            @PathVariable(name = "id") Long boardId,
            HttpServletRequest req, HttpSession hs) {
        // 1.
        User sessionUser = (User) hs.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/login-form";
        }
        // 2.
        Board b = boardRepository.findById(boardId);
        if (b == null) {
            throw new RuntimeException("There is no post to update");
        }
        // 3.
        if (!b.isOwner(sessionUser.getId())) {
            throw new RuntimeException("Not eligible to update");
        }
        // 4.
        req.setAttribute("board", b);
        // 내부에서 스프링컨테이너 뷰 리졸브를 활용해서 머스태치 파일 찾기
        return "board/update-form";
    }

    // 게시글 수정 액션 : Dirty checking
    // /board/{{board.id}}/update-form
    // 1. 인증검사 - 로그인체크
    // 2. 유효성검사 - 데이터 검증
    // 3. 권한체크를 위해 게시글 다시 조회
    // 4. 더티체킹을 통한 수정 설정
    // 5. 수정 완료 후 게시글 상세보기로 redirect.
    @PostMapping("/board/{id}/update-form")
    public String update(@PathVariable(name = "id") Long boardId,
                         BoardRequest.UpdateDTO reqDTO,
                         HttpSession hs) {
        // 1.
        User sessionUser = (User) hs.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/login-form";
        }
        // 2.
        reqDTO.validate();
        // 3.
        Board board = boardRepository.findById(boardId);
        if(!board.isOwner(sessionUser.getId())) {
            throw new RuntimeException("Not eligible to update");
        }
        //4.
        boardRepository.updateById(boardId,reqDTO);
        return "redirect:/board/" + boardId; // http://localhost:8080/board/1
    }

    // 게시글 삭제 액션 처리
    // /board/{{board.id}}/delete method="post"
    // 1. 로그인 여부 확인(인증검사)
    //  > 로그인 안되어있으면, 로그인 페이지로 리다이렉트 처리
    //  > 로그인 되있으면, 게시글이 존재하는지 다시 확인 - 없으면, 이미 삭제된 게시물입니다
    // 2. 권한 체크(1번 유저 게시물인데 3번 유저가 삭제할수없음)
    // 3. 삭제 후, 인덱스(리스트) 화면으로 redirect
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id, HttpSession hs) {
        // 1. 로그인 체크 Define.SESSION_USER
        User sessionUser = (User) hs.getAttribute("sessionUser"); // object 로 떨어짐 > user로 다운캐스팅
        if (sessionUser == null) {
            // 로그인 하라고 redirect
            // redirect:/ >> 내부에서 페이지 찾는것 아님,
            // 다시 클라이언트에 와서 > GET 요청이 온 것 > HTTP 메세지 생성됨
            return "redirect:/login-form";
        }
        // 게시물 존재여부확인(내가 삭제하기전에 삭제되어있을수도있음)
        Board board = boardRepository.findById(id);
        if (board == null) {
            throw new IllegalArgumentException("It is deleted already");
        }
        // 2. 권한체크
        if (!board.isOwner(sessionUser.getId())) {
            throw new RuntimeException("Not eligible to delete");
        }
//        if (!(sessionUser.getId() == board.getUser().getId())) {
//            throw new RuntimeException("Not eligible to delete");
//        }
        // 3. 권한확인이후 삭제처리
        boardRepository.deleteById(id);
        // redirect
        return "redirect:/";
    }

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
