package dev.gokhana.userservice.controller.valdiation;

import dev.gokhana.userservice.controller.ValidatorHandler;
import dev.gokhana.userservice.model.User;
import dev.gokhana.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class UserHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserHandler.class);

    private final UserRepository userMongoRepository;
    private final ValidatorHandler validator;

    public UserHandler(UserRepository userMongoRepository, ValidatorHandler validator) {
        this.userMongoRepository = userMongoRepository;
        this.validator = validator;
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return userMongoRepository.findAll()
                .collectList()
                .flatMap(users -> {
                    if (users.isEmpty()) {
                        return ServerResponse.noContent().build();
                    }
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromValue(users));
                });
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(User.class)
                .doOnNext(validator::validate)
                .flatMap(userMongoRepository::save)
                .doOnSuccess(userSaved -> LOGGER.info("User saved with id: " + userSaved.getId()))
                .doOnError(e -> LOGGER.error("Error in saveUser method", e))
                .map(userSaved -> UriComponentsBuilder.fromPath(("/{id}")).buildAndExpand(userSaved.getId()).toUri())
                .flatMap(uri -> ServerResponse.created(uri).build());
    }

}
