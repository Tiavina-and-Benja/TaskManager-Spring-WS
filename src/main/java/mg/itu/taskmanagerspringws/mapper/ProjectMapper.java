package mg.itu.taskmanagerspringws.mapper;

import mg.itu.taskmanagerspringws.dto.ProjectDto;
import mg.itu.taskmanagerspringws.model.Project;
import mg.itu.taskmanagerspringws.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface ProjectMapper {
    @Mapping(source = "user.id", target = "userId")
    ProjectDto toDto(Project project);

    @Mapping(source = "userId", target = "user")
    Project toEntity(ProjectDto dto);

    default User map(Long userId) {
        if (userId == null) return null;
        User user = new User();
        user.setId(userId);
        return user;
    }
}
