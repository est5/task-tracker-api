package est5.tracker.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import est5.tracker.task.Task;
import est5.tracker.task.TaskRepository;;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(TaskRepository repository) {
        return args -> {
            log.info("Preloading "
                    + repository.save(new Task("Living off borrowed time, the clock tick faster")));
            log.info("Preloading " + repository.save(new Task("There's only one beer left")));
        };
    }
}