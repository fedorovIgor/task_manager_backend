package com.fedorovigord.task_manager.config;

import com.fedorovigord.task_manager.handlers.ProjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutersConfig {

    @Bean
    public RouterFunction<ServerResponse> router(ProjectHandler projectHandler) {
        return route()
                .GET("/project", projectHandler::getAll)
                .POST("/project", projectHandler::createProject)
                .PUT("/project", projectHandler::updateProject)

                .GET( "/project/{projectId}", projectHandler::getProjectInfo)
                .build();
    }
}