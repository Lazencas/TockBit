package kgt.tockbit.feign;

import kgt.tockbit.domain.NewsFeed;
import kgt.tockbit.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "activity-service")
public interface ActivityClient {

    //해당 이메일이 활동한 이력들 가져오기
    @GetMapping("/activity/comm/{email}")
    List<NewsFeed> getActivityByEmail(@PathVariable("email") String email);

    //팔로워 이메일을 보내서, 이메일이 팔로우 한 사람들 가져오기
    @GetMapping("/activity/comm/follower/{email}")
    List<UserDto> getFollowedByEmail(@PathVariable("email") String email);

    //팔로우 된 이메일을 보내서, 이메일을 팔로우 한 사람들 가져오기
    @GetMapping("/activity/comm/follwed/{email}")
    List<UserDto> getFollowerByEmail(@PathVariable("email") String email);

}
