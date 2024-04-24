package kgt.tockbit.service;

import kgt.tockbit.domain.Follow;
import kgt.tockbit.domain.User;
import kgt.tockbit.repository.JpaFollowRepository;
import kgt.tockbit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class FollowService {
private final JpaFollowRepository followRepository;
private final UserRepository userRepository;

    @Autowired
    public FollowService(JpaFollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    public void follow(String followerEmail, String followedUserEmail){
        System.out.println("체큭1");
        Optional<User> follower = userRepository.findByEmail(followerEmail);
        Optional<User> followedUser = userRepository.findByEmail(followedUserEmail);
        System.out.println("체큭2");
        if(follower.isPresent() && followedUser.isPresent()){
            System.out.println("체큭3");
            Follow follow = new Follow();
            follow.setFollower(follower.get());
            follow.setFollowedUser(followedUser.get());
            followRepository.save(follow);
            System.out.println("체큭4");
        }
    }


}
