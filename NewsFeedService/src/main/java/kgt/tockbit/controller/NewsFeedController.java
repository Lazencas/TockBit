package kgt.tockbit.controller;

import kgt.tockbit.domain.User;
import kgt.tockbit.jwt.JwtUtil;
import kgt.tockbit.service.NewsFeedService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewsFeedController {
    private final NewsFeedService newsFeedService;
    private final JwtUtil jwtUtil;

    public NewsFeedController(NewsFeedService newsFeedService, JwtUtil jwtUtil) {
        this.newsFeedService = newsFeedService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/newsfeed")
    public String getNewsFeed(Model model){
        User user = new User();//임시코드
//        List<NewsFeed> activities = newsFeedService.getNewsFeed(user);
//        model.addAttribute("activities", activities);
        return "newsfeed";
    }




}
