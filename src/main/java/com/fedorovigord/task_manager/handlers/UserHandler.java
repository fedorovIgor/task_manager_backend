package com.fedorovigord.task_manager.handlers;

import com.fedorovigord.task_manager.model.project.Project;
import com.fedorovigord.task_manager.model.project.Task;
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

    public Mono<ServerResponse> getUserById(ServerRequest req) {
        final Integer userId = Integer.parseInt(req.pathVariable("userId"));
        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(userService.getUserById(userId), User.class);
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest req) {
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(userService.getAllUsers(), User.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest req) {
        final var user = userService.createUser(req.bodyToMono(User.class));

        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(user, User.class);
    }

    public Mono<ServerResponse> updateUser(ServerRequest req) {
        final var user = userService.updateUser(req.bodyToMono(User.class));

        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(user, User.class);
    }
}
