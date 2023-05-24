package com.fedorovigord.task_manager.model.project.entity;

import com.fedorovigord.task_manager.model.project.Project;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Getter @Setter
@Table(name = "project")
public class ProjectEntity {

    @Id
    private int id;
    private LocalDateTime startDate;
    private String name;
    private String description;
    private String status;

    public ProjectEntity() {
    }

    public ProjectEntity(Project p) {
        this.id = p.getId();
        this.startDate = p.getStartDate();
        this.name = p.getName();
        this.description = p.getDescription();
        this.status = p.getStatus();
    }
}
