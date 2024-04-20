package kgt.tockbit.repository;

import kgt.tockbit.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class MemoryUserRepositoryTest {

    MemoryUserRepository repository = new MemoryUserRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save(){
        User user = new User();
        user.setName("testman");

        repository.save(user);

        User result = repository.findById(user.getId()).get();
        assertThat(user).isEqualTo(result);

    }

    @Test
    public void findByName(){
        User user1 = new User();
        user1.setName("same");
        repository.save(user1);

        User user2 = new User();
        user2.setName("same2");
        repository.save(user2);

        User result = repository.findByName("same").get();

        assertThat(result).isEqualTo(user1);

    }

}
