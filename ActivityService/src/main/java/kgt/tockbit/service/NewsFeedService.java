package kgt.tockbit.service;

import kgt.tockbit.domain.Activity;
import kgt.tockbit.domain.User;
import kgt.tockbit.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class NewsFeedService {
    @Autowired
    private final ActivityRepository activityRepository;
    public NewsFeedService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> getNewsFeed(User user){
        List<Activity> activities = activityRepository.findActivitiesByUserOrFollower(user, user);

        activities.sort(Comparator.comparing(Activity::getCreatedAt).reversed());

        return activities;

    }





}
