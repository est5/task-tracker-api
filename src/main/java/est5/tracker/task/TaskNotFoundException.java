package est5.tracker.task;

public class TaskNotFoundException extends RuntimeException {
    TaskNotFoundException(Long id) {
        super("No task with id: " + id);
    }
}
