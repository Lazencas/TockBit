package kgt.tockbit.service;

import kgt.tockbit.domain.*;
import kgt.tockbit.repository.*;
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
private final CommentRepository commentRepository;
private final PostRepository postRepository;

private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(JpaFollowRepository followRepository, UserRepository userRepository, CommentRepository commentRepository, PostRepository postRepository, ActivityRepository activityRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.activityRepository = activityRepository;
    }

    public void follow(String followerEmail, String followedUserEmail){
        Optional<User> follower = userRepository.findByEmail(followerEmail);
        Optional<User> followedUser = userRepository.findByEmail(followedUserEmail);
        if(follower.isPresent() && followedUser.isPresent()){
            Follow follow = new Follow();
            follow.setFollower(follower.get());
            follow.setFollowedUser(followedUser.get());
            followRepository.save(follow);
            //액티비티 추가로직
            Activity activity = new Activity();
            activity.setType(Activity.ActivityType.FOLLOW);
            activity.setUser(follower.get());
            activity.setFollower(followedUser.get());
            activityRepository.save(activity);
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
        //activity 추가
        Activity activity = new Activity();
        activity.setType(Activity.ActivityType.POST);
        activity.setUser(user);
        activity.setPost(post);
        activity.setContent(content);
        activityRepository.save(activity);
    }

    //댓글 작성
    public void createComment(String email, Long post_id, String content){
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다.")
        );
        Post post = postRepository.findById(post_id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물이 없습니다.")
        );
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(content);
        commentRepository.save(comment);
        //activity 추가
        Activity activity = new Activity();
        activity.setType(Activity.ActivityType.COMMENT);
        activity.setPost(post);
        activity.setUser(user);
        activity.setContent(content);
        activityRepository.save(activity);
    }


    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public List<Comment> findcommentsAll(){
        return commentRepository.findAll();
    }

    public boolean likePost(Long post_id){
        Post post = postRepository.findById(post_id).orElseThrow(
                () -> new IllegalArgumentException("해당 포스트가 존재 하지 않습니다.")
        );
        post.setLikes(post.getLikes()+1);
        postRepository.save(post);
        //activity 추가
        Activity activity = new Activity();
        activity.setType(Activity.ActivityType.PLIKE);
        activity.setPost(post);
        activityRepository.save(activity);
        return true;
    }

    public boolean likeComment(Long comment_id){
        Comment comment = commentRepository.findById(comment_id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재 하지 않습니다.")
        );
        comment.setLikes(comment.getLikes()+1);
        commentRepository.save(comment);
        //activity 추가
        Activity activity = new Activity();
        activity.setType(Activity.ActivityType.CLIKE);
        activity.setComment(comment);
        activityRepository.save(activity);
        return true;
    }

    public void createActivity(Activity activity){
        activityRepository.save(activity);
    }






}
