package kgt.tockbit.repository;

import kgt.tockbit.domain.NewsFeed;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ActivityRepository extends JpaRepository<NewsFeed, Long> {
//    @Query("SELECT a FROM NewsFeed a WHERE a.user = :user OR a.follower = :follower")
//    List<NewsFeed> findActivitiesByUserOrFollower(@Param("user") User user, @Param("follower") User follower);

}
