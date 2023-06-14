package com.fedorovigord.task_manager.service;

import com.fedorovigord.task_manager.exception.TaskIncorrectException;
import com.fedorovigord.task_manager.model.project.Task;
import com.fedorovigord.task_manager.model.project.entity.TaskEntity;
import com.fedorovigord.task_manager.model.user.dto.UserResponseDto;
import com.fedorovigord.task_manager.proxy.UserProxy;
import com.fedorovigord.task_manager.repo.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserProxy keycloakProxy;

    public Mono<Task> getTaskById (Integer taskId) {

        return taskRepository.findById(taskId)
                .map(Task::new)
                .switchIfEmpty(Mono.error(new TaskIncorrectException("can`t find task by id " + taskId)));
    }

    public Flux<Task> getTasksByProjectId(int projectId) {
        return taskRepository.findByProjectId(projectId)
                .delayElements(Duration.ofSeconds(2))
                .map(Task::new)
                .flatMap(task -> {
                    var user = keycloakProxy.getUserById(task.getUserKeycloakId())
                            .onErrorReturn(new UserResponseDto());

                    return Mono.zip(Mono.just(task), user, (t,u) -> {
                        t.setUserName(u.getUsername());
                        t.setUserEmail(u.getEmail());
                        return t;
                    });
                });
    }

    public Flux<Task> getTasksByUserId(Mono<String> userKeycloakId) {
        return userKeycloakId
                .flatMapMany(taskRepository::findByUserId)
                .map(Task::new);
    }

    public Mono<Task> updateTask(Mono<Task> task) {
        return task
                .map(TaskEntity::new)
                .flatMap(t -> {
                    return taskRepository.findById(t.getId())
                            .flatMap(ts ->{
                                t.setProjectIdFk(ts.getProjectIdFk());
                                t.setUserKeycloakId(ts.getUserKeycloakId());
                                t.setStartTime(ts.getStartTime());
                                t.setFinishTime(ts.getFinishTime());
                                return taskRepository.save(t);
                            })
                            .switchIfEmpty(Mono.error(new TaskIncorrectException("can`t find task by id: " + t.getId())));
                })
                .map(Task::new);
    }

    public Mono<Task> updateTaskToWork(Mono<Task> task, Mono<String> userKeycloakId) {

        return Mono
                .zip(task, userKeycloakId, (t,id) -> {
                        t.setUserKeycloakId(id);
                        return t;
                })
                .map(TaskEntity::new)
                .flatMap(taskEntity -> {
                    return taskRepository.findById(taskEntity.getId())
                            .flatMap(t -> {
                                taskEntity.setProjectIdFk(t.getProjectIdFk());
                                taskEntity.setStatus("STARTED");
                                taskEntity.setStartTime(LocalDateTime.now());
                                return taskRepository.save(taskEntity);
                            });
                })
                .map(Task::new);
    }

    public Mono<Task> updateTaskToClose(Mono<Task> task) {
        return task
                .map(Task::getId)
                .flatMap(id -> {
                    return taskRepository.findById(id)
                            .flatMap(t -> {
                                t.setStatus("COMPLETE");
                                t.setFinishTime(LocalDateTime.now());
                                return taskRepository.save(t);
                            });
                })
                .map(Task::new);
    }

    public Mono<Task> saveTask(Mono<Task> task, Integer projectId) {
        return task
                .map(TaskEntity::new)
                .doOnNext(t -> t.setProjectIdFk(projectId))
                .flatMap(t -> taskRepository.save(t))
                .map(Task::new)
                .onErrorResume(DataIntegrityViolationException.class, e -> Mono.error(new TaskIncorrectException("Can't find project by id: " + projectId)));
    }
}
