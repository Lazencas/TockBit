package kgt.tockbit.service;

import jakarta.ws.rs.NotFoundException;
import kgt.tockbit.domain.Activity;
import kgt.tockbit.domain.Comment;
import kgt.tockbit.domain.Follow;
import kgt.tockbit.domain.Post;
import kgt.tockbit.dto.NewsFeedDto;
import kgt.tockbit.dto.PostDto;
import kgt.tockbit.dto.UserDto;
import kgt.tockbit.feign.UserClient;
import kgt.tockbit.repository.ActivityRepository;
import kgt.tockbit.repository.CommentRepository;
import kgt.tockbit.repository.FollowRepository;
import kgt.tockbit.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActivityService {
private final FollowRepository followRepository;
private final CommentRepository commentRepository;
private final PostRepository postRepository;
private final UserClient userClient;
private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(FollowRepository followRepository, CommentRepository commentRepository, PostRepository postRepository, UserClient userClient, ActivityRepository activityRepository) {
        this.followRepository = followRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userClient = userClient;
        this.activityRepository = activityRepository;
    }

    public void follow(String followerEmail, String followedUserEmail){
        UserDto user = userClient.getUserByEmail(followerEmail);
        UserDto followed = userClient.getUserByEmail(followedUserEmail);
        Follow follow = new Follow();
        follow.setFollower(user.getEmail());
        follow.setFollowed(followed.getEmail());
        followRepository.save(follow);

        Activity activity = new Activity();
        activity.setType(Activity.ActivityType.FOLLOW);
        activity.setUserEmail(user.getEmail());
        activity.setFollowed(followed.getEmail());
        activityRepository.save(activity);

//        Optional<User> follower = userRepository.findByEmail(followerEmail);
//        Optional<User> followedUser = userRepository.findByEmail(followedUserEmail);
//        if(follower.isPresent() && followedUser.isPresent()){
//            Follow follow = new Follow();
//            follow.setFollower(follower.get());
//            follow.setFollowedUser(followedUser.get());
//            followRepository.save(follow);

            //액티비티 추가로직
//            Activity activity = new Activity();
//            activity.setType(Activity.ActivityType.FOLLOW);
//            activity.setUser(follower.get());
//            activity.setFollower(followedUser.get());
//            activityRepository.save(activity);
//        }
    }
    public PostDto getPost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException("해당 아이디의 게시물을 찾지 못했습니다.")
        );
        System.out.println(post.getUserEmail());
        UserDto user = userClient.getUserByEmail(post.getUserEmail());
        System.out.println("해당 유저의 이메일은"+user.getEmail());
        System.out.println("해당 유저의 이름은"+user.getName());

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setUserName(user.getName());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setContent(post.getContent());

        return postDto;
    }


    public void createPost(String email, String title, String content){
//        User user = userRepository.findByEmail(email).orElseThrow(
//                () -> new IllegalArgumentException("해당 사용자가 없습니다.")
//        );
        UserDto user = userClient.getUserByEmail(email);
        System.out.println("해당 유저의 이메일은"+user.getEmail());
        System.out.println("해당 유저의 이름은"+user.getName());
        Post post = new Post();

        post.setTitle(title);
        post.setContent(content);
        post.setUserName(user.getName());
        post.setUserEmail(user.getEmail());
        postRepository.save(post);
        //activity 추가
        Activity activity = new Activity();
        activity.setType(Activity.ActivityType.POST);
        activity.setUserEmail(user.getEmail());
        activity.setPost(post);
        activity.setContent(content);
        activityRepository.save(activity);
    }

    //댓글 작성
    public void createComment(String email, Long post_id, String content){
        UserDto user = userClient.getUserByEmail(email);
        Post post = postRepository.findById(post_id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물이 없습니다.")
        );
        Comment comment = new Comment();
        comment.setUserEmail(user.getEmail());
        comment.setPost(post);
        comment.setContent(content);
        commentRepository.save(comment);
        //activity 추가
        Activity activity = new Activity();
        activity.setType(Activity.ActivityType.COMMENT);
        activity.setPost(post);
        activity.setUserEmail(user.getEmail());
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

    //해당 유저의 활동들을 반환
    public List<NewsFeedDto> getActivityByUserEmail(String email){
        System.out.println("조회1");
       List<Activity> activities =  activityRepository.findByUserEmail(email);
       List<NewsFeedDto> activityDtos = new ArrayList<>();
       for (Activity activity : activities){
           NewsFeedDto dto = new NewsFeedDto();
           System.out.println("이거확인해야함"+activity.getUserEmail());
           dto.setId(activity.getId());
           dto.setType(activity.getType().toString());
           dto.setUserEmail(activity.getUserEmail());
           dto.setCreatedAt(activity.getCreatedAt());
           if(activity.getFollowed()!=null){
               dto.setFollowed(activity.getFollowed());
           }
          if(activity.getContent() != null){
              dto.setContent(activity.getContent());
          }
           // Post와 Comment 객체가 null이 아닌 경우에만 매칭시킵니다.
           if (activity.getPost() != null) {
               dto.setPost_id(activity.getPost().getId().toString());
               dto.setTitle(activity.getPost().getTitle());
           }
           if (activity.getComment() != null) {
               dto.setComment_id(activity.getComment().getId().toString());
           }
           activityDtos.add(dto);
       }
        return activityDtos;
    }

    //내가 팔로우한 유저들 반환
    public List<UserDto> getFollowed(String followerEmail){
        System.out.println("조회2, 이메일체크"+followerEmail);
    List<String> followedEmails = followRepository.findFollowedByEmail(followerEmail);
        System.out.println("유저0"+followedEmails);
    return followedEmails.stream().map(email -> {
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        System.out.println("유저"+userDto.getEmail());
        return userDto;
    }).collect(Collectors.toList());
    }

    //나를 팔로우 한 유저들 반환
    public List<UserDto> getFollow(String followedEmail){
        System.out.println("조회3");
        List<String> followerEmails = followRepository.findFollowerByEmail(followedEmail);
        return followerEmails.stream().map(email -> {
            UserDto userDto = new UserDto();
            userDto.setEmail(email);
            return userDto;
        }).collect(Collectors.toList());
    }




}
