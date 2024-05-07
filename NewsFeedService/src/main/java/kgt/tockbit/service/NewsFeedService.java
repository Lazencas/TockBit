package kgt.tockbit.service;

import kgt.tockbit.domain.NewsFeed;
import kgt.tockbit.dto.UserDto;
import kgt.tockbit.feign.ActivityClient;
import kgt.tockbit.repository.NewsFeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NewsFeedService {
    private final NewsFeedRepository newsFeedRepository;

    private final ActivityClient activityClient;

    @Autowired
    public NewsFeedService(NewsFeedRepository newsFeedRepository, ActivityClient activityClient) {
        this.newsFeedRepository = newsFeedRepository;
        this.activityClient = activityClient;
    }

    public List<NewsFeed> getNewsFeed(String UserEmail){

        //내가 팔로우한 유저1와, 나를 팔로우 한 유저2들 찾아야 함
        //내가 팔로우한 유저1와, 나를 팔로우한 유저2의 이메일로 뉴스피드 2번 가져오기
        System.out.println("문제찾기1");
        List<NewsFeed> newsFeeds = new ArrayList<>();
        System.out.println("문제찾기2");
        List<UserDto> followedUsers = activityClient.getFollowedByEmail(UserEmail);
        List<String> followedEmails = followedUsers.stream().map(UserDto::getEmail).collect(Collectors.toList());

        for (String email : followedEmails){
            System.out.println("이메일체크"+email);
            newsFeeds.addAll(activityClient.getActivityByEmail(email));
        }
        System.out.println("문제찾기3");

        List<UserDto> followUsers = activityClient.getFollowerByEmail(UserEmail);
        List<String> follwerEmails = followUsers.stream().map(UserDto::getEmail).collect(Collectors.toList());

        for (String email : follwerEmails){
            newsFeeds.addAll(activityClient.getActivityByEmail(email));
        }

        System.out.println("문제찾기4");
        for(NewsFeed newsFeed : newsFeeds){
            System.out.println("뉴스피드"+newsFeed.getUser());
            newsFeedRepository.save(newsFeed);
        }
        //newsfeed_db에서 그 두개의 이메일로 데이터를 조회해서, 시간순으로 정렬해서 리턴
        Collections.sort(newsFeeds, Comparator.comparing(NewsFeed::getCreatedAt).reversed());
        System.out.println("문제찾기5");
        return newsFeeds;

    }





}
