package est5.tracker.task;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class TaskController {

    final private TaskRepository taskRepository;
    final private TaskModelAssembler assembler;

    TaskController(TaskRepository taskRepository, TaskModelAssembler assembler) {
        this.taskRepository = taskRepository;
        this.assembler = assembler;
    }

    @GetMapping("/tasks")
    public CollectionModel<EntityModel<Task>> all() {
        List<EntityModel<Task>> tasks = taskRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(tasks,
                linkTo(methodOn(TaskController.class).all()).withSelfRel());
    }

    @GetMapping("/tasks/{id}")
    public EntityModel<Task> one(@PathVariable Long id) {

        Task task = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(id));

        return assembler.toModel(task);
    }

    @PostMapping("/tasks")
    ResponseEntity<EntityModel<Task>> newTask(@RequestBody Task task) {

        task.setStatus(Status.IN_PROGRESS);
        Task newTask = taskRepository.save(task);

        return ResponseEntity //
                .created(linkTo(methodOn(TaskController.class).one(newTask.getId())).toUri())
                .body(assembler.toModel(newTask));
    }

    @DeleteMapping("/tasks/{id}/cancel")
    ResponseEntity<?> cancel(@PathVariable Long id) {
        Task t = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (t.getStatus() == Status.IN_PROGRESS) {
            t.setStatus(Status.CANCELED);
            return ResponseEntity.ok(assembler.toModel(taskRepository.save(t)));
        }
        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't cancel a task that is in the " + t.getStatus() + " status"));

    }

    @PutMapping("/tasks/{id}/complete")
    ResponseEntity<?> complete(@PathVariable Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (task.getStatus() == Status.IN_PROGRESS) {
            task.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toModel(taskRepository.save(task)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't complete a task that is in the " + task.getStatus() + " status"));
    }

    @PutMapping("/tasks/{id}/undo")
    ResponseEntity<?> undo(@PathVariable Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (task.getStatus() != Status.IN_PROGRESS) {
            task.setStatus(Status.IN_PROGRESS);
            return ResponseEntity.ok(assembler.toModel(taskRepository.save(task)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't undo a task that is in the " + task.getStatus() + " status"));
    }

}
