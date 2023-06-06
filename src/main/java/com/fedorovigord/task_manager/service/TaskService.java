package com.fedorovigord.task_manager.service;

import com.fedorovigord.task_manager.model.project.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {
    Mono<Task> getTaskById (Integer taskId);
    Flux<Task> getTasksByProjectId(int projectId);
    Flux<Task> getTasksByUserId(Mono<String> userKeycloakId);
    Mono<Task> updateTask(Mono<Task> task);
    Mono<Task> getTaskToWork(Mono<Task> task, Mono<String> userKeycloakId);
    Mono<Task> getTaskToClose(Mono<Task> task);
    Mono<Task> saveTask(Mono<Task> task, Integer projectId);
}
