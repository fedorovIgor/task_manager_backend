package com.fedorovigord.task_manager.model.project;

import com.fedorovigord.task_manager.model.project.entity.ProjectEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
public class Project {
    private int id;
    private LocalDateTime startDate;
    private String name;
    private String description;
    private String status;

    public Project() {
    }

    public Project(LocalDateTime startDate, String name, String description, String status) {
        this.startDate = startDate;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Project(ProjectEntity entity) {
        this.id = entity.getId();
        this.startDate = entity.getStartDate();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.status = entity.getStatus();
    }
}

