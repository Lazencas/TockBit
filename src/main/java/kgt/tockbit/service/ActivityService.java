package kgt.tockbit.service;

import kgt.tockbit.domain.Follow;
import kgt.tockbit.domain.Post;
import kgt.tockbit.domain.User;
import kgt.tockbit.repository.JpaFollowRepository;
import kgt.tockbit.repository.PostRepository;
import kgt.tockbit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActivityService {
private final JpaFollowRepository followRepository;
private final UserRepository userRepository;

private final PostRepository postRepository;

    @Autowired
    public ActivityService(JpaFollowRepository followRepository, UserRepository userRepository, PostRepository postRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public void follow(String followerEmail, String followedUserEmail){
        Optional<User> follower = userRepository.findByEmail(followerEmail);
        Optional<User> followedUser = userRepository.findByEmail(followedUserEmail);
        if(follower.isPresent() && followedUser.isPresent()){System.out.println("체큭3");
            Follow follow = new Follow();
            follow.setFollower(follower.get());
            follow.setFollowedUser(followedUser.get());
            followRepository.save(follow);

        }
    }

    public void createPost(String email, String title, String content){
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다.")
        );
        Post post = new Post();
        post.setUser(user);
        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public boolean likePost(Long post_id){
        Post post = postRepository.findById(post_id).orElseThrow(
                () -> new IllegalArgumentException("해당 포스트가 존재 하지 않습니다.")
        );
        post.setLikes(post.getLikes()+1);
        postRepository.save(post);
        return true;
    }





}
