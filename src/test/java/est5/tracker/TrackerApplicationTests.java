package est5.tracker;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import est5.tracker.task.TaskModelAssembler;
import est5.tracker.task.TaskRepository;

@SpringBootTest
class TrackerApplicationTests {

	@Autowired
	TaskRepository repo;

	@Autowired
	TaskModelAssembler assembler;

	@Test
	void contextLoads() {
	}

	@Test
	void injectedComponentsAreNotNull() {
		assertNotNull(repo);
		assertNotNull(assembler);
	}

}
