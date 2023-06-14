package com.fedorovigord.task_manager.handlers;

import com.fedorovigord.task_manager.model.project.Project;
import com.fedorovigord.task_manager.model.project.Task;
import com.fedorovigord.task_manager.model.project.entity.TaskEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@EnableAutoConfiguration
@ActiveProfiles("test")
public class TaskHandlerTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    R2dbcEntityTemplate r2dbcEntityTemplate;

    @Autowired
    WebTestClient webClient;

    @BeforeEach
    public void setupWebTestClient() {

        var project = new Project(1,null,"TEST_PROJECT", "DESCRIPTION", "ACTIVE");

        r2dbcEntityTemplate.delete(Project.class)
                .from("task")
                .all()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

        r2dbcEntityTemplate.delete(Project.class)
                .from("project")
                .all()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

        r2dbcEntityTemplate.insert(Project.class)
                .using(project)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

        r2dbcEntityTemplate.insert(TaskEntity.class)
                .using(new TaskEntity(new Task(1,"TEST_TASK", "1", project)))
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

        webClient = WebTestClient.bindToApplicationContext(context)
                .apply(springSecurity())
                .configureClient()
                .build();
    }


    @Test
    @WithMockUser(username= "admin", authorities = {"ROLE_ADMIN", "ROLE_USER"})
    void getTaskById_shouldReturnTask_Test() {

        webClient
                .get()
                .uri("/api/v1/task/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Task.class)
                .hasSize(1);
    }

    @Test
    @WithMockUser(username= "admin", authorities = {"ROLE_ADMIN", "ROLE_USER"})
    void getTaskById_shouldReturn400_Test() {

        webClient
                .get()
                .uri("/api/v1/task/10")
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Test
    @WithMockUser(username= "admin", authorities = {"ROLE_ADMIN", "ROLE_USER"})
    void getTaskByProjectId_shouldReturnFlux_Test() {

        webClient
                .get()
                .uri("/api/v1/project/1/task")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Task.class)
                .hasSize(1);
    }

    @Test
    @WithMockUser(username= "admin", authorities = {"ROLE_ADMIN", "ROLE_USER"})
    void getTaskByProjectId_shouldReturnEmptyListOk_Test() {

        webClient
                .get()
                .uri("/api/v1/project/10/task")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Task.class)
                .hasSize(0);
    }

    @Test
    @WithMockUser(username= "1", authorities = {"ROLE_ADMIN", "ROLE_USER"})
    void getTaskByUserId_shouldReturnList_Test() {

        webClient
                .get()
                .uri("/api/v1/user/task")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Task.class)
                .hasSize(1);
    }

    @Test
    @WithMockUser(username= "1", authorities = {"ROLE_ADMIN", "ROLE_USER"})
    void postTask_shouldCreateTask_Test() {
        var testTask = new Task(0,
                "SECOND_TASK",
                "1",
                new Project(1,null,"TEST_PROJECT", "DESCRIPTION", "ACTIVE"));

        webClient
                .post()
                .uri("/api/v1/project/1/task")
                .body(Mono.just(testTask), Task.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    @WithMockUser(username= "1", authorities = {"ROLE_ADMIN", "ROLE_USER"})
    void postTaskWithIncorrectProjectId_shouldReturn400_Test() {
        var testTask = new Task(0,
                "SECOND_TASK",
                "1",
                new Project(1,null,"TEST_PROJECT", "DESCRIPTION", "ACTIVE"));

        webClient
                .post()
                .uri("/api/v1/project/2/task")
                .body(Mono.just(testTask), Task.class)
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }
}
