package mg.itu.taskmanagerspringws.mapper;

import mg.itu.taskmanagerspringws.dto.TaskDto;
import mg.itu.taskmanagerspringws.dto.TaskScoreDto;
import mg.itu.taskmanagerspringws.model.Project;
import mg.itu.taskmanagerspringws.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "task.project.id", target = "projectId")
    TaskDto toDto(Task task);

    @Mapping(source = "projectId", target = "project.id")
    Task toEntity(TaskDto dto);

    @Mapping(source = "task.project.id", target = "projectId")
    TaskScoreDto toTaskScoreDto(Task task);

    default Project map(Long projectId) {
        if (projectId == null) return null;
        Project project = new Project();
        project.setId(projectId);
        return project;
    }
}