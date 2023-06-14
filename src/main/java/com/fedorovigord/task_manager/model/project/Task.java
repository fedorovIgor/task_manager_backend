package com.fedorovigord.task_manager.model.project;

import com.fedorovigord.task_manager.model.project.entity.TaskEntity;
import com.fedorovigord.task_manager.model.user.User;
import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Getter @Setter
public class Task {
    private int id;
    private String header;
    private String linkInfo;
    private String status;
    private String description;
    private LocalDateTime startData;
    private LocalDateTime finishData;
    private String userKeycloakId;
    private Project project;
    private String userName;
    private String userEmail;

    public Task() {
    }

    public Task(int id, String header,String userKeycloakId, Project project) {
        this.id = id;
        this.header = header;
        this.project = project;
        this.userKeycloakId = userKeycloakId;
    }

    public Task(TaskEntity taskEntity) {
        this.id = taskEntity.getId();
        this.header = taskEntity.getHeader();
        this.linkInfo = taskEntity.getLinkInfo();
        this.status = taskEntity.getStatus();
        this.startData = taskEntity.getStartTime();
        this.finishData = taskEntity.getFinishTime();
        this.description = taskEntity.getDescription();
        this.userKeycloakId = taskEntity.getUserKeycloakId();
    }
}
