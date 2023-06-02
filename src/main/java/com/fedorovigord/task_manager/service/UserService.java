package com.fedorovigord.task_manager.service;

import com.fedorovigord.task_manager.model.user.User;
import com.fedorovigord.task_manager.model.user.dto.UserRequestDto;
import com.fedorovigord.task_manager.proxy.UserKeycloakProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserKeycloakProxy userProxy;

    public Flux<User> getAllUsers() {
        return userProxy.getUserList();
    }

    public Flux<String> getAllRoles() {
        return userProxy.getAllRoles()
                .map(r -> r.getName());
    }

    public Flux<String> getUserRoles(String keycloakUserId) {
        return userProxy.getUserRoles(keycloakUserId);
    }

    public Flux<User> getAllUsersWithRoles() {

        return userProxy.getUserList()
                .flatMap(user -> {
                    var roles = userProxy.getUserRoles(user.getKeycloakId())
                            .collectList();
                    var justUser = Mono.just(user);

                    return Mono.zip(justUser, roles,
                            (u,r) -> {
                                u.setRoles(r);
                                return u;
                            });
                });
    }


    public Mono<String> createUser(Mono<User> user) {
        return user
                .map(UserRequestDto::new)
                .flatMap(userProxy::createUser);

    }

    //TODO not call method
    public Mono<User> addRoleToUser(Mono<User> user) {
        return user.doOnNext(u -> {
                    userProxy.getRoles()
                            .filter(r -> u.getRoles().contains(r.getName()))
                            .map(r -> userProxy.addRoleToUser(u.getKeycloakId(), r));
                });
    }

    //TODO not call method
    public Mono<User> deleteRoleUser(Mono<User> user) {
        return user
                .doOnNext(u -> {
                    userProxy.getRoles()
                            .filter(r -> u.getRoles().contains(r.getName()))
                            .map(r -> userProxy.deleteUserRole(u.getKeycloakId(), r));
                });
    }
}
