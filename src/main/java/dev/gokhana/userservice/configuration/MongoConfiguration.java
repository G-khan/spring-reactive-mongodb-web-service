package dev.gokhana.userservice.configuration;


import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import dev.gokhana.userservice.model.User;
import dev.gokhana.userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;

@Configuration
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "users";
    }

    @Override
    @Bean
    @DependsOn("embeddedMongoServer")
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(String.format("mongodb://localhost:%d", port));
    }

    @Bean
    public CommandLineRunner insertData(UserRepository userMongoRepository) {
        return args -> {
            userMongoRepository.save(new User("Vincenzo", 1)).subscribe();
            userMongoRepository.save(new User("Mario", 2)).subscribe();
            userMongoRepository.save(new User("Gennaro", 3)).subscribe();
            userMongoRepository.save(new User("Diego", 4)).subscribe();
        };
    }
}