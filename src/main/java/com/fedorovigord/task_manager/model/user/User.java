package com.fedorovigord.task_manager.model.user;

import com.fedorovigord.task_manager.model.user.dto.UserResponseDto;
import com.fedorovigord.task_manager.model.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String password;
    private String keycloakId;
    private String email;
    private List<String> roles;

    public User(UserEntity user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.keycloakId = user.getKeycloakId();
    }

    public User(UserResponseDto userResponseDto) {
        this.keycloakId = userResponseDto.getId();
        this.firstName = userResponseDto.getFirstName();
        this.lastName = userResponseDto.getLastName();
        this.email = userResponseDto.getEmail();
    }
}
