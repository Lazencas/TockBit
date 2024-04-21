package kgt.tockbit.service;

import kgt.tockbit.domain.User;
import kgt.tockbit.repository.MemoryUserRepository;
import kgt.tockbit.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    MemoryUserRepository userRepository = new MemoryUserRepository();
    UserService userService = new UserService();

    @Test
    void 회원가입() {
        //given
        User user = new User();
        user.setName("user1");
        user.setEmail("ab@ab.com");
        user.setPassword("1234");

        //when
       Long saveId = userService.join(user);

        //then
        User findUser = userService.findOne(saveId).get();
        assertThat(user.getEmail()).isEqualTo(findUser.getName());

    }

    @Test
    void login() {
    }

    @Test
    void logout() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void findUsers() {
    }

    @Test
    void findOne() {
    }
}