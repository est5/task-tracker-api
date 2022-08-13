package est5.tracker.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import est5.tracker.task.Task;
import est5.tracker.task.TaskRepository;

@DataJpaTest
public class TaskRepoTest {

    @Autowired
    TaskRepository repo;

    @Test
    void testRepoCreated() {
        assertEquals(repo.count(), 0);
    }

    @Test
    void saveOne() {
        repo.save(new Task("Test"));
        assertTrue(repo.findByDescription("Test").isPresent());
    }

    @Test
    void deleteAll() {
        repo.save(new Task("del"));
        assertEquals(repo.count(), 1);

        repo.deleteAll();
        assertEquals(repo.count(), 0);
    }

    @Test
    void deleteOne() {
        repo.save(new Task("del1"));
        repo.save(new Task("del2"));
        repo.delete(repo.findByDescription("del1").get());

        assertEquals(repo.count(), 1);
        assertTrue(repo.findByDescription("del2").isPresent());
    }

}
