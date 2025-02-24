package app.configurations;

import app.repositories.impl.InMemoryRepository;
import app.repositories.impl.JpaUserRepositoryWrapper;
import app.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class UserConfiguration {

    @Bean
    @Primary
    public UserRepository userRepository(JpaUserRepositoryWrapper jpaUserRepositoryImpl) {
        boolean useRealDb = false; // Change this flag to switch between JPA and in-memory
        return useRealDb ? jpaUserRepositoryImpl: new InMemoryRepository();
        //return useRealDb ? jpaUserRepositoryImpl : inMemoryRepository;
    }
}
