package com.fedorovigord.task_manager.model.project.entity;

import com.fedorovigord.task_manager.model.project.Task;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    private int id;
    private String header;
    private String description;
    private String linkInfo;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private int projectIdFk;

    public TaskEntity(Task task) {
        this.id = task.getId();
        this.header = task.getHeader();
        this.description = task.getHeader();
        this.linkInfo = task.getLinkInfo();
        this.status = task.getStatus();
        this.startTime = task.getStartData();
        this.finishTime = task.getFinishData();
    }
}