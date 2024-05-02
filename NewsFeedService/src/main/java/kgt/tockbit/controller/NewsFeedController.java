package kgt.tockbit.controller;

import kgt.tockbit.domain.User;
import kgt.tockbit.service.NewsFeedService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewsFeedController {
    private final NewsFeedService newsFeedService;

    public NewsFeedController(NewsFeedService newsFeedService) {
        this.newsFeedService = newsFeedService;
    }

    @GetMapping("/newsfeed")
    public String getNewsFeed(Model model){
        User user = new User();//임시코드
//        List<NewsFeed> activities = newsFeedService.getNewsFeed(user);
//        model.addAttribute("activities", activities);
        return "newsfeed";
    }




}
