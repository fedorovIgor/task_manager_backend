package com.fedorovigord.task_manager.service;

import com.fedorovigord.task_manager.model.project.Project;
import com.fedorovigord.task_manager.model.project.ProjectInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectService {
    Flux<Project> getAll();
    Mono<Project> createProject(Mono<Project> project);
    Mono<Project> updateProject(Mono<Project> project);
    Mono<ProjectInfo> getProjectInfo(Integer projectId);
    Mono<Project> getProjectById(Integer projectId);
}
