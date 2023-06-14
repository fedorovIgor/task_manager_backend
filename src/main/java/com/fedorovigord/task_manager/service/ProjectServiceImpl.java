package com.fedorovigord.task_manager.service;

import com.fedorovigord.task_manager.exception.TaskIncorrectException;
import lombok.extern.slf4j.Slf4j;
import com.fedorovigord.task_manager.exception.ProjectIncorrectException;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;


    public Flux<Project> getAll() {

        return projectRepository.findAll()
                .map(Project::new);
    }

    public Mono<Project> createProject(Mono<Project> project) {

        return project
                .flatMap(this::checkProjectName)
                .map(ProjectEntity::new)
                .flatMap(p -> {
                    //check if already exist by given name,
                    //if not than save
                    Mono<ProjectEntity> result = projectRepository.findByName(p.getName())
                            .flatMap(this::throwIfExist)
                            .switchIfEmpty(projectRepository.save(p));
                    return result;
                })
                .map(Project::new);
    }

    private Mono<ProjectEntity> throwIfExist(ProjectEntity projectEntity) {
        return Mono.error(new ProjectIncorrectException("project with that name already exist: "
                        + projectEntity.getName()));
    }

    private Mono<Project> checkProjectName(Project p) {
        if (p == null || p.getName() == null || p.getName().isEmpty())
            return Mono.error(new ProjectIncorrectException("project name is incorrect"));

        return Mono.just(p);
    }

    public Mono<Project> updateProject(Mono<Project> project) {
        return project
                .map(ProjectEntity::new)
                .flatMap(projectEntity -> {
                    //check if already exist by given id,
                    // if not than Throw,
                    // if exist update
                    return projectRepository.findById(projectEntity.getId())
                            .flatMap(pr -> projectRepository.save(projectEntity))
                            .switchIfEmpty(Mono.error(new ProjectIncorrectException("Task with id not found: " + projectEntity.getId())));
                })
                .map(Project::new);
    }

    // method is blocked until all tasks arrives
    // use two call - to getProjectById and getTasksByProjectId
    public Mono<ProjectInfo> getProjectInfo(Integer projectId) {
        //search project by id, then search list of tasks and combine

        var projectInfo = projectRepository.findById(projectId)
                .map(ProjectInfo::new);

        var tasks = taskRepository.findByProjectId(projectId)
                .map(Task::new)
                .collectList();

        return Mono.zip(projectInfo, tasks,
                    (p,t) -> {
                            p.setTasks(t);
                            return p;
                    });
    }

    public Mono<Project> getProjectById(Integer projectId) {
        return projectRepository.findById(projectId)
                .switchIfEmpty(Mono.error(new ProjectIncorrectException("no project by id: " + projectId)))
                .map(Project::new);
    }
}
