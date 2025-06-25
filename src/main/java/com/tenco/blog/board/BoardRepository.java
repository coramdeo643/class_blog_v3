package com.tenco.blog.board;

import com.tenco.blog.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    // 게시글 삭제
    @Transactional
    public void deleteById(Long id) {
        // 1. NativeQuery - 테이블 대상
        // 2. JPQL(Java Persistence Query Language) - Entity obj 대상
        // 3. 영속성 처리(em) - CRUD(.createQuery/.find/.merge/.remove)
        // JPQL
        String jpql = "delete from Board b where b.id = :id ";
        Query q = em.createQuery(jpql);
        q.setParameter("id", id);
        int deletedCount = q.executeUpdate(); // Insert, Update, Delete
        if(deletedCount == 0) {
            throw new IllegalArgumentException("There is no post to delete");
        }
    }
    @Transactional
    public void deleteByIdSafely(Long id) {
        // 영속성 context 활용한 delete
        // 1. 먼저 삭제할 entity를 영속 상태로 조회
        Board board = em.find(Board.class, id);
        // board = 영속화됨
        // 2. entity 존재여부 확인
        if(board == null) {
            throw new IllegalArgumentException("There is no post to delete");
        }
        // 3. 영속화 상태의 엔티티를 삭제상태로 변경
        em.remove(board);
        // 장점 : 1차 캐시에서 자동제거 / 연관관계처리도 자동 수행(Cascade)
    }
//    @Transactional
//    public void deleteById(Long id) {
//        Board b = findById(id);
//        em.remove(b);
//    }

    /**
     * Save the post : User 와 연관관계를 가진 Board entity 영속화
     *
     * @param board
     * @return board
     */
    @Transactional
    public Board save(Board board) {
        // 비영속 상태의 Board obj 를 영속성 context에 저장하면,
        em.persist(board);
        // 이후 시점 에는 사실 같은 메모리 주소를 가리킨다
        return board;
    }

    public List<Board> findByAll() {
        // find - JPQL query
        String jpql = "select b from Board b order by b.id desc ";
//        TypedQuery query = em.createQuery(jpql, Board.class);
//        List<Board> boardList = query.getResultList();
//        return boardList;
        return em.createQuery(jpql, Board.class).getResultList();
    }

    /**
     * Post find once(PK; Primitive key)
     *
     * @param id : Boarder entity id value
     * @return : Board entity
     */
    public Board findById(Long id) {
        // find - pk find, 무조건 EM method 활용이 이득
        return em.find(Board.class, id);
    }
}
