package com.fedorovigord.task_manager.handlers;

import com.fedorovigord.task_manager.model.project.Project;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@EnableAutoConfiguration
@ActiveProfiles("test")
class ProjectHandlerTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    R2dbcEntityTemplate r2dbcEntityTemplate;

    @Autowired
    WebTestClient webClient;

    @BeforeEach
    public void setupWebTestClient() {

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
                .using(new Project(1,null,"TEST_PROJECT", "DESCRIPTION", "ACTIVE"))
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
    void getAllProjectTest() {

        webClient
                .get()
                .uri("/api/v1/project")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Project.class)
                .hasSize(1);
    }

    @Test
    @WithMockUser(username= "admin", authorities = {"ROLE_ADMIN", "ROLE_USER"})
    void getProjectById_shouldReturnProject() {
        webClient
                .get()
                .uri("/api/v1/project/1")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Project.class);
    }

    @Test
    @WithMockUser(username= "admin", authorities = {"ROLE_ADMIN", "ROLE_USER"})
    void getProjectById_return404() {
        webClient
                .get()
                .uri("/api/v1/project/10")
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Test
    @WithMockUser(username= "admin", authorities = {"ROLE_ADMIN", "ROLE_USER"})
    void createProject_shouldReturnCreate() {
        var testProject = Mono.just(new Project(1,null,"TEST_PROJECT_NEW", "DESCRIPTION", "ACTIVE"));

        webClient
                .post()
                .uri("/api/v1/project")
                .body(testProject, Project.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    @WithMockUser(username= "admin", authorities = {"ROLE_ADMIN", "ROLE_USER"})
    void createProjectWithNullName_shouldCreate400Exception() {
        var testProject = Mono.just(new Project(0,null,null, "DESCRIPTION", "ACTIVE"));

        webClient
                .post()
                .uri("/api/v1/project")
                .body(testProject, Project.class)
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Test
    @WithMockUser(username= "admin", authorities = {"ROLE_ADMIN", "ROLE_USER"})
    void createProjectWithExistName_shouldReturn400Exception() {
        var testProject = Mono.just(new Project(0,null,"TEST_PROJECT", "DESCRIPTION", "ACTIVE"));

        webClient
                .post()
                .uri("/api/v1/project")
                .body(testProject, Project.class)
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }


}