package com.fedorovigord.task_manager.handlers;

import com.fedorovigord.task_manager.model.user.User;
import com.fedorovigord.task_manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService userService;


    public Mono<ServerResponse> getUserRoles(ServerRequest req) {
        final String userKeycloakId = req.pathVariable("keycloakUserId");
        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(userService.getUserRoles(userKeycloakId), String.class);
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest req) {

        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(userService.getAllUsersWithRoles(), User.class);
    }


    public Mono<ServerResponse> getAllRoles(ServerRequest req) {

        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(userService.getAllRoles(), String.class);
    }
    public Mono<ServerResponse> createUser(ServerRequest req) {
        final var user = userService.createUser(req.bodyToMono(User.class));

        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(user, String.class);
    }

    public Mono<ServerResponse> addRoleToUser(ServerRequest req) {
        final var user = userService.addRoleToUser(req.bodyToMono(User.class));

        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(user, User.class);
    }

    public Mono<ServerResponse> deleteRoleUser(ServerRequest req) {
        final var user = userService.deleteRoleUser(req.bodyToMono(User.class));

        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(user, User.class);
    }
}
