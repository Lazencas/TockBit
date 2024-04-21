package kgt.tockbit.service;

import kgt.tockbit.domain.User;
import kgt.tockbit.repository.MemoryUserRepository;
import kgt.tockbit.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceIntegrationTest {
   @Autowired
   UserService userService;
   @Autowired
   UserRepository userRepository;


    @Test
    void 회원가입() {
        //given
        User user = new User();
        user.setName("user1");
        user.setEmail("ab@ab.com");
        user.setPassword("1234");

        //when
       String saveEmail = userService.join(user);

        //then
        User findUser = userService.findOne(saveEmail).get();
        assertThat(user.getEmail()).isEqualTo(findUser.getEmail());

    }
    @Test
    public void 중복이메일회원() {
        //given
        User user1 = new User();
        user1.setEmail("aa@ab.com");

        User user2 = new User();
        user2.setEmail("aa@ab.com");

        //when
        userService.join(user1);
        assertThrows(IllegalStateException.class, ()-> userService.join(user2));

        //then

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