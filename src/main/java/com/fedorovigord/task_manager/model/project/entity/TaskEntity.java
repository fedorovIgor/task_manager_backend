package com.fedorovigord.task_manager.model.project.entity;

import com.fedorovigord.task_manager.model.project.Task;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")
public class TaskEntity {

    @Id
    private int id;
    private String header;
    private String description;
    private String linkInfo;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private int projectIdFk;
    private int userIdFk;

    public TaskEntity(Task task) {
        this.id = task.getId();
        this.header = task.getHeader();
        this.description = task.getHeader();
        this.linkInfo = task.getLinkInfo();
        this.status = task.getStatus();
        this.startTime = task.getStartData();
        this.finishTime = task.getFinishData();

        if (task.getProject() != null)
            this.projectIdFk = task.getProject().getId();
        if (task.getUser() != null)
            this.userIdFk = task.getUser().getId();
    }
}