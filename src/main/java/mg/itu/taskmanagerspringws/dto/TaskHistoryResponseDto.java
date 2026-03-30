package mg.itu.taskmanagerspringws.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.itu.taskmanagerspringws.enums.TaskHistoryAction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskHistoryResponseDto {

    private Long id;
    private String taskTitle;
    private String projectTitle;
    private TaskHistoryAction action;
    private String oldValue;
    private String newValue;
    private String name;
}