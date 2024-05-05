package kgt.tockbit.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringConfig {

    @PersistenceContext
    private EntityManager em;

    @Bean
    public EntityManager em(){
        return em;
    }


}
