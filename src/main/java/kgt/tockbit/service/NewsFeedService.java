package kgt.tockbit.service;

import kgt.tockbit.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsFeedService {
    @Autowired
    private final ActivityRepository activityRepository;
    public NewsFeedService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }



}
