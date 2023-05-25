package com.fedorovigord.task_manager.config;

import com.fedorovigord.task_manager.handlers.ProjectHandler;
import com.fedorovigord.task_manager.handlers.TaskHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutersConfig {

    @Bean
    public RouterFunction<ServerResponse> projectRouter(ProjectHandler projectHandler) {
        return route()
                .GET("/api/v1/project", projectHandler::getAll)
                .POST("/api/v1/project", projectHandler::createProject)
                .PUT("/api/v1/project", projectHandler::updateProject)

                .GET( "/api/v1/project/{projectId}", projectHandler::getProjectInfo)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> taskRouter(TaskHandler taskHandler) {
        return route()
                .GET("/api/v1/task/{taskId}", taskHandler::getBYId)
                .POST("/api/v1/task", taskHandler::createTask)
                .PUT("/api/v1/task", taskHandler::updateTask)
                .build();

    }
}