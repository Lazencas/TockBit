package kgt.tockbit.repository;

import kgt.tockbit.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class MemoryUserRepositoryTest {

    MemoryUserRepository repository = new MemoryUserRepository();

    @Test
    public void save(){
        User user = new User();
        user.setName("testman");

        repository.save(user);

        User result = repository.findById(user.getId()).get();
        assertThat(user).isEqualTo(null);


    }
}
