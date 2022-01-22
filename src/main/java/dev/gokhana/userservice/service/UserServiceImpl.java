package dev.gokhana.userservice.service;


import com.mongodb.client.result.DeleteResult;
import dev.gokhana.userservice.model.User;
import dev.gokhana.userservice.repository.UserRepository;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ReactiveMongoTemplate template;

    public UserServiceImpl(UserRepository userRepository, ReactiveMongoTemplate template) {
        this.userRepository = userRepository;
        this.template = template;
    }

    @Override
    public Mono<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Flux<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> saveUser(User userDTO) {
        return userRepository.save(userDTO);
    }

    @Override
    public Mono<User> updateUser(String id, User userDTO) {
        return userRepository.findById(id).flatMap(user -> {
            userDTO.setId(user.getId()); // if there is something else to update do it here.
            return userRepository.save(userDTO);
        });
    }

    @Override
    public Mono<Void> deleteUser(String id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Mono<Long> deleteByName(String name) {
        return template.remove(query(where("name").is(name)), User.class).map(DeleteResult::getDeletedCount);
    }
}
