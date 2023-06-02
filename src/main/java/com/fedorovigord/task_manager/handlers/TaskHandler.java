package com.fedorovigord.task_manager.handlers;

import com.fedorovigord.task_manager.model.project.Task;
import com.fedorovigord.task_manager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class TaskHandler {

    private final TaskService taskService;

    public Mono<ServerResponse> createTask(ServerRequest req) {
        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(taskService.saveTask(req.bodyToMono(Task.class)), Task.class);
    }

    public Mono<ServerResponse> getBYId(ServerRequest req) {
        final Integer taskId = Integer.parseInt(req.pathVariable("taskId"));
        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(taskService.getTaskById(taskId), Task.class);
    }

    public Mono<ServerResponse> getByProjectId(ServerRequest req) {
        final int projectId = Integer.parseInt(req.pathVariable("projectId"));

        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(taskService.getTasksByProjectId(projectId), Task.class);
    }

    public Mono<ServerResponse> getTasksByUserId(ServerRequest req) {
        final String projectId = req.pathVariable("userId");

        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(taskService.getTasksByUserId(projectId), Task.class);
    }

    public Mono<ServerResponse> updateTask(ServerRequest req) {
        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(taskService.updateTask(req.bodyToMono(Task.class)), Task.class);
    }
}
//        return ReactiveSecurityContextHolder.getContext()
//                .map(sc -> sc.getAuthentication())
//                .map(Authentication::getName)
//                .doOnNext(System.out::println)
//                .flatMap(s -> ServerResponse.ok()
//                        .contentType(MediaType.TEXT_EVENT_STREAM)
//                        .body(userService.getAllUsers(), User.class)
//                        );

