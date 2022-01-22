package dev.gokhana.userservice.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutingHandler {

    private static final String API = "/api/v1/users";
    private static final String ID = "/{id}";

    @Bean
    public RouterFunction<ServerResponse> userRouter(UserHandler userHandler) {
        return route(GET(API), userHandler::getAll)
                .andRoute(POST(API).and(accept(MediaType.APPLICATION_JSON)), userHandler::createUser)
                .andRoute(GET(API + ID), userHandler::getUserById)
                .andRoute(PUT(API + ID).and(accept(MediaType.APPLICATION_JSON)), userHandler::updateUser)
                .andRoute(DELETE(API + ID), userHandler::deleteUser)
                .andRoute(DELETE(API).and(RequestPredicates.queryParam("name", StringUtils::hasText)), userHandler::deleteUserByName);
    }

}