package com.fedorovigord.task_manager.handlers;

import com.fedorovigord.task_manager.exception.ProjectIncorrectException;
import com.fedorovigord.task_manager.model.project.Project;
import com.fedorovigord.task_manager.service.ProjectService;
import com.fedorovigord.task_manager.service.ProjectServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ProjectHandler {

    private final ProjectService projectService;

    public ProjectHandler(ProjectServiceImpl projectServiceImpl) {
        this.projectService = projectServiceImpl;
    }

    public Mono<ServerResponse> getAll(ServerRequest req) {
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(projectService.getAll(), Project.class);
    }

    public Mono<ServerResponse> createProject(ServerRequest req) {
        var requestBody = req.bodyToMono(Project.class);
        var responseBody = projectService.createProject(requestBody);

        return responseBody
                .flatMap(project -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .bodyValue(project))
                .doOnError(error -> log.error(error.getMessage() + "\n" + req))
                .onErrorResume(ProjectIncorrectException.class, error -> ServerResponse.badRequest().build());

    }

    public Mono<ServerResponse> updateProject(ServerRequest req) {
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(projectService.updateProject(req.bodyToMono(Project.class)), Project.class);
    }

    public Mono<ServerResponse> getProjectById(ServerRequest req) {
        final Integer projectId = Integer.parseInt(req.pathVariable("projectId"));
        final var project = projectService.getProjectById(projectId);

        return project
                .flatMap(p -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .bodyValue(p))
                .doOnError(error -> log.error(error.getMessage() + req))
                .onErrorResume(ProjectIncorrectException.class, error -> ServerResponse.badRequest().build());
    }
}
