package com.fedorovigord.task_manager.model.user.dto;

import lombok.Data;

@Data
public class RoleDto {
    private String id;
    private String name;
    private String description;
    private boolean composite;
    private boolean clientRole;
    private String containerId;
}
