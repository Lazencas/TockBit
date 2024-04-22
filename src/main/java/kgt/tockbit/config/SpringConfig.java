package kgt.tockbit.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kgt.tockbit.controller.UserController;
import kgt.tockbit.repository.JpaUserRepository;
import kgt.tockbit.repository.MemoryUserRepository;
import kgt.tockbit.repository.UserRepository;
import kgt.tockbit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SpringConfig {

    @PersistenceContext
    private EntityManager em;

    @Bean
    public EntityManager em(){
        return em;
    }
//    private final UserRepository userRepository;
//    @Autowired
//    public SpringConfig(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Bean
    public UserService userService(){
        return new UserService(userRepository());
    }

    @Bean
    public UserRepository userRepository(){
//        return new MemoryUserRepository();
        return new JpaUserRepository(em);
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
