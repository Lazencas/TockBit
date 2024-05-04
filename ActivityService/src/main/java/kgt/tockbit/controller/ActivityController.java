package kgt.tockbit.controller;

import kgt.tockbit.domain.Post;
import kgt.tockbit.service.ActivityService;
import kgt.tockbit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class ActivityController {
    private final UserService userService;
    private final ActivityService activityService;

    @Autowired
    public ActivityController(UserService userService, ActivityService activityService) {
        this.userService = userService;
        this.activityService = activityService;
    }

    @ResponseBody
    @GetMapping("/activity/{followerEmail}/{followedUserEmail}")
    public ResponseEntity<String> follow(@PathVariable String followerEmail, @PathVariable String followedUserEmail) {
        try {
            activityService.follow(followerEmail, followedUserEmail);
            return new ResponseEntity<>("Followed successfully", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/activity/post")
    public String Post(){
        return "users/post";
    }

    @PostMapping("/activity/post")
    public String createPost(@RequestHeader("User-Email") String email,@RequestParam("title") String title, @RequestParam("text") String content){
        activityService.createPost(email,title, content);
        return "redirect:/activity/post";
    }
    @ResponseBody
    @GetMapping("/activity/post/{post_id}/like")
    public ResponseEntity<String> likePost(@PathVariable("post_id") Long post_id){
        boolean success = activityService.likePost(post_id);
        if(success){
            return ResponseEntity.ok("게시물에 좋아요가 추가되었습니다!");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시물을 찾을 수 없습니다.");
        }
    }
    @ResponseBody
    @GetMapping("/activity/comment/{comment_id}/like")
    public ResponseEntity<String> likeComment(@PathVariable("comment_id") Long comment_id){
        boolean success = activityService.likeComment(comment_id);
        if(success){
            return ResponseEntity.ok("댓글에 좋아요가 추가되었습니다!");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글을 찾을 수 없습니다.");
        }
    }


    @GetMapping("/activity/posts")
    public String showPosts(Model model){
        List<Post> posts = activityService.findAll();
        model.addAttribute("posts", posts);
        return "users/posts";
    }
    @ResponseBody
    @GetMapping("/activity/post/{post_id}/comment")
    public String createComment(@RequestHeader("User-Email") String email,@PathVariable("post_id") Long post_id, @RequestParam("con") String content){
        activityService.createComment(email,post_id,content);
        return "success";
    }

}
