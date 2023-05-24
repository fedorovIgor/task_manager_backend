package com.fedorovigord.task_manager.handlers;

import com.fedorovigord.task_manager.config.RoutersConfig;
import org.junit.runner.RunWith;
import com.fedorovigord.task_manager.model.project.Project;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RoutersConfig.class, ProjectHandler.class})
@WebFluxTest
class ProjectHandlerTest {

}