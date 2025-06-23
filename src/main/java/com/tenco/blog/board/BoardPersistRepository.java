package com.tenco.blog.board;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor // 필수 멤버변수 확인해서 생성자에 등록해줌
@Repository //IoC 대상 - Singleton pattern
public class BoardPersistRepository {
//    // JPA core Interface
//    // @Autowired // = DI // final 쓰면 autowired 사용불가
//    private final EntityManager em;
//
//    // Post delete(persist context)
//    @Transactional
//    public void deleteById(Long id) {
//        // 1. 먼저 삭제할 entity를 영속 상태로 조회
//        Board board = em.find(Board.class, id);
//        // 영속 상태의 entity를 삭제 상태로 변경
//        em.remove(board);
//        // transaction이 커밋 되는 순간 삭제 처리
//        // remove() process :
//        // 1. Board entity 영속 상태에서 remove() call, 삭제 상태로 변경
//        // 2. 1차 cache 에서 해당 entity delete
//        // 3. transaction commit 시점에 delete SQL 자동 실행
//        // 4. 연관관계 처리 자동 수행(cascade 설정 시).
//    }
//
//    // 게시글 수정(DB 접근 계층)
//    @Transactional
//    public void update(Long boardId, BoardRequest.UpdateDTO updateDTO) {
//        Board board = findById(boardId);
//        // board -> persist context 1st cache key=value 값이 저장 되어있다
//        board.setTitle(updateDTO.getTitle());
//        board.setContent(updateDTO.getContent());
//        //board.setUsername(updateDTO.getUsername());
//        // Transaction 끝나면 persist context 에서 변경 감지를 한다
//        // 변경 감지(Dirty Checking)
//        // 1. 영속성 컨텍스트가 엔티티 최초상태를 스냅샷으로 보관
//        // 2. 필드 값 변경시 현재 상태와 스냅샷 비교
//        // 3. 트랜잭션 커밋 시점에 변경된 필드만 UPDATE 쿼리를 자동 생성
//        // 4. update board_tb set title=?, content=?, username=? where id = ?
//        // .
//    }
//
//    // 게시글 한건 조회 쿼리
//    // em.find() / JPQL / NativeQuery (상황에 맞게 적절한 기술 사용)
//    public Board findById(Long id) {
//        // 1차 캐시 활용
//        return em.find(Board.class, id);
//    }
//
//    // JPQL 게시글 한건 조회(비교용)
//    public Board findByIdJPQL(Long id) {
//        // Named parameter recommended
//        String jpql = "select b from Board b where b.id = :id ";
//
////        Query query = em.createQuery(jpql,Board.class);
////        query.setParameter("id", id);
////        Board board = (Board) query.getSingleResult();
//        try {
//            return em.createQuery(jpql, Board.class)
//                    .setParameter("id", id)
//                    .getSingleResult(); // Warning! 결과가 없으면 무조건 NoResultException 발생!
//        } catch (Exception e) {
//            return null;
//        }
//    }
//    // JPQL 단점 :
//    // 1. 1차 캐시 우회하여 항상 DB 접근
//    // 2. 코드가 복잡
//    // 3. 예외처리 필요
//
//    // find + JPQL 셀프 페스트
//
//
//    // JPQL 사용 게시글 목록 조회
//    public List<Board> findAll() {
//        // JPQL : Entity 객체를 대상으로 하는 객체지향 쿼리
//        // Board = Entity Class, b = Alias
//        String jpql = "select b from Board b order by b.id desc ";
//        // em.createNativeQuery() = v1
//        /* Query query = em.createQuery(jpql, Board.class);
//           List<Board> boardList = query.getResultList();
//           return boardList; */
//        return em.createQuery(jpql, Board.class).getResultList();
//    }
//
//
//    // 게시글 저장 기능 - 영속성 컨텍스트 활용
//    @Transactional
//    public Board save(Board board) {
//        // v1 -> NativeQuery...
//
//        // 1. 매개변수로 받은 board 는 현재 비영속 상태,
//        // = 아직 영속성 context에 관리되지않는 상태
//        // = DB와 아직 연관없는 순수 Java 객체 상태
//        //
//        // 2. em.persist(board) = 영속성 context 에 저장하는 개념
//        // = 영속성 context 가 board 객체를 관리한다 .
//
//        em.persist(board);
//        // 3. Transaction commit 시점에 Insert Query 실행
//        // -> 이때 영속성 context의 변경사항이 자동으로 DB에 반영됨
//        // -> board 객체에 id 필드에 자동으로 생성도니 값이 설정됨.
//
//
//        // 4. 영속 상태로 된 board 객체로 반환
//        // -> 이 시점에는 자동으로 board id 멤버 변수에 db pk 값이 할당된 상태이다.
//
//        return board;
//    }
//

}
