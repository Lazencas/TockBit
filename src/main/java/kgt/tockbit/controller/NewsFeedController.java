package kgt.tockbit.controller;

import io.jsonwebtoken.Claims;
import kgt.tockbit.domain.Activity;
import kgt.tockbit.domain.User;
import kgt.tockbit.jwt.JwtUtil;
import kgt.tockbit.service.NewsFeedService;
import kgt.tockbit.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NewsFeedController {
    private final NewsFeedService newsFeedService;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    public NewsFeedController(NewsFeedService newsFeedService, JwtUtil jwtUtil, UserService userService) {
        this.newsFeedService = newsFeedService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @GetMapping("/newsfeed")
    public String getNewsFeed(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue, Model model){
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
        User user = userService.findOne(email).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        List<Activity> activities = newsFeedService.getNewsFeed(user);
        model.addAttribute("activities", activities);
        return "newsfeed";
    }




}
