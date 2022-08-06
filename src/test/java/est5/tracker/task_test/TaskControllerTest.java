package est5.tracker.task_test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import est5.tracker.task.TaskController;

@SpringBootTest
public class TaskControllerTest {

    @Autowired
    private TaskController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}