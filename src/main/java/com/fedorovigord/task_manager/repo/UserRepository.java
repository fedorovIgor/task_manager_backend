package com.fedorovigord.task_manager.repo;

import com.fedorovigord.task_manager.model.user.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Integer> {
}
