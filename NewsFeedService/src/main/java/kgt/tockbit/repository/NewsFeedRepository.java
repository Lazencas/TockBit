package kgt.tockbit.repository;

import kgt.tockbit.domain.NewsFeed;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NewsFeedRepository extends JpaRepository<NewsFeed, Long> {

}
