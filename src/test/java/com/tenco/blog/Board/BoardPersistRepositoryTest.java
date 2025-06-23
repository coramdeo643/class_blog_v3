package com.tenco.blog.Board;

import com.tenco.blog.board.Board;
import com.tenco.blog.board.BoardPersistRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(BoardPersistRepository.class)
@DataJpaTest
public class BoardPersistRepositoryTest {
//
//    @Autowired
//    private BoardPersistRepository br;
//
//    @Test
//    public void deleteById_test() {
//        // given
//        Long id = 1L;
//        // when
//        // 삭제할 게시글이 실제로 존재하는지 확인
//        Board targetBoard = br.findById(id);
//        Assertions.assertThat(targetBoard).isNotNull();
//        // persist context 에서 delete
//        br.deleteById(id);
//        // then
//        List<Board> afterDeleteBoardList = br.findAll();
//        Assertions.assertThat(afterDeleteBoardList.size()).isEqualTo(3);
//    }
//
//
//    @Test
//    public void findAll_test() {
//        // given = db/data.sql
//        // when
//        List<Board> boardList = br.findAll();
//        // then
//        System.out.println("size test = " + boardList.size());
//        System.out.println("1st title check= " + boardList.get(0).getTitle());
//
//        // Native Query 를 사용한다는 것을 영속 context를 우회해서 일 처리
//        // JPQL 바로 영속성 context를 우회하지만 조회된 이후에는 영속성 상태가 된다
//
//        for (Board board : boardList) {
//            Assertions.assertThat(board.getId()).isNotNull();
//        }
//    }
//
//
//    @Test
//    public void save_test() {
//        // given
//        Board board = new Board("제목111", "내용111", "작성자111");
//
//        // 저장전 객체의 상태값 확인
//        Assertions.assertThat(board.getId()).isNull();
//        System.out.println("DB 저장 전 board = " + board);
//        // when
//        // 영속성 context를 통한 Entity save
//        Board savedBoard = br.save(board);
//        // then
//        // 1. 저장 후에 자동 생성된 ID 확인
//        Assertions.assertThat(savedBoard.getId()).isNotNull();
//        Assertions.assertThat(savedBoard.getId()).isGreaterThan(0);
//        // 2. 내용 일치 여부 확인
//        Assertions.assertThat(savedBoard.getTitle()).isEqualTo("제목111");
//        // 3. JPA 자동으로 생성된 생성시간 확인
//        Assertions.assertThat(savedBoard.getCreatedAt()).isNotNull();
//        // 4. 원본 객체 - board, savedBoard(Persist context)
//        Assertions.assertThat(board).isSameAs(savedBoard);
//    }

}
