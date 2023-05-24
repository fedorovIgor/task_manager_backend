package com.fedorovigord.task_manager.model.project;

import com.fedorovigord.task_manager.model.project.entity.ProjectEntity;
import com.fedorovigord.task_manager.model.project.entity.TaskEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ProjectInfo {
    private int id;
    private String name;
    private String fullDescription;
    private List<Task> tasks = new ArrayList<>();

    public ProjectInfo(ProjectEntity projectEntity) {
        this.id = projectEntity.getId();
        this.name = projectEntity.getName();
        this.fullDescription = projectEntity.getDescription();
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }
}
