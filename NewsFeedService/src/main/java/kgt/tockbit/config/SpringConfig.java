package kgt.tockbit.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
