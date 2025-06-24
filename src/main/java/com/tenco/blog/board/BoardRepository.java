package com.tenco.blog.board;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

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

    @Transactional
    public void delete(Board board) {
        em.remove(board);
    }

}
