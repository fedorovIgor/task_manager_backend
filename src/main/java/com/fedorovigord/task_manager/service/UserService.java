package com.fedorovigord.task_manager.service;

import com.fedorovigord.task_manager.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<User> getAllUsers();
    Flux<String> getAllRoles();
    Flux<String> getUserRoles(String keycloakUserId);
    Flux<User> getAllUsersWithRoles();
    Mono<String> createUser(Mono<User> user);
    Mono<User> addRoleToUser(Mono<User> user);
    Mono<User> deleteRoleUser(Mono<User> user);
}
