package com.tenco.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {

    private final EntityManager em;

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
     * @param id : Boarder entity id value
     * @return : Board entity
     */
    public Board findById(Long id) {
        // find - pk find, 무조건 EM method 활용이 이득
        return em.find(Board.class, id);
    }


}
