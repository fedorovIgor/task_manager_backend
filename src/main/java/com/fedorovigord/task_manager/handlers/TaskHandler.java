package com.fedorovigord.task_manager.handlers;

import com.fedorovigord.task_manager.exception.TaskIncorrectException;
import com.fedorovigord.task_manager.model.project.Task;
import com.fedorovigord.task_manager.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskHandler {

    private final TaskService taskService;

    public Mono<ServerResponse> createTask(ServerRequest req) {
        final var projectId = Integer.parseInt(req.pathVariable("projectId"));
        final var responseBody = taskService.saveTask(req.bodyToMono(Task.class) ,projectId);

        return responseBody
                .flatMap(task -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .bodyValue(task))
                .doOnError(error -> log.error(error.getMessage() + "\n" + req))
                .onErrorResume(TaskIncorrectException.class, error -> ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> getBYId(ServerRequest req) {
        final Integer taskId = Integer.parseInt(req.pathVariable("taskId"));
        final var responseBody = taskService.getTaskById(taskId);

        return responseBody
                .flatMap(task -> ok()
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .bodyValue(task))
                .doOnError(error -> log.error(error.getMessage() + "\n" + req))
                .onErrorResume(TaskIncorrectException.class, error -> ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> getByProjectId(ServerRequest req) {
        final int projectId = Integer.parseInt(req.pathVariable("projectId"));
        final var responseBody = taskService.getTasksByProjectId(projectId);

        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseBody, Task.class);
    }

    public Mono<ServerResponse> getTasksByUserId(ServerRequest req) {
        final var userId = ReactiveSecurityContextHolder.getContext()
                .map(sc -> sc.getAuthentication())
                .map(Authentication::getName);

        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(taskService.getTasksByUserId(userId), Task.class);
    }

    public Mono<ServerResponse> updateTask(ServerRequest req) {
        final var taskRequest = req.bodyToMono(Task.class);
        final var responseBody = taskService.updateTask(taskRequest);

        return responseBody
                .flatMap(task -> ok()
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .bodyValue(task))
                .doOnError(error -> log.error(error.getMessage() + "\n" + req))
                .onErrorResume(TaskIncorrectException.class, error -> ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> getTaskToWork(ServerRequest req) {
        var userKeycloakId = ReactiveSecurityContextHolder.getContext()
                .map(sc -> sc.getAuthentication())
                .map(Authentication::getName);

        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(taskService.updateTaskToWork(req.bodyToMono(Task.class), userKeycloakId), Task.class);
    }

    public Mono<ServerResponse> getTaskToClose(ServerRequest req) {
        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(taskService.updateTaskToClose(req.bodyToMono(Task.class)), Task.class);
    }
}

