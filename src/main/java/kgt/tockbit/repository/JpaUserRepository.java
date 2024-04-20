package kgt.tockbit.repository;

import jakarta.persistence.EntityManager;
import kgt.tockbit.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class JpaUserRepository implements UserRepository{

    private final EntityManager em;

    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }
}
