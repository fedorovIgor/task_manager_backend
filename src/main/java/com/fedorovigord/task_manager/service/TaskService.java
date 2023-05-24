package com.fedorovigord.task_manager.service;

import com.fedorovigord.task_manager.model.project.Task;
import com.fedorovigord.task_manager.model.project.entity.TaskEntity;
import com.fedorovigord.task_manager.repo.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Mono<Task> getTaskById (Integer taskId) {
        return taskRepository.findById(taskId)
                .map(Task::new);
    }

    public Mono<Task> updateTask(Mono<Task> task) {
        return task
                .map(TaskEntity::new)
                .flatMap(t -> {
                    return taskRepository.findById(t.getId())
                            .flatMap(ts -> taskRepository.save(t));
                })
                .map(Task::new);
    }

    public Mono<Task> saveTask(Mono<Task> task) {
        return task
                .map(TaskEntity::new)
                .flatMap(t -> taskRepository.save(t))
                .map(Task::new);
    }
}
