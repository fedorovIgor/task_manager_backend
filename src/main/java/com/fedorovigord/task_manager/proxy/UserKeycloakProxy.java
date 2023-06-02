package com.fedorovigord.task_manager.proxy;

import com.fedorovigord.task_manager.model.user.User;
import com.fedorovigord.task_manager.model.user.dto.RoleDto;
import com.fedorovigord.task_manager.model.user.dto.UserRequestDto;
import com.fedorovigord.task_manager.model.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.management.relation.Role;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserKeycloakProxy {

    private final WebClient webClient;

    public Flux<User> getUserList() {
        String GET_USERS_URI = "http://localhost:28080/auth/admin/realms/task_manager/users";

        return webClient.get()
                .uri(GET_USERS_URI)
                .retrieve()
                .bodyToFlux(UserResponseDto.class)
                .map(User::new);
    }

    public Flux<RoleDto> getAllRoles() {
        String GET_ROLES_URI = "http://localhost:28080/auth/admin/realms/task_manager/roles";

        return webClient.get()
                .uri(GET_ROLES_URI)
                .retrieve()
                .bodyToFlux(RoleDto.class);
    }

    public Flux<String> getUserRoles(String userKeycloakId) {
        String GET_USER_ROLES = "http://localhost:28080/auth/admin/realms/task_manager/users/" + userKeycloakId + "/role-mappings/realm";

        return webClient.get()
                .uri(GET_USER_ROLES)
                .retrieve()
                .bodyToFlux(RoleDto.class)
                .map(r -> r.getName());
    }

    public Mono<String> createUser(UserRequestDto user) {
        String POST_USER = "http://localhost:28080/auth/admin/realms/task_manager/users";

        return webClient.post()
                .uri(POST_USER)
                .body(Mono.just(user), UserRequestDto.class)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> addRoleToUser(String keycloakUserId, RoleDto role) {
        String POST_ROLE_TO_USER = "http://localhost:28080/auth/admin/realms/task_manager/users/" + keycloakUserId + "/role-mappings/realm";

        return webClient.post()
                .uri(POST_ROLE_TO_USER)
                .body(Mono.just(role), RoleDto.class)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> deleteUserRole(String keycloakUserId, RoleDto role) {
        String DELETE_ROLE_USER = "http://localhost:28080/auth/admin/realms/task_manager/users/" + keycloakUserId + "/role-mappings/realm";

        return webClient.method(HttpMethod.DELETE)
                .uri(DELETE_ROLE_USER)
                .body(Mono.just(role), RoleDto.class)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Flux<RoleDto> getRoles() {
        String GET_ROLES = "http://localhost:28080/auth/admin/realms/task_manager/roles";

        return webClient.get()
                .uri(GET_ROLES)
                .retrieve()
                .bodyToFlux(RoleDto.class);
    }

}
