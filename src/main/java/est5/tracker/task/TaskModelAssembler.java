package est5.tracker.task;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class TaskModelAssembler implements RepresentationModelAssembler<Task, EntityModel<Task>> {

    @Override
    public EntityModel<Task> toModel(Task entity) {

        EntityModel<Task> taskModel = EntityModel.of(
                entity,
                linkTo(methodOn(TaskController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(TaskController.class).all()).withRel("tasks"));

        if (entity.getStatus() == Status.IN_PROGRESS) {

            taskModel.add(
                    linkTo(methodOn(TaskController.class).cancel(entity.getId())).withRel("cancel"),
                    linkTo(methodOn(TaskController.class).complete(entity.getId())).withRel("complete"));
        } else {
            taskModel.add(
                    linkTo(methodOn(TaskController.class).undo(entity.getId())).withRel("undo"));
        }

        return taskModel;
    }

}
