package mg.itu.taskmanagerspringws.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.itu.taskmanagerspringws.enums.TaskHistoryAction;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskHistoryDto {

    private Long id;
    private Long taskId;
    private String oldValue;
    private String newValue;
    private String name;
    private LocalDate changedAt;

}