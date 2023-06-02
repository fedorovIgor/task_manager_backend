package com.fedorovigord.task_manager.model.user.dto;

import com.fedorovigord.task_manager.model.user.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserRequestDto {
    private boolean enabled;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<Credential> credentials = new ArrayList<>();

    @Data
    public class Credential {
        private String type;
        private String value;
        private boolean temporary;
    }

    public UserRequestDto(User user) {
        this.enabled = true;
        this.username = user.getFirstName();
        this.email = user.getEmail();

        var credential = new Credential();
        credential.temporary = true;
        credential.value = user.getPassword();
        credential.type = "password";

        this.credentials.add(credential);
    }
}
