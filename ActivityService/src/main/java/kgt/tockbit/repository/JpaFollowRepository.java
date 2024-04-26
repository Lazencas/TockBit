package kgt.tockbit.repository;

import kgt.tockbit.domain.Follow;
import kgt.tockbit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFollowRepository extends JpaRepository<Follow,Long> {
    Follow findByFollowerAndFollowedUser(User follower, User followerdUser);

}
