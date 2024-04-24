package kgt.tockbit.controller;

import kgt.tockbit.service.FollowService;
import kgt.tockbit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
public class FollowController {
    private final UserService userService;
    private final FollowService followService;

    @Autowired
    public FollowController(UserService userService, FollowService followService) {
        this.userService = userService;
        this.followService = followService;
    }

    @GetMapping("/act/{followerEmail}/{followedUserEmail}")
    public ResponseEntity<String> follow(@PathVariable String followerEmail, @PathVariable String followedUserEmail) {
        try {
            followService.follow(followerEmail, followedUserEmail);
            return new ResponseEntity<>("Followed successfully", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        }
    }





}
