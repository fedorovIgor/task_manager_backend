package com.fedorovigord.task_manager.proxy;

import com.fedorovigord.task_manager.model.user.User;
import com.fedorovigord.task_manager.model.user.dto.RoleDto;
import com.fedorovigord.task_manager.model.user.dto.UserRequestDto;
import com.fedorovigord.task_manager.model.user.dto.UserResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserProxy {

    Flux<User> getUserList();
    Mono<UserResponseDto> getUserById(String userId);
    Flux<RoleDto> getAllRoles();
    Flux<String> getUserRoles(String userKeycloakId);
    Mono<String> createUser(UserRequestDto user);
    Mono<String> addRoleToUser(String keycloakUserId, RoleDto role);
    Mono<String> deleteUserRole(String keycloakUserId, RoleDto role);
    Flux<RoleDto> getRoles();

}
