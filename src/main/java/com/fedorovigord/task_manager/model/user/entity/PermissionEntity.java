package com.fedorovigord.task_manager.model.user.entity;

import com.fedorovigord.task_manager.model.user.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permission")
public class PermissionEntity {

    @Id
    private int id;
    private String name;

    public PermissionEntity (Permission permission) {
        this.id = permission.getId();
        this.name = permission.getPermission();
    }
}
