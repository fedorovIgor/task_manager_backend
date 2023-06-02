package com.fedorovigord.task_manager;

import com.fedorovigord.task_manager.model.user.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
class TaskManagerApplicationTests {

	@Autowired
	private  WebClient webClient;
	@Test
	void contextLoads() {
		var t = webClient.get()
				.uri("http://localhost:28080/auth/admin/realms/task_manager/users")
				.retrieve()
				.bodyToFlux(UserResponseDto.class)
				.collectList()
				.block();

		System.out.println(t);
	}
}
