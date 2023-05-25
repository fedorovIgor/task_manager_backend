package com.fedorovigord.task_manager.service;

import com.fedorovigord.task_manager.model.project.Project;
import com.fedorovigord.task_manager.model.project.ProjectInfo;
import com.fedorovigord.task_manager.model.project.Task;
import com.fedorovigord.task_manager.model.project.entity.ProjectEntity;
import com.fedorovigord.task_manager.repo.ProjectRepository;
import com.fedorovigord.task_manager.repo.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;



    public Flux<Project> getAll() {

        return projectRepository.findAll()
                .map(Project::new);
    }

    public Mono<Project> createProject(Mono<Project> project) {

        return project
                .map(ProjectEntity::new)
                .flatMap(p -> {
                    //check if already exist by given name,
                    //if not than save
                    return projectRepository.findByName(p.getName())
                            .switchIfEmpty(projectRepository.save(p));
                })
                .map(Project::new);
    }

    public Mono<Project> updateProject(Mono<Project> project) {
        return project
                .map(ProjectEntity::new)
                .flatMap(projectEntity -> {
                    //check if already exist by given name,
                    // if not than search by id,
                    // if exist update
                    return projectRepository.findByName(projectEntity.getName())
                            .switchIfEmpty(projectRepository.findById(projectEntity.getId())
                                    .flatMap(pr -> projectRepository.save(projectEntity)));
                })
                .map(Project::new);
    }

    public Mono<ProjectInfo> getProjectInfo(Integer projectId) {
        //search project by id, then search list of tasks and combine
        return Mono
                .zip(projectRepository.findById(projectId)
                            .map(ProjectInfo::new),
                    taskRepository.findByProjectId(projectId)
                            .map(Task::new)
                            .collectList(),
                    (p,t) -> {
                            p.setTasks(t);
                            return p;
                    });
    }
}
