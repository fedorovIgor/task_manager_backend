package com.fedorovigord.task_manager.repo;

import com.fedorovigord.task_manager.model.project.entity.ProjectEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProjectRepository extends ReactiveCrudRepository<ProjectEntity, Integer> {

    @Query("select * from project where name = $1")
    Mono<ProjectEntity> findByName(String name);

}
