package kgt.tockbit.feign;

import kgt.tockbit.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/auth/comm/{email}")
    UserDto getUserByEmail(@PathVariable("email") String email);
}
