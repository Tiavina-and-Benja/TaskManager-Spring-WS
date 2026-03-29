package mg.itu.taskmanagerspringws.dto;

import lombok.Data;
import mg.itu.taskmanagerspringws.enums.Priority;
import mg.itu.taskmanagerspringws.enums.Status;

import java.time.LocalDate;

@Data
public class TaskScoreDto {
    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private LocalDate deadline;
    private Long projectId;
    private int priorityScore;
}
