package mg.itu.taskmanagerspringws.mapper;

import mg.itu.taskmanagerspringws.dto.TaskHistoryDto;
import mg.itu.taskmanagerspringws.dto.TaskHistoryResponseDto;
import mg.itu.taskmanagerspringws.model.Task;
import mg.itu.taskmanagerspringws.model.TaskHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TaskHistoryMapper {
    @Mapping(source = "task.title", target = "taskTitle")
    @Mapping(source = "task.project.title", target = "projectTitle")
    TaskHistoryResponseDto toDto(TaskHistory taskHistory);

    @Mapping(source = "taskId", target = "task", qualifiedByName = "mapTaskIdToTask")
    TaskHistory toEntity(TaskHistoryDto taskHistoryDto);
    @Mapping(source = "task.id", target = "taskId")
    TaskHistoryDto taskHistorytoDto(TaskHistory taskHistory);

    @Named("mapTaskIdToTask")
    default Task mapTaskIdToTask(Long taskId) {
        if (taskId == null) {
            return null;
        }
        Task task = new Task();
        task.setId(taskId);
        return task;
    }
}
