package kgt.tockbit.repository;

import kgt.tockbit.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    User save(User member);

}
