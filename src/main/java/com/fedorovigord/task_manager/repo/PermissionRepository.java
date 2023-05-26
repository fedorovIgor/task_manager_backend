package com.fedorovigord.task_manager.repo;

import com.fedorovigord.task_manager.model.user.entity.PermissionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PermissionRepository extends ReactiveCrudRepository<PermissionEntity, Integer> {


}
