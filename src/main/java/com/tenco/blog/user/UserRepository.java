package com.tenco.blog.user;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor // = DI
@Repository // IoC + Singleton pattern
public class UserRepository {

    private final EntityManager em;

    /**
     * Sign-in (User insert / save user info.)
     *
     * @param user
     * @return User entity
     */
    @Transactional
    public User save(User user) { // 매개변수에 들어오는 user object 는 비영속화된 상태
        em.persist(user); // 여기서부터 persist context 에 user 객체 관리하기 시작한다
        // 트랜잭션 커밋 시점에 실제 INSERT 쿼리를 실행한다
        return user;
    }

    // 사용자명 중복 체크용 조회 기능 (user unique)
    public User findByUsername(String username) {
//        String jpql = "select u from User u where u.username = :username; ";
//        TypedQuery<User> typedQuery = em.createQuery(jpql, User.class);
//        typedQuery.setParameter("username", username);
//        return typedQuery.getSingleResult();
        try {
            String jpql = "select u from User u where u.username = :username; ";
            return em.createQuery(jpql, User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
