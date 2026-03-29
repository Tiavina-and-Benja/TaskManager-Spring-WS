package mg.itu.taskmanagerspringws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardProjectDto {
    private Long projectId;
    private String title;
    private Long totalTasks;
    private Long totalTaskDone;
    private Long totalTaskDoing;
    private Long totalTaskToDo;
}
