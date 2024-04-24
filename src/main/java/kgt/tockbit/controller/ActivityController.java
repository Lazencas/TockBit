package kgt.tockbit.controller;

import io.jsonwebtoken.Claims;
import kgt.tockbit.domain.Post;
import kgt.tockbit.jwt.JwtUtil;
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
    private final JwtUtil jwtUtil;

    @Autowired
    public ActivityController(UserService userService, ActivityService activityService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.activityService = activityService;
        this.jwtUtil = jwtUtil;
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
    public String createPost(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue,@RequestParam("title") String title, @RequestParam("text") String content){
        String email =  bringme_email_jwt(tokenValue);
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
    @GetMapping("/activity/posts")
    public String showPosts(Model model){
        List<Post> posts = activityService.findAll();
        model.addAttribute("posts", posts);
        return "users/posts";
    }















    public String bringme_email_jwt(String tokenValue){
        // JWT 토큰 substring
        String token = jwtUtil.substringToken(tokenValue);
        // 토큰 검증
        if(!jwtUtil.validateToken(token)){
            throw new IllegalArgumentException("Token Error");
        }
        // 토큰에서 사용자 정보 가져오기
        Claims info = jwtUtil.getUserInfoFromToken(token);
        // 사용자 email
        String email = info.getSubject();
        return  email;
    }

}
