package com.fedorovigord.task_manager.model.project;

import com.fedorovigord.task_manager.model.project.entity.TaskEntity;
import com.fedorovigord.task_manager.model.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class Task {
    private int id;
    private String header;
    private String linkInfo;
    private String status;
    private LocalDateTime startData;
    private LocalDateTime finishData;
    private User user;

    public Task() {
    }

    public Task(TaskEntity taskEntity) {
        this.id = taskEntity.getId();
        this.header = taskEntity.getHeader();
        this.linkInfo = taskEntity.getLinkInfo();
        this.status = taskEntity.getStatus();
        this.startData = taskEntity.getStartTime();
        this.finishData = taskEntity.getFinishTime();
    }
}
