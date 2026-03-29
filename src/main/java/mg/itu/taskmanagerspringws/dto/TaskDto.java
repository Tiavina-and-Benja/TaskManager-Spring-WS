package mg.itu.taskmanagerspringws.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.itu.taskmanagerspringws.enums.Priority;
import mg.itu.taskmanagerspringws.enums.Status;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    @NotEmpty
    private String title;
    private String description;
    @NotEmpty
    private Priority priority;
    private Status status;
    private LocalDate deadline;
    private LocalDate completedAt;
    @NotEmpty
    private Long projectId;
}