package kgt.tockbit.controller;

import kgt.tockbit.domain.NewsFeed;
import kgt.tockbit.service.NewsFeedService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Controller
public class NewsFeedController {
    private final NewsFeedService newsFeedService;

    public NewsFeedController(NewsFeedService newsFeedService) {
        this.newsFeedService = newsFeedService;
    }

    @GetMapping("/newsfeed")
    public List<NewsFeed> getNewsFeed(@RequestHeader("User-Email") String userEmail){
        return newsFeedService.getNewsFeed(userEmail);
    }




}
