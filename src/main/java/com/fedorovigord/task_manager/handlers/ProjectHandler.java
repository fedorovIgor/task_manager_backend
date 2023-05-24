package com.fedorovigord.task_manager.handlers;

import com.fedorovigord.task_manager.model.project.Project;
import com.fedorovigord.task_manager.model.project.ProjectInfo;
import com.fedorovigord.task_manager.service.ProjectService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ProjectHandler {

    private final ProjectService projectService;

    public ProjectHandler(ProjectService projectService) {
        this.projectService = projectService;
    }

    public Mono<ServerResponse> getAll(ServerRequest req) {
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(projectService.getAll(), Project.class);
    }

    public Mono<ServerResponse> createProject(ServerRequest req) {
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(projectService.createProject(req.bodyToMono(Project.class)), Project.class);
    }

    public Mono<ServerResponse> updateProject(ServerRequest req) {
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(projectService.updateProject(req.bodyToMono(Project.class)), Project.class);
    }

    public Mono<ServerResponse> getProjectInfo(ServerRequest req) {
        final Integer projectId = Integer.parseInt(req.pathVariable("projectId"));

        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(projectService.getProjectInfo(projectId), ProjectInfo.class);
    }
}
