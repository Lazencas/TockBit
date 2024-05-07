package kgt.tockbit.repository;

import kgt.tockbit.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ActivityRepository extends JpaRepository<Activity, Long> {
//    @Query("SELECT a FROM Activity a WHERE a.user = :user OR a.follower = :follower")
//    List<Activity> findActivitiesByUserOrFollower(@Param("user") User user, @Param("follower") User follower);
List<Activity> findByUserEmail(String email);
}
