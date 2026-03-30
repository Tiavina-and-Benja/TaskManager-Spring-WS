package mg.itu.taskmanagerspringws.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.itu.taskmanagerspringws.enums.TaskHistoryAction;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Enumerated(EnumType.STRING)
    private TaskHistoryAction action;
    private String oldValue;
    private String newValue;

    private LocalDate changedAt;

    public TaskHistory(Task task, TaskHistoryAction taskHistoryAction, String oldValue, String newValue) {
    }
}
