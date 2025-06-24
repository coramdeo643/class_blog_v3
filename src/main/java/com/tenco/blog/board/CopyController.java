//package com.tenco.blog.board;
//
//import com.tenco.blog.user.User;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@Controller
//public class CopyController {
//    private final BoardRepository br;
//
//    @GetMapping("/board/save-form")
//    public String saveForm(HttpSession ses) {
//        User sesUser = (User) ses.getAttribute("sesUser");
//        if (sesUser == null) {
//            return "redirect:/login-form";
//        }
//        return "board/save-form";
//    }
//
//    @PostMapping("/board/save")
//    public String save(BoardRequest.SaveDTO reqDTO, HttpSession ses) {
//        try {
//            User sesUser = (User) ses.getAttribute("sesUser");
//            if(sesUser == null) {
//                return "redirect:/login-form";
//            }
//            reqDTO.validate();
//            br.save(reqDTO.toEntity(sesUser));
//            return "redirect:/";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "board/save-form";
//        }
//    }
//
//    @PostMapping("/board/{id}/delete")
//    public String delete(@PathVariable(name = "id") Long id) {
//        br.deleteById(id);
//        return "redirect:/";
//    }
//
//    @GetMapping("/")
//    public String index(HttpServletRequest req) {
//        List<Board> bList = br.findByAll();
//        req.setAttribute("boardList", bList);
//        return "index";
//    }
//
//    @GetMapping("/board/{id}")
//    public String detail(@PathVariable(name = "id") Long id,
//                         HttpServletRequest req) {
//        Board b = br.findById(id);
//        req.setAttribute("board", b);
//        return "board/detail";
//    }
//
//}
