package com.fedorovigord.task_manager.service;

import com.fedorovigord.task_manager.model.user.User;
import com.fedorovigord.task_manager.repo.PermissionRepository;
import com.fedorovigord.task_manager.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    //TODO realize method
    public Mono<User> getUserById(Integer id) {
        return Mono.just(new User());
    }

    //TODO realize method
    public Flux<User> getAllUsers() {
        return Flux.just(new User());
    }

    //TODO realize method
    public Mono<User> createUser(Mono<User> user) {
        return user;
    }

    //TODO realize method
    public Mono<User> updateUser(Mono<User> user) {
        return user;
    }
}
