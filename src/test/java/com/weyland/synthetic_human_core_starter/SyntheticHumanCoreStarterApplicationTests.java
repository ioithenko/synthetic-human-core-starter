package com.weyland.synthetic_human_core_starter;

import com.weyland.synthetic_human_core_starter.command.CommandProcessingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SyntheticHumanCoreStarterApplicationTests {
	@Autowired
	private CommandProcessingService commandProcessingService;

	@Test
	void contextLoads() {
		assertNotNull(commandProcessingService);
	}
}