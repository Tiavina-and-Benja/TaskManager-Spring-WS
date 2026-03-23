package mg.itu.taskmanagerspringws.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mg.itu.taskmanagerspringws.enums.Priority;
import mg.itu.taskmanagerspringws.enums.Status;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate deadline;
    private LocalDate completedAt;
    private LocalDate startedAt;
    private double duration;

    @ManyToOne
    private Project project;

    @OneToMany(mappedBy = "task")
    private List<TaskTag> taskTags;
}
