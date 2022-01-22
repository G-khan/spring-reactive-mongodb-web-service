package dev.gokhana.userservice.controller;

import dev.gokhana.userservice.controller.validation.ValidatorHandler;
import dev.gokhana.userservice.model.User;
import dev.gokhana.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class UserHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserHandler.class);

    private final ValidatorHandler validator;
    private final UserService userService;

    public UserHandler(ValidatorHandler validator, UserService userService) {
        this.validator = validator;
        this.userService = userService;
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return userService.getUsers().collectList().flatMap(users -> {
            if (users.isEmpty()) {
                return ServerResponse.noContent().build();
            }
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(fromValue(users));
        });
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {
        String id = request.pathVariable("id");

        return userService.getUserById(id).flatMap(contact -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromValue(contact))).switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(User.class)
                .doOnNext(validator::validate)
                .flatMap(userService::saveUser)
                .doOnSuccess(userSaved -> LOGGER.info("User saved with id: {} ", userSaved.getId()))
                .doOnError(e -> LOGGER.error("Error in saveUser method", e))
                .flatMap(user -> ServerResponse.created(getToUri(user)).bodyValue(user));
    }

    public Mono<ServerResponse> updateUser(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        String id = request.pathVariable("id");
        return userMono
                .flatMap(user -> userService.updateUser(id, user))
                .flatMap(user -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(user))).switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        String id = request.pathVariable("id");
        return userService.deleteUser(id)
                .then(ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteUserByName(ServerRequest request) {
        Optional<String> name = request.queryParam("name");
        if (name.isEmpty())
            return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(Map.of("error", "There is no name in the field"));
        return userService.deleteByName(name.get())
                .then(ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    private URI getToUri(User userSaved) {
        return UriComponentsBuilder.fromPath(("/{id}"))
                .buildAndExpand(userSaved.getId()).toUri();
    }
}
