package com.fedorovigord.task_manager.model.user;

import com.fedorovigord.task_manager.model.user.entity.PermissionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    private int id;
    private String permission;
    private boolean isActive;

    public Permission (PermissionEntity permission) {
        this.id = permission.getId();
        this.permission = permission.getName();
    }
}