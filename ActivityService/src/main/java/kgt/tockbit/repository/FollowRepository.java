package kgt.tockbit.repository;

import kgt.tockbit.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow,Long> {
//    Follow findByFollowerAndFollowedUser(User follower, User followerdUser);
    List<Follow> findByFollower(String followerEmail);
    List<Follow> findByFollowed(String followedEmail);

    // 해당 이메일을 팔로우한 유저 리스트를 반환
    @Query("SELECT f.follower FROM Follow f WHERE f.followed = :email")
    List<String> findFollowerByEmail(@Param("email") String followedEmail);

    // 해당 이메일이 팔로우한 유저 리스트를 반환
    @Query("SELECT f.followed FROM Follow f WHERE f.follower = :email")
    List<String> findFollowedByEmail(@Param("email") String followerEmail);


}
