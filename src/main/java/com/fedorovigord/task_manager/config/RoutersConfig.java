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
                .GET("/project", projectHandler::getAll)
                .POST("/project", projectHandler::createProject)
                .PUT("/project", projectHandler::updateProject)

                .GET( "/project/{projectId}", projectHandler::getProjectInfo)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> taskRouter(TaskHandler taskHandler) {
        return route()
                .GET("task/{taskId}", taskHandler::getBYId)
                .POST("task", taskHandler::createTask)
                .PUT("task", taskHandler::updateTask)
                .build();

    }
}