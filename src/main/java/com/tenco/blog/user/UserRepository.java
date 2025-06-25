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
     * 로그인 요청 기능(사용자정보조회)
     *
     * @param username
     * @param password
     * @return 성공시 user entity / 실패시 null return
     */
    public User findByUsernameAndPassword(String username, String password) {
        // JPQL
        String jpql = "select u from User u " +
                "where u.username = :username and u.password = :password ";
        try {
            return em.createQuery(jpql, User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            // 일치하는 사용자가 없거나 에러 발생시 null 반환
            // 즉, 로그인 실패를 의미한다
            return null;
        }
    }

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
            String jpql = "select u from User u where u.username = :username ";
            return em.createQuery(jpql, User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public User findById(Long id) {
        User user = em.find(User.class, id);
        if (user == null) {
            throw new RuntimeException("User not found with id : " + id);
        }
        return user;
    }

    @Transactional
    public User updateById(Long id, UserRequest.UpdateDTO reqDTO) {
        // 조회, 객체의 상태값 변경, 트랜잭션 처리 >> update
        User user = findById(id);
        // password update 객체의 상태값을 행위를 통해서 변경
        user.setPassword(reqDTO.getPassword());
        // updated 영속 entity 반환(session 동기화)
        return user;
    }
}
