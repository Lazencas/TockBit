package kgt.tockbit.controller;

import kgt.tockbit.service.NewsFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class NewsFeedController {
    @Autowired
    private final NewsFeedService newsFeedService;
    public NewsFeedController(NewsFeedService newsFeedService) {
        this.newsFeedService = newsFeedService;
    }




}
