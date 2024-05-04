package kgt.tockbit.repository;

import kgt.tockbit.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query("SELECT a FROM Activity a WHERE a.user = :user OR a.follower = :follower")
    List<Activity> findActivitiesByUserOrFollower(@Param("user") User user, @Param("follower") User follower);
}
