package kgt.tockbit.repository;

import kgt.tockbit.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow,Long> {
//    Follow findByFollowerAndFollowedUser(User follower, User followerdUser);

}
