package com.fedorovigord.task_manager.repo;

import com.fedorovigord.task_manager.model.project.entity.ProjectEntity;
import com.fedorovigord.task_manager.model.project.entity.TaskEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskRepository extends ReactiveCrudRepository<TaskEntity, Integer> {

    @Query("select * from task where project_id_fk = $1")
    Flux<TaskEntity> findByProjectId(int id);
}
