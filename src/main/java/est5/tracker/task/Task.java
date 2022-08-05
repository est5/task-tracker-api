package est5.tracker.task;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
public class Task {

    @Setter(value = AccessLevel.NONE)
    private @Id @GeneratedValue Long id;

    private String description;
    private Status status = Status.IN_PROGRESS;

    public Task(String description) {
        this.description = description;
    }

    public Task() {
    };

}
