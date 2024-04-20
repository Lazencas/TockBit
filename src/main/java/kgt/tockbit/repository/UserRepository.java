package kgt.tockbit.repository;

import kgt.tockbit.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    Optional<User> updateById(Long id, String name, String greet, byte[] image, String password);
    Optional<User> deleteById(Long id);
    List<User> findAll();

}
